package org.nifiopa.nifiopa;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.nifi.authorization.AuthorizationRequest;
import org.apache.nifi.authorization.AuthorizationResult;
import org.apache.nifi.authorization.Authorizer;
import org.apache.nifi.authorization.AuthorizerConfigurationContext;
import org.apache.nifi.authorization.AuthorizerInitializationContext;
import org.apache.nifi.authorization.exception.AuthorizationAccessException;
import org.apache.nifi.authorization.exception.AuthorizerCreationException;
import org.apache.nifi.authorization.exception.AuthorizerDestructionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.styra.opa.OPAClient;
import com.styra.opa.OPAException;

public class OpaAuthorizer implements Authorizer {
	private static final Logger logger = LoggerFactory.getLogger(OpaAuthorizer.class);
	private OPAClient opaClient;
	private RequestCache cache;
	private boolean dumpCache = false;

	private final String OPA_URI_PROPNAME = "OPA_URI";

	private final String OPA_RULE_HEAD_PROPNAME = "OPA_RULE_HEAD";
	private String OPA_RULE_HEAD;

	@Override
	public AuthorizationResult authorize(AuthorizationRequest request) throws AuthorizationAccessException {

		AuthorizationResult cachedResult = cache.getCachedResult(request);
		if (cachedResult != null && !dumpCache) {
			logger.debug("PolicyCache: Cache hit.");
			return cachedResult;
		}

		Map<String, Map<String, String>> requestForm = null;

		try {
			/* CREATING REQUEST */
			requestForm = Map.of(
					"action",
					Map.of("name", request.getAction().toString()),

					"identity",
					Map.of("name", request.getIdentity(),
							"groups", String.join(",", request.getGroups())),

					"resource",
					Map.of("name", request.getResource().getName(),
							"id", request.getResource().getIdentifier(),
							"safeDescription", request.getResource().getSafeDescription()),
					"requestedResource",
					Map.of("name", request.getRequestedResource().getName(),
							"id", request.getRequestedResource().getIdentifier(),
							"safeDescription", request.getRequestedResource().getSafeDescription()),
					"properties",
					Map.of("isAccessAttempt", Boolean.toString(request.isAccessAttempt()),
							"isAnonymous", Boolean.toString(request.isAnonymous())),
					"userContext",
					request.getUserContext() != null && !request.getUserContext().isEmpty() ? request.getUserContext() : Map.of("", ""),
					"resourceContext",
					request.getResourceContext() != null && !request.getResourceContext().isEmpty() ? request.getResourceContext() : Map.of("", ""));
		} catch (Exception e) {
			logger.error(
					"An error occured while trying to build the OPA-request.", e);
			return AuthorizationResult.denied("An error occured while trying to build the OPA-request.");
		}

		OPAResponse opaResponse = null;
		try {
			opaResponse = opaClient.evaluate(OPA_RULE_HEAD, requestForm, OPAResponse.class);
		} catch (OPAException e) {
			logger.error("An error occured while trying to query against OPA.", e);
			return AuthorizationResult.denied("An error occured while trying to query against OPA.");
		}
		if (opaResponse == null) {
			logger.error("An error occured while unmarshalling an OPA response.");
			return AuthorizationResult.denied("An error occured while unmarshalling an OPA response.");
		}

		// Evaluate response from OPA
		dumpCache = opaResponse.dumpCache();
		if (dumpCache) {
			logger.debug("PolicyCache: Cache cleared.");
			cache.clear();
		}

		if (opaResponse.resourceNotFound()) {
			cache.putCachedResult(request, AuthorizationResult.resourceNotFound());
			logger.debug("Authorizer-Result: Resource not found");
			return AuthorizationResult.resourceNotFound();
		}

		if (opaResponse.allowed()) {
				cache.putCachedResult(request, AuthorizationResult.approved());
				logger.debug("Authorizer-Result: Access was approved");
				return AuthorizationResult.approved();
		} else {
				cache.putCachedResult(request, AuthorizationResult.denied());
				logger.debug("Authorizer-Result: Access was denied");
				return AuthorizationResult
						.denied(opaResponse.message() != null ? opaResponse.message() : "Access denied.");
		}
	}

	@Override
	public void initialize(AuthorizerInitializationContext initializationContext) throws AuthorizerCreationException {
	}

	@Override
	public void onConfigured(AuthorizerConfigurationContext configurationContext) throws AuthorizerCreationException {
		String uriProp = ConfigLoader.getProperty(configurationContext, OPA_URI_PROPNAME);
		if (uriProp == null)
			throw new AuthorizerCreationException("Missing required property OPA_URI");

		OPA_RULE_HEAD = ConfigLoader.getProperty(configurationContext, OPA_RULE_HEAD_PROPNAME);
		if (OPA_RULE_HEAD == null)
			throw new AuthorizerCreationException("Missing required property OPA_RULE_HEAD");

		opaClient = new OPAClient(uriProp);
		cache = new RequestCache();
		cache.initialize(configurationContext);
	}

	@Override
	public void preDestruction() throws AuthorizerDestructionException {
	}
}
