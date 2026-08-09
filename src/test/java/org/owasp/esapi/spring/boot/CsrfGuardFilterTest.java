package org.owasp.esapi.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.owasp.esapi.spring.boot.csrf.web.filter.CsrfGuardFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Tests for {@link CsrfGuardFilter}.
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class CsrfGuardFilterTest {

    @Test
    void filterShouldInitialize() throws Exception {
        CsrfGuardFilter filter = new CsrfGuardFilter();
        FilterConfig config = mock(FilterConfig.class);
        filter.init(config);
        assertThat(filter).isNotNull();
    }

    @Test
    void filterShouldPassThrough() throws Exception {
        CsrfGuardFilter filter = new CsrfGuardFilter();
        ServletRequest request = mock(ServletRequest.class);
        ServletResponse response = mock(ServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    void filterShouldDestroy() {
        CsrfGuardFilter filter = new CsrfGuardFilter();
        filter.destroy();
    }
}
