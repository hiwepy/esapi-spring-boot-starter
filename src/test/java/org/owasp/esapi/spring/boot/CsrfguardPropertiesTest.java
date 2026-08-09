package org.owasp.esapi.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CsrfguardProperties} and {@link CsrfguardJavascriptServletProperties}.
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class CsrfguardPropertiesTest {

    @Test
    void csrfguardPropertiesDefaults() {
        org.owasp.esapi.spring.boot.csrf.CsrfguardProperties props = new org.owasp.esapi.spring.boot.csrf.CsrfguardProperties();
        assertThat(props.isEnabled()).isFalse();
        assertThat(props.getLogger()).isEqualTo(org.owasp.esapi.spring.boot.csrf.CsrfguardProperties.LoggerType.CONSOLE);
        assertThat(props.getTokenName()).isEqualTo("OWASP_CSRFGUARD");
        assertThat(props.getTokenLength()).isEqualTo(32);
        assertThat(props.isRotateEnabled()).isFalse();
        assertThat(props.isTokenPerPageEnabled()).isFalse();
        assertThat(props.isValidationWhenNoSessionExists()).isTrue();
        assertThat(props.isTokenPerPagePrecreateEnabled()).isFalse();
        assertThat(props.isPrintConfig()).isFalse();
        assertThat(props.getPrng()).isEqualTo("SHA1PRNG");
        assertThat(props.getPrngProvider()).isEqualTo("SUN");
        assertThat(props.getNewTokenLandingPage()).isNull();
        assertThat(props.isUseNewTokenLandingPage()).isFalse();
        assertThat(props.isAjaxEnabled()).isFalse();
        assertThat(props.isProtectEnabled()).isFalse();
        assertThat(props.getSessionKey()).isEqualTo("OWASP_CSRFGUARD_KEY");
        assertThat(props.getActions()).isEmpty();
        assertThat(props.getProtectedPages()).isEmpty();
        assertThat(props.getUnprotectedPages()).isEmpty();
        assertThat(props.getProtectedMethods()).isEmpty();
        assertThat(props.getUnprotectedMethods()).isEmpty();
    }

    @Test
    void csrfguardPropertiesSettersAndGetters() {
        org.owasp.esapi.spring.boot.csrf.CsrfguardProperties props = new org.owasp.esapi.spring.boot.csrf.CsrfguardProperties();
        props.setEnabled(true);
        props.setLogger(org.owasp.esapi.spring.boot.csrf.CsrfguardProperties.LoggerType.JAVA);
        props.setTokenName("MY_TOKEN");
        props.setTokenLength(64);
        props.setRotateEnabled(true);
        props.setTokenPerPageEnabled(true);
        props.setValidationWhenNoSessionExists(false);
        props.setTokenPerPagePrecreateEnabled(true);
        props.setPrintConfig(true);
        props.setPrng("SHA256PRNG");
        props.setPrngProvider("BC");
        props.setNewTokenLandingPage("/landing");
        props.setUseNewTokenLandingPage(true);
        props.setAjaxEnabled(true);
        props.setProtectEnabled(true);
        props.setSessionKey("MY_KEY");

        assertThat(props.isEnabled()).isTrue();
        assertThat(props.getLogger()).isEqualTo(org.owasp.esapi.spring.boot.csrf.CsrfguardProperties.LoggerType.JAVA);
        assertThat(props.getTokenName()).isEqualTo("MY_TOKEN");
        assertThat(props.getTokenLength()).isEqualTo(64);
        assertThat(props.isRotateEnabled()).isTrue();
        assertThat(props.isTokenPerPageEnabled()).isTrue();
        assertThat(props.isValidationWhenNoSessionExists()).isFalse();
        assertThat(props.isTokenPerPagePrecreateEnabled()).isTrue();
        assertThat(props.isPrintConfig()).isTrue();
        assertThat(props.getPrng()).isEqualTo("SHA256PRNG");
        assertThat(props.getPrngProvider()).isEqualTo("BC");
        assertThat(props.getNewTokenLandingPage()).isEqualTo("/landing");
        assertThat(props.isUseNewTokenLandingPage()).isTrue();
        assertThat(props.isAjaxEnabled()).isTrue();
        assertThat(props.isProtectEnabled()).isTrue();
        assertThat(props.getSessionKey()).isEqualTo("MY_KEY");
    }

    @Test
    void csrfguardPropertiesToProperties() {
        org.owasp.esapi.spring.boot.csrf.CsrfguardProperties props = new org.owasp.esapi.spring.boot.csrf.CsrfguardProperties();
        props.setEnabled(true);
        props.setTokenName("TEST_TOKEN");
        props.setNewTokenLandingPage("/landing");

        java.util.Properties result = props.toProperties();
        assertThat(result).isNotNull();
        assertThat(result.get("org.owasp.csrfguard.Enabled")).isEqualTo(true);
        assertThat(result.getProperty("org.owasp.csrfguard.TokenName")).isEqualTo("TEST_TOKEN");
        assertThat(result.getProperty("org.owasp.csrfguard.NewTokenLandingPage")).isEqualTo("/landing");
    }

    @Test
    void loggerTypeEnum() {
        assertThat(org.owasp.esapi.spring.boot.csrf.CsrfguardProperties.LoggerType.CONSOLE.className())
                .isEqualTo("org.owasp.csrfguard.log.ConsoleLogger");
        assertThat(org.owasp.esapi.spring.boot.csrf.CsrfguardProperties.LoggerType.JAVA.className())
                .isEqualTo("org.owasp.csrfguard.log.JavaLogger");
        assertThat(org.owasp.esapi.spring.boot.csrf.CsrfguardProperties.LoggerType.CONSOLE
                .equals(org.owasp.esapi.spring.boot.csrf.CsrfguardProperties.LoggerType.CONSOLE)).isTrue();
    }

    @Test
    void csrfguardPropertiesToPropertiesWithMaps() {
        org.owasp.esapi.spring.boot.csrf.CsrfguardProperties props = new org.owasp.esapi.spring.boot.csrf.CsrfguardProperties();
        props.setEnabled(true);
        props.setTokenName("TEST_TOKEN");
        props.setNewTokenLandingPage("/landing");
        props.getActions().put("Logger", "org.owasp.csrfguard.log.ConsoleLogger");
        props.getProtectedPages().put("/admin", "true");
        props.getUnprotectedPages().put("/public", "true");
        props.getProtectedMethods().add("POST");
        props.getUnprotectedMethods().add("GET");

        java.util.Properties result = props.toProperties();
        assertThat(result).isNotNull();
        assertThat(result.get("org.owasp.csrfguard.Enabled")).isEqualTo(true);
        assertThat(result.getProperty("org.owasp.csrfguard.TokenName")).isEqualTo("TEST_TOKEN");
        assertThat(result.getProperty("org.owasp.csrfguard.NewTokenLandingPage")).isEqualTo("/landing");
        assertThat(result.getProperty("org.owasp.csrfguard.action.Logger")).isEqualTo("org.owasp.csrfguard.log.ConsoleLogger");
        assertThat(result.getProperty("org.owasp.csrfguard.protected./admin")).isEqualTo("true");
        assertThat(result.getProperty("org.owasp.csrfguard.unprotected./public")).isEqualTo("true");
        assertThat(result.getProperty("org.owasp.csrfguard.ProtectedMethods")).isEqualTo("POST");
        assertThat(result.getProperty("org.owasp.csrfguard.UnprotectedMethods")).isEqualTo("GET");
    }

    @Test
    void csrfguardPropertiesMapSetters() {
        org.owasp.esapi.spring.boot.csrf.CsrfguardProperties props = new org.owasp.esapi.spring.boot.csrf.CsrfguardProperties();
        java.util.Map<String, String> actions = new java.util.HashMap<>();
        actions.put("Inject", "org.owasp.csrfguard.action.Inject");
        props.setActions(actions);
        assertThat(props.getActions()).containsEntry("Inject", "org.owasp.csrfguard.action.Inject");

        java.util.Map<String, String> protectedPages = new java.util.HashMap<>();
        protectedPages.put("/api/**", "true");
        props.setProtectedPages(protectedPages);
        assertThat(props.getProtectedPages()).containsEntry("/api/**", "true");

        java.util.Map<String, String> unprotectedPages = new java.util.HashMap<>();
        unprotectedPages.put("/health", "true");
        props.setUnprotectedPages(unprotectedPages);
        assertThat(props.getUnprotectedPages()).containsEntry("/health", "true");

        java.util.Set<String> protectedMethods = new java.util.HashSet<>();
        protectedMethods.add("PUT");
        protectedMethods.add("DELETE");
        props.setProtectedMethods(protectedMethods);
        assertThat(props.getProtectedMethods()).contains("PUT", "DELETE");

        java.util.Set<String> unprotectedMethods = new java.util.HashSet<>();
        unprotectedMethods.add("OPTIONS");
        props.setUnprotectedMethods(unprotectedMethods);
        assertThat(props.getUnprotectedMethods()).contains("OPTIONS");
    }

    @Test
    void javascriptServletPropertiesDefaults() {
        org.owasp.esapi.spring.boot.csrf.CsrfguardJavascriptServletProperties props = new org.owasp.esapi.spring.boot.csrf.CsrfguardJavascriptServletProperties();
        assertThat(props.getCacheControl()).isEqualTo("private, maxage=28800");
        assertThat(props.getSourceFile()).isNull();
        assertThat(props.getPattern()).isEqualTo("/csrfguard.js");
        assertThat(props.isDomainStrict()).isTrue();
        assertThat(props.isInjectIntoAttributes()).isTrue();
        assertThat(props.isInjectGetForms()).isTrue();
        assertThat(props.isInjectFormAttributes()).isTrue();
        assertThat(props.isInjectIntoForms()).isTrue();
        assertThat(props.getRefererPattern()).isEqualTo(".*");
        assertThat(props.isRefererMatchDomain()).isTrue();
        assertThat(props.getXRequestedWith()).isEqualTo("OWASP CSRFGuard Project");
    }

    @Test
    void javascriptServletPropertiesSetters() {
        org.owasp.esapi.spring.boot.csrf.CsrfguardJavascriptServletProperties props = new org.owasp.esapi.spring.boot.csrf.CsrfguardJavascriptServletProperties();
        props.setPattern("/custom.js");
        props.setCacheControl("no-cache");
        props.setDomainStrict(false);
        props.setInjectIntoAttributes(false);
        props.setInjectGetForms(false);
        props.setInjectFormAttributes(false);
        props.setInjectIntoForms(false);
        props.setRefererPattern(".*example.*");
        props.setRefererMatchDomain(false);
        props.setSourceFile("/custom.js");
        props.setXRequestedWith("XMLHttpRequest");

        assertThat(props.getPattern()).isEqualTo("/custom.js");
        assertThat(props.getCacheControl()).isEqualTo("no-cache");
        assertThat(props.isDomainStrict()).isFalse();
        assertThat(props.isInjectIntoAttributes()).isFalse();
        assertThat(props.isInjectGetForms()).isFalse();
        assertThat(props.isInjectFormAttributes()).isFalse();
        assertThat(props.isInjectIntoForms()).isFalse();
        assertThat(props.getRefererPattern()).isEqualTo(".*example.*");
        assertThat(props.isRefererMatchDomain()).isFalse();
        assertThat(props.getSourceFile()).isEqualTo("/custom.js");
        assertThat(props.getXRequestedWith()).isEqualTo("XMLHttpRequest");
    }
}
