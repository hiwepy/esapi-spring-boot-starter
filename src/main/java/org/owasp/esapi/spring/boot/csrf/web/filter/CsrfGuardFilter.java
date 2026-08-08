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
package org.owasp.esapi.spring.boot.csrf.web.filter;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.owasp.csrfguard.CsrfGuard;
import org.owasp.csrfguard.http.InterceptRedirectResponse;
import org.springframework.boot.autoconfigure.security.SecurityProperties.Filter;

/**
 * CSRF guard filter adapted from {@code org.owasp.csrfguard.CsrfGuardFilter} and
 * integrated with the Spring Security filter chain by extending
 * {@link org.springframework.boot.autoconfigure.security.SecurityProperties.Filter}.
 *
 * @author <a href="https://github.com/loong10k">@Loong Wan</a>
 * @since 1.0.0
 */
public class CsrfGuardFilter extends Filter {

	/**
	 * Allows access when CSRFGuard is disabled (short-circuit behaviour).
	 * @param request the servlet request
	 * @param response the servlet response
	 * @param mappedValue the filter-mapped value, if any
	 * @return {@code true} if access is allowed because CSRFGuard is disabled
	 * @throws Exception if an error occurs while inspecting CSRFGuard state
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		//maybe the short circuit to disable is set
		return !CsrfGuard.getInstance().isEnabled();
	}

	/**
	 * Handles denied access by validating the CSRF token of the current HTTP request
	 * and refreshing tokens when necessary.
	 * @param request the servlet request
	 * @param response the servlet response
	 * @return {@code true} to allow the request to proceed, {@code false} to block it
	 * @throws Exception if an error occurs while validating the request
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		
		/** only work with HttpServletRequest objects **/
		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpSession session = httpRequest.getSession(false);
			
			//if there is no session and we arent validating when no session exists
			if (session == null && !CsrfGuard.getInstance().isValidateWhenNoSessionExists()) {
				// If there is no session, no harm can be done
				return true;
			}

			CsrfGuard csrfGuard = CsrfGuard.getInstance();
			csrfGuard.getLogger().log(String.format("CsrfGuard analyzing request %s", httpRequest.getRequestURI()));

			InterceptRedirectResponse httpResponse = new InterceptRedirectResponse((HttpServletResponse) response, httpRequest, csrfGuard);

//			 if(MultipartHttpServletRequest.isMultipartRequest(httpRequest)) {
//				 httpRequest = new MultipartHttpServletRequest(httpRequest);
//			 }

			if ((session != null && session.isNew()) && csrfGuard.isUseNewTokenLandingPage()) {
				csrfGuard.writeLandingPage(httpRequest, httpResponse);
			} else if (csrfGuard.isValidRequest(httpRequest, httpResponse)) {
				return true;
			} else {
				/** invalid request - nothing to do - actions already executed **/
			}

			/** update tokens **/
			csrfGuard.updateTokens(httpRequest);

		} else {
			filterConfig.getServletContext().log(String.format("[WARNING] CsrfGuard does not know how to work with requests of class %s ", request.getClass().getName()));
			return true;
		}
		
		return true;
	}
	
}
