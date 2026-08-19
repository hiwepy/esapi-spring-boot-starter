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

/**
 * Configuration properties for OWASP CSRFGuard, mirroring the properties consumed by
 * the underlying CSRFGuard runtime and convertible to a {@link Properties} instance
 * via {@link #toProperties()}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
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

	/** @return return whether enabled is enabled. */
	public boolean isEnabled() {
		return enabled;
	}

	/** @param enabled set the enabled. */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/** @return return the logger. */
	public LoggerType getLogger() {
		return logger;
	}

	/** @param logger set the logger. */
	public void setLogger(LoggerType logger) {
		this.logger = logger;
	}

	/** @return return the token name. */
	public String getTokenName() {
		return tokenName;
	}

	/** @param tokenName set the token name. */
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	/** @return return the token length. */
	public int getTokenLength() {
		return tokenLength;
	}

	/** @param tokenLength set the token length. */
	public void setTokenLength(int tokenLength) {
		this.tokenLength = tokenLength;
	}

	/** @return return whether rotate enabled is enabled. */
	public boolean isRotateEnabled() {
		return rotateEnabled;
	}

	/** @param rotateEnabled set the rotate enabled. */
	public void setRotateEnabled(boolean rotateEnabled) {
		this.rotateEnabled = rotateEnabled;
	}

	/** @return return whether token per page enabled is enabled. */
	public boolean isTokenPerPageEnabled() {
		return tokenPerPageEnabled;
	}

	/** @param tokenPerPageEnabled set the token per page enabled. */
	public void setTokenPerPageEnabled(boolean tokenPerPageEnabled) {
		this.tokenPerPageEnabled = tokenPerPageEnabled;
	}

	/** @return return whether validation when no session exists is enabled. */
	public boolean isValidationWhenNoSessionExists() {
		return validationWhenNoSessionExists;
	}

	/** @param validationWhenNoSessionExists set the validation when no session exists. */
	public void setValidationWhenNoSessionExists(boolean validationWhenNoSessionExists) {
		this.validationWhenNoSessionExists = validationWhenNoSessionExists;
	}

	/** @return return whether token per page precreate enabled is enabled. */
	public boolean isTokenPerPagePrecreateEnabled() {
		return tokenPerPagePrecreateEnabled;
	}

	/** @param tokenPerPagePrecreateEnabled set the token per page precreate enabled. */
	public void setTokenPerPagePrecreateEnabled(boolean tokenPerPagePrecreateEnabled) {
		this.tokenPerPagePrecreateEnabled = tokenPerPagePrecreateEnabled;
	}

	/** @return return whether print config is enabled. */
	public boolean isPrintConfig() {
		return printConfig;
	}

	/** @param printConfig set the print config. */
	public void setPrintConfig(boolean printConfig) {
		this.printConfig = printConfig;
	}

	/** @return return the prng. */
	public String getPrng() {
		return prng;
	}

	/** @param prng set the prng. */
	public void setPrng(String prng) {
		this.prng = prng;
	}

	/** @return return the prng provider. */
	public String getPrngProvider() {
		return prngProvider;
	}

	/** @param prngProvider set the prng provider. */
	public void setPrngProvider(String prngProvider) {
		this.prngProvider = prngProvider;
	}

	/** @return return the new token landing page. */
	public String getNewTokenLandingPage() {
		return newTokenLandingPage;
	}

	/** @param newTokenLandingPage set the new token landing page. */
	public void setNewTokenLandingPage(String newTokenLandingPage) {
		this.newTokenLandingPage = newTokenLandingPage;
	}

	/** @return return whether use new token landing page is enabled. */
	public boolean isUseNewTokenLandingPage() {
		return useNewTokenLandingPage;
	}

	/** @param useNewTokenLandingPage set the use new token landing page. */
	public void setUseNewTokenLandingPage(boolean useNewTokenLandingPage) {
		this.useNewTokenLandingPage = useNewTokenLandingPage;
	}

	/** @return return whether ajax enabled is enabled. */
	public boolean isAjaxEnabled() {
		return ajaxEnabled;
	}

	/** @param ajaxEnabled set the ajax enabled. */
	public void setAjaxEnabled(boolean ajaxEnabled) {
		this.ajaxEnabled = ajaxEnabled;
	}

	/** @return return whether protect enabled is enabled. */
	public boolean isProtectEnabled() {
		return protectEnabled;
	}

	/** @param protectEnabled set the protect enabled. */
	public void setProtectEnabled(boolean protectEnabled) {
		this.protectEnabled = protectEnabled;
	}

	/** @return return the session key. */
	public String getSessionKey() {
		return sessionKey;
	}

	/** @param sessionKey set the session key. */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	/** @return return the actions. */
	public Map<String, String> getActions() {
		return actions;
	}

	/** @param actions set the actions. */
	public void setActions(Map<String, String> actions) {
		this.actions = actions;
	}

	/** @return return the protected pages. */
	public Map<String, String> getProtectedPages() {
		return protectedPages;
	}

	/** @param protectedPages set the protected pages. */
	public void setProtectedPages(Map<String, String> protectedPages) {
		this.protectedPages = protectedPages;
	}

	/** @return return the unprotected pages. */
	public Map<String, String> getUnprotectedPages() {
		return unprotectedPages;
	}

	/** @param unprotectedPages set the unprotected pages. */
	public void setUnprotectedPages(Map<String, String> unprotectedPages) {
		this.unprotectedPages = unprotectedPages;
	}

	/** @return return the protected methods. */
	public Set<String> getProtectedMethods() {
		return protectedMethods;
	}

	/** @param protectedMethods set the protected methods. */
	public void setProtectedMethods(Set<String> protectedMethods) {
		this.protectedMethods = protectedMethods;
	}

	/** @return return the unprotected methods. */
	public Set<String> getUnprotectedMethods() {
		return unprotectedMethods;
	}

	/** @param unprotectedMethods set the unprotected methods. */
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
		if (newTokenLandingPage != null) {
			properties.put("org.owasp.csrfguard.NewTokenLandingPage", newTokenLandingPage);
		}
		properties.put("org.owasp.csrfguard.Config.Print", printConfig);
		properties.put("org.owasp.csrfguard.Enabled", enabled);
		properties.put("org.owasp.csrfguard.UseNewTokenLandingPage", useNewTokenLandingPage);
		properties.put("org.owasp.csrfguard.SessionKey", sessionKey);
		properties.put("org.owasp.csrfguard.Ajax", ajaxEnabled);
		properties.put("org.owasp.csrfguard.Protect", protectEnabled);
		properties.put("org.owasp.csrfguard.ProtectedMethods", String.join(",", protectedMethods));
		properties.put("org.owasp.csrfguard.UnprotectedMethods", String.join(",", unprotectedMethods));
		
		if (!actions.isEmpty()) {
			for (Map.Entry<String, String> entry : actions.entrySet()) {
				properties.put(ACTION_PREFIX + entry.getKey(), entry.getValue());
			}
		}

		if (!protectedPages.isEmpty()) {
			for (Map.Entry<String, String> entry : protectedPages.entrySet()) {
				properties.put(PROTECTED_PAGE_PREFIX + entry.getKey(), entry.getValue());
			}
		}
		
		if (!unprotectedPages.isEmpty()) {
			for (Map.Entry<String, String> entry : unprotectedPages.entrySet()) {
				properties.put(UNPROTECTED_PAGE_PREFIX + entry.getKey(), entry.getValue());
			}
		}

		return properties;
	}

}
