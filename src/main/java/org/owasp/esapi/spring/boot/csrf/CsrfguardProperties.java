/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.owasp.esapi.spring.boot.csrf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Configuration properties for OWASP CSRFGuard, mirroring the properties consumed by
 * the underlying CSRFGuard runtime and convertible to a {@link Properties} instance
 * via {@link #toProperties()}.
 *
 * @author <a href="https://github.com/loong10k">@Loong Wan</a>
 * @since 1.0.0
 */
public class CsrfguardProperties {

	/** Property key prefix used for CSRFGuard action entries. */
	private final static String ACTION_PREFIX = "org.owasp.csrfguard.action.";

	/** Property key prefix used for protected page entries. */
	private final static String PROTECTED_PAGE_PREFIX = "org.owasp.csrfguard.protected.";

	/** Property key prefix used for unprotected page entries. */
	private final static String UNPROTECTED_PAGE_PREFIX = "org.owasp.csrfguard.unprotected.";

	/**
	 * Supported CSRFGuard logger implementations.
	 */
	public enum LoggerType {

		/** Logger that writes to the console. */
		CONSOLE("org.owasp.csrfguard.log.ConsoleLogger"),
		/** Logger that delegates to the Java logging API. */
		JAVA("org.owasp.csrfguard.log.JavaLogger");

		private final String implClassName;

		/**
		 * Creates a logger type bound to the given implementation class name.
		 * @param implClassName the fully qualified logger implementation class name
		 */
		LoggerType(String implClassName) {
			this.implClassName = implClassName;
		}

		/**
		 * Returns the fully qualified implementation class name of this logger.
		 * @return the logger implementation class name
		 */
		public String className() {
			return implClassName;
		}

		/**
		 * Compares this logger type to another by ordinal.
		 * @param loggerType the logger type to compare with
		 * @return {@code true} if both logger types have the same ordinal
		 */
		public boolean equals(LoggerType loggerType) {
			return this.compareTo(loggerType) == 0;
		}

	}

	/** Whether CSRFGuard protection is enabled. */
	private boolean enabled = false;
	/** Logger implementation used by CSRFGuard. */
	private LoggerType logger = LoggerType.CONSOLE;
	/** Name of the CSRF token request parameter. */
	private String tokenName = "OWASP_CSRFGUARD";
	/** Length of the generated CSRF token. */
	private int tokenLength = 32;
	/** Whether the token is rotated on each request. */
	private boolean rotateEnabled = false;
	/** Whether per-page token validation is enabled. */
	private boolean tokenPerPageEnabled = false;
	/**
	 * If csrf guard filter should check even if there is no session for the user
	 * Note: this changed in 2014/04, the default behavior used to be to not check
	 * if there is no session. If you want the legacy behavior (if your app is not
	 * susceptible to CSRF if the user has no session), set this to false
	 */
	private boolean validationWhenNoSessionExists = true;

	/** Whether per-page tokens are pre-created. */
	private boolean tokenPerPagePrecreateEnabled = false;
	/** Whether to print the effective CSRFGuard configuration on startup. */
	private boolean printConfig = false;
	/** Pseudo-random number generation algorithm used to generate tokens. */
	private String prng = "SHA1PRNG";
	/** Provider of the pseudo-random number generator. */
	private String prngProvider = "SUN";

	/** Landing page shown when a new token is generated. */
	private String newTokenLandingPage;

	/** Whether to use the new token landing page. */
	private boolean useNewTokenLandingPage = false;

	/** Whether AJAX token handling is enabled. */
	private boolean ajaxEnabled = false;

	/** Whether page-level protection is enabled. */
	private boolean protectEnabled = false;

	/** Session attribute key under which the CSRF token is stored. */
	private String sessionKey = "OWASP_CSRFGUARD_KEY";

	/** Map of CSRFGuard action names to their implementation classes. */
	private Map<String, String> actions = new HashMap<String, String>();

	/** Map of protected page patterns to their handling configuration. */
	private Map<String, String> protectedPages = new HashMap<String, String>();

	/** Map of unprotected page patterns to their handling configuration. */
	private Map<String, String> unprotectedPages = new HashMap<String, String>();

	/** Set of HTTP methods that are CSRF-protected. */
	private Set<String> protectedMethods = new HashSet<String>();

	/** Set of HTTP methods that are excluded from CSRF protection. */
	private Set<String> unprotectedMethods = new HashSet<String>();

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public LoggerType getLogger() {
		return logger;
	}

	public void setLogger(LoggerType logger) {
		this.logger = logger;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public int getTokenLength() {
		return tokenLength;
	}

	public void setTokenLength(int tokenLength) {
		this.tokenLength = tokenLength;
	}

	public boolean isRotateEnabled() {
		return rotateEnabled;
	}

	public void setRotateEnabled(boolean rotateEnabled) {
		this.rotateEnabled = rotateEnabled;
	}

	public boolean isTokenPerPageEnabled() {
		return tokenPerPageEnabled;
	}

	public void setTokenPerPageEnabled(boolean tokenPerPageEnabled) {
		this.tokenPerPageEnabled = tokenPerPageEnabled;
	}

	public boolean isValidationWhenNoSessionExists() {
		return validationWhenNoSessionExists;
	}

	public void setValidationWhenNoSessionExists(boolean validationWhenNoSessionExists) {
		this.validationWhenNoSessionExists = validationWhenNoSessionExists;
	}

	public boolean isTokenPerPagePrecreateEnabled() {
		return tokenPerPagePrecreateEnabled;
	}

	public void setTokenPerPagePrecreateEnabled(boolean tokenPerPagePrecreateEnabled) {
		this.tokenPerPagePrecreateEnabled = tokenPerPagePrecreateEnabled;
	}

	public boolean isPrintConfig() {
		return printConfig;
	}

	public void setPrintConfig(boolean printConfig) {
		this.printConfig = printConfig;
	}

	public String getPrng() {
		return prng;
	}

	public void setPrng(String prng) {
		this.prng = prng;
	}

	public String getPrngProvider() {
		return prngProvider;
	}

	public void setPrngProvider(String prngProvider) {
		this.prngProvider = prngProvider;
	}

	public String getNewTokenLandingPage() {
		return newTokenLandingPage;
	}

	public void setNewTokenLandingPage(String newTokenLandingPage) {
		this.newTokenLandingPage = newTokenLandingPage;
	}

	public boolean isUseNewTokenLandingPage() {
		return useNewTokenLandingPage;
	}

	public void setUseNewTokenLandingPage(boolean useNewTokenLandingPage) {
		this.useNewTokenLandingPage = useNewTokenLandingPage;
	}

	public boolean isAjaxEnabled() {
		return ajaxEnabled;
	}

	public void setAjaxEnabled(boolean ajaxEnabled) {
		this.ajaxEnabled = ajaxEnabled;
	}

	public boolean isProtectEnabled() {
		return protectEnabled;
	}

	public void setProtectEnabled(boolean protectEnabled) {
		this.protectEnabled = protectEnabled;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public Map<String, String> getActions() {
		return actions;
	}

	public void setActions(Map<String, String> actions) {
		this.actions = actions;
	}

	public Map<String, String> getProtectedPages() {
		return protectedPages;
	}

	public void setProtectedPages(Map<String, String> protectedPages) {
		this.protectedPages = protectedPages;
	}

	public Map<String, String> getUnprotectedPages() {
		return unprotectedPages;
	}

	public void setUnprotectedPages(Map<String, String> unprotectedPages) {
		this.unprotectedPages = unprotectedPages;
	}

	public Set<String> getProtectedMethods() {
		return protectedMethods;
	}

	public void setProtectedMethods(Set<String> protectedMethods) {
		this.protectedMethods = protectedMethods;
	}

	public Set<String> getUnprotectedMethods() {
		return unprotectedMethods;
	}

	public void setUnprotectedMethods(Set<String> unprotectedMethods) {
		this.unprotectedMethods = unprotectedMethods;
	}

	/**
	 * Flattens these configuration options into a {@link Properties} instance using the
	 * property key names expected by the OWASP CSRFGuard runtime, including nested
	 * action, protected-page and unprotected-page entries.
	 * @return a properties object suitable for initialising CSRFGuard
	 */
	public Properties toProperties() {

		Properties properties = new Properties();

		properties.put("org.owasp.csrfguard.Logger", logger.className());
		properties.put("org.owasp.csrfguard.TokenName", tokenName);
		properties.put("org.owasp.csrfguard.TokenLength", tokenLength);
		properties.put("org.owasp.csrfguard.Rotate", rotateEnabled);
		properties.put("org.owasp.csrfguard.TokenPerPage", tokenPerPageEnabled);
		properties.put("org.owasp.csrfguard.ValidateWhenNoSessionExists", validationWhenNoSessionExists);
		properties.put("org.owasp.csrfguard.TokenPerPagePrecreate", tokenPerPagePrecreateEnabled);
		properties.put("org.owasp.csrfguard.PRNG", prng);
		properties.put("org.owasp.csrfguard.PRNG.Provider", prngProvider);
		properties.put("org.owasp.csrfguard.NewTokenLandingPage", newTokenLandingPage);
		properties.put("org.owasp.csrfguard.Config.Print", printConfig);
		properties.put("org.owasp.csrfguard.Enabled", enabled);
		properties.put("org.owasp.csrfguard.UseNewTokenLandingPage", useNewTokenLandingPage);
		properties.put("org.owasp.csrfguard.SessionKey", sessionKey);
		properties.put("org.owasp.csrfguard.Ajax", ajaxEnabled);
		properties.put("org.owasp.csrfguard.Protect", protectEnabled);
		properties.put("org.owasp.csrfguard.ProtectedMethods", StringUtils.join(protectedMethods, ","));
		properties.put("org.owasp.csrfguard.UnprotectedMethods", StringUtils.join(unprotectedMethods, ","));
		
		if(MapUtils.isNotEmpty(actions)) {
			Iterator<String> ite = actions.keySet().iterator();
			while (ite.hasNext()) {
				String key = ite.next();
				properties.put(ACTION_PREFIX + key, actions.get(key));
			}
		}

		if(MapUtils.isNotEmpty(protectedPages)) {
			Iterator<String> ite = protectedPages.keySet().iterator();
			while (ite.hasNext()) {
				String key = ite.next();
				properties.put(PROTECTED_PAGE_PREFIX + key, protectedPages.get(key));
			}
		}
		
		if(MapUtils.isNotEmpty(unprotectedPages)) {
			Iterator<String> ite = unprotectedPages.keySet().iterator();
			while (ite.hasNext()) {
				String key = ite.next();
				properties.put(UNPROTECTED_PAGE_PREFIX + key, unprotectedPages.get(key));
			}
		}

		return properties;
	}

}
