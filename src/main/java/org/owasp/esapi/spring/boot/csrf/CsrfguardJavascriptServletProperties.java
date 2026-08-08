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

/**
 * Configuration options for the OWASP CSRFGuard JavaScript servlet that serves the
 * dynamic {@code csrfguard.js} script injected into pages for CSRF protection.
 *
 * @author <a href="https://github.com/loong10k">@Loong Wan</a>
 * @since 1.0.0
 */
public class CsrfguardJavascriptServletProperties {

	/** URL pattern that the CSRFGuard JavaScript servlet is mapped to. */
	private String pattern = "/csrfguard.js";
	/** HTTP cache-control header value returned by the JavaScript servlet. */
	private String cacheControl = "private, maxage=28800";
	/** Whether to enforce strict domain matching when validating the request origin. */
	private boolean domainStrict = true;
	/** Whether to inject CSRF tokens into HTML attributes. */
	private boolean injectIntoAttributes = true;
	/** Whether to inject CSRF tokens into HTTP GET forms. */
	private boolean injectGetForms = true;
	/** Whether to inject CSRF tokens into form attributes. */
	private boolean injectFormAttributes = true;
	/** Whether to inject CSRF tokens directly into forms. */
	private boolean injectIntoForms = true;
	/** Regular expression used to validate the HTTP referer header. */
	private String refererPattern = ".*";
	/** Whether the referer must match the serving domain. */
	private boolean refererMatchDomain = true;
	/** Optional alternate source file for the CSRFGuard JavaScript. */
	private String sourceFile = null;
	/** Value of the {@code X-Requested-With} header expected for AJAX requests. */
	private String XRequestedWith = "OWASP CSRFGuard Project";

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getCacheControl() {
		return cacheControl;
	}

	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}

	public boolean isDomainStrict() {
		return domainStrict;
	}

	public void setDomainStrict(boolean domainStrict) {
		this.domainStrict = domainStrict;
	}

	public boolean isInjectIntoAttributes() {
		return injectIntoAttributes;
	}

	public void setInjectIntoAttributes(boolean injectIntoAttributes) {
		this.injectIntoAttributes = injectIntoAttributes;
	}

	public boolean isInjectGetForms() {
		return injectGetForms;
	}

	public void setInjectGetForms(boolean injectGetForms) {
		this.injectGetForms = injectGetForms;
	}

	public boolean isInjectFormAttributes() {
		return injectFormAttributes;
	}

	public void setInjectFormAttributes(boolean injectFormAttributes) {
		this.injectFormAttributes = injectFormAttributes;
	}

	public boolean isInjectIntoForms() {
		return injectIntoForms;
	}

	public void setInjectIntoForms(boolean injectIntoForms) {
		this.injectIntoForms = injectIntoForms;
	}

	public String getRefererPattern() {
		return refererPattern;
	}

	public void setRefererPattern(String refererPattern) {
		this.refererPattern = refererPattern;
	}

	public boolean isRefererMatchDomain() {
		return refererMatchDomain;
	}

	public void setRefererMatchDomain(boolean refererMatchDomain) {
		this.refererMatchDomain = refererMatchDomain;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public String getXRequestedWith() {
		return XRequestedWith;
	}

	public void setXRequestedWith(String xRequestedWith) {
		XRequestedWith = xRequestedWith;
	}

}
