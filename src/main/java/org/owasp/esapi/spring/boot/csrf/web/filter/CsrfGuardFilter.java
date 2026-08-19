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

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * CSRF guard filter placeholder that can be enabled via configuration.
 * When CSRFGuard is on the classpath this filter delegates to it; otherwise
 * it acts as a pass-through.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class CsrfGuardFilter implements Filter {

	@Override
	/**
	 * <p>Init.</p>
	 * @param filterConfig
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		// no-op
	}

	@Override
	/**
	 * <p>Do filter.</p>
	 * @param request
	 * @param response
	 * @param chain
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	@Override
	/**
	 * <p>Destroy.</p>
	 */
	public void destroy() {
		// no-op
	}

}
