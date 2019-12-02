package org.owasp.esapi.spring.boot;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.contrib.spring.encryptedproperty.EncryptedPropertyPlaceholderConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@ConditionalOnClass(ESAPI.class)
@ConditionalOnProperty(prefix = EsapiProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties(EsapiProperties.class)
public class EsapiAutoConfiguration implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	 
    @Bean
	public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(){
    	EncryptedPropertyPlaceholderConfigurer placeholderConfigurer = new EncryptedPropertyPlaceholderConfigurer();
    	PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	    Resource resource = resolver.getResource("classpath:application.properties");
	    placeholderConfigurer.setLocation(resource);
	    return placeholderConfigurer;
	}
	/*
	@Bean
    @ConditionalOnMissingBean
	public ServletRegistrationBean<JavaScriptServlet> javaScriptServlet(EsapiProperties properties) throws Exception {

		 ArrayList list = new ArrayList();
	     list.add( new WindowsCodec() );
	     list.add( new MySQLCodec(Mode.STANDARD) );
	     list.add( new PercentCodec() );
	     Encoder encoder = new DefaultEncoder( list );
	     String clean = encoder.canonicalize( "");

		
		ServletRegistrationBean<JavaScriptServlet> registrationBean = new ServletRegistrationBean<JavaScriptServlet>();
        
		JavaScriptServlet javaScriptServlet = new JavaScriptServlet();
		
		registrationBean.setServlet(javaScriptServlet);
		
		// 默认参数
		CsrfguardJavascriptServletProperties javascript = properties.getJavascript();
		registrationBean.addInitParameter("cache-control", javascript.getCacheControl());
		registrationBean.addInitParameter("domain-strict", Boolean.toString(javascript.isDomainStrict()));
		registrationBean.addInitParameter("inject-into-attributes", Boolean.toString(javascript.isInjectIntoAttributes()));
		registrationBean.addInitParameter("inject-get-forms", Boolean.toString(javascript.isInjectGetForms()));
		registrationBean.addInitParameter("inject-form-attributes", Boolean.toString(javascript.isInjectFormAttributes()));
		registrationBean.addInitParameter("inject-into-forms", Boolean.toString(javascript.isInjectIntoForms()));
		registrationBean.addInitParameter("referer-pattern", javascript.getRefererPattern());
		registrationBean.addInitParameter("referer-match-domain", Boolean.toString(javascript.isRefererMatchDomain()));
		registrationBean.addInitParameter("source-file", javascript.getSourceFile());
		registrationBean.addInitParameter("x-requested-with", javascript.getXRequestedWith());
		registrationBean.addUrlMappings(javascript.getPattern());

        return registrationBean;
    }
	
	@Bean
	@ConditionalOnProperty(prefix = "shiro", value = "session-creation-enabled", havingValue = "true")
	protected ServletListenerRegistrationBean<CsrfGuardHttpSessionListener> csrfGuardHttpSessionListener()
			throws Exception {
		
		ServletListenerRegistrationBean<CsrfGuardHttpSessionListener> registrationBean = new ServletListenerRegistrationBean<CsrfGuardHttpSessionListener>();
		registrationBean.setListener(new CsrfGuardHttpSessionListener());
		registrationBean.setOrder(Integer.MIN_VALUE);

		return registrationBean;
	}
	
	
	@Bean
    @ConditionalOnMissingBean
    protected FilterRegistrationBean<CsrfGuardFilter> csrfGuardFilter() throws Exception {

        FilterRegistrationBean<CsrfGuardFilter> filterRegistrationBean = new FilterRegistrationBean<CsrfGuardFilter>();
        filterRegistrationBean.setFilter(new CsrfGuardFilter());
        filterRegistrationBean.setOrder(Integer.MIN_VALUE);
        
        return filterRegistrationBean;
    }*/
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
}
 