package org.nifiopa.nifiopa;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.nifi.authorization.AuthorizationRequest;
import org.apache.nifi.authorization.AuthorizationResult;
import org.apache.nifi.authorization.AuthorizerConfigurationContext;
import org.apache.nifi.authorization.exception.AuthorizationAccessException;
import org.apache.nifi.authorization.exception.AuthorizerCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestCache {

	private static final Logger logger = LoggerFactory.getLogger(RequestCache.class);

	private Cache<RequestKey, AuthorizationResult> cache;

	private final String CACHE_TIME_SECS_PROPNAME = "CACHE_TIME_SECS";
	private int CACHE_TIME_SECS;
	private final String CACHE_TIME_SECS_DEFAULT = "30";

	private final String CACHE_MAX_ENTRY_COUNT_PROPNAME = "CACHE_MAX_ENTRY_COUNT";
	private int CACHE_MAX_ENTRY_COUNT;
	private final String CACHE_MAX_ENTRY_COUNT_DEFAULT = "0";

	void putCachedResult(AuthorizationRequest request, AuthorizationResult result) throws AuthorizationAccessException {
		if (request == null | result == null)
			return;

		cache.put(new RequestKey(request), result);
	}

	AuthorizationResult getCachedResult(AuthorizationRequest request) throws AuthorizationAccessException {
		return cache.getIfPresent(new RequestKey(request));
	}

	void clear() {
		cache.invalidateAll();
	}

	public void initialize(AuthorizerConfigurationContext configurationContext) throws AuthorizerCreationException {
		initializePropertys(configurationContext);

		cache = Caffeine.newBuilder()
				.expireAfterWrite(CACHE_TIME_SECS, TimeUnit.SECONDS)
				.maximumSize(CACHE_MAX_ENTRY_COUNT)
				.build();
	}

	void initializePropertys(AuthorizerConfigurationContext configurationContext) {
		try {
			/* Initialize property of maximum cache time after write */
			String cacheTimeProp = ConfigLoader.getProperty(configurationContext, CACHE_TIME_SECS_PROPNAME, CACHE_TIME_SECS_DEFAULT);
			if (cacheTimeProp != null)
				CACHE_TIME_SECS = Integer.parseInt(cacheTimeProp);
		} catch (NumberFormatException nfe) {
			String message = MessageFormat.format(
					"An error occured while trying to initialze the Cache property {0}: {1}", CACHE_TIME_SECS_PROPNAME,
					nfe.getStackTrace().toString());
			logger.error(message);
			throw new AuthorizerCreationException(message);
		}

		/* Initialize property of maximum entries in the cache at one time */
		try {
			String cacheMaxEntryProp = ConfigLoader.getProperty(configurationContext, CACHE_MAX_ENTRY_COUNT_PROPNAME, CACHE_MAX_ENTRY_COUNT_DEFAULT);
			if (cacheMaxEntryProp != null)
				CACHE_MAX_ENTRY_COUNT = Integer.parseInt(cacheMaxEntryProp);
		} catch (NumberFormatException nfe) {
			String message = MessageFormat.format(
					"An error occured while trying to initialze the Cache property {0}: {1}",
					CACHE_MAX_ENTRY_COUNT_PROPNAME, nfe.getStackTrace().toString());
			logger.error(message);
			throw new AuthorizerCreationException(message);
		}
	}

	class RequestKey {

		private int hashCode;

		public RequestKey(AuthorizationRequest request) {
			hashCode = Objects.hash(

					Optional.ofNullable(request.getIdentity())
							.map(Object::hashCode)
							.orElse(0),

					Optional.ofNullable(request.getGroups())
							.map(Object::hashCode)
							.orElse(0),

					request.getAction().toString().hashCode(),

					Optional.ofNullable(request.getRequestedResource())
							.map(res -> res.getIdentifier() != null ? res.getIdentifier().hashCode() : 0)
							.orElse(0),

					Optional.ofNullable(request.getResource())
							.map(res -> res.getIdentifier() != null ? res.getIdentifier().hashCode() : 0)
							.orElse(0),

					Optional.ofNullable(request.getResourceContext())
							.map(Object::hashCode)
							.orElse(0),

					Optional.ofNullable(request.getUserContext())
							.map(Object::hashCode)
							.orElse(0));

		}

		@Override
		public int hashCode() {
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			return obj.hashCode() == hashCode;
		}
	}
}
