package org.nifiopa.nifiopa;

import java.security.InvalidParameterException;
import java.text.MessageFormat;

import org.apache.nifi.authorization.AuthorizerConfigurationContext;
import org.apache.nifi.components.PropertyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigLoader {

	private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

	public static String getProperty(AuthorizerConfigurationContext configurationContext, String propertyName, String defaultValue) throws InvalidParameterException {
		// 1. Try load from environment
		try {
			String propertyEnv = System.getenv(propertyName);
			if (propertyEnv != null) {
				return propertyEnv;
			}
		} catch (Exception e) {
			logger.warn(
					MessageFormat.format("Could not find or load property with name {0} in environment", propertyName));
		}

		// 2. Try load from configurations
		try {
			PropertyValue propertyConf = configurationContext.getProperty(propertyName);
			if (propertyConf != null && propertyConf.getValue() != null) {
				return propertyConf.getValue();
			}
		} catch (Exception e) {
			logger.warn(
					MessageFormat.format("Could not find or load property with name {0} in properties", propertyName));
		}

		return defaultValue;
	}

	public static String getProperty(AuthorizerConfigurationContext configurationContext, String propertyName) throws InvalidParameterException {
		String property = ConfigLoader.getProperty(configurationContext, propertyName, null);
		if (property != null)
			return property;
		throw new InvalidParameterException(
				MessageFormat.format("Could not find or load required property {0}", propertyName));
	}

}
