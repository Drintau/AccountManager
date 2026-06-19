package drintau.accountmanager.webserver.config;

import drintau.accountmanager.webserver.filter.StaticResourceFilter;
import drintau.accountmanager.webserver.filter.TraceIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<StaticResourceFilter> staticResourceFilterRegistration(StaticResourceFilter staticResourceFilter) {
        FilterRegistrationBean<StaticResourceFilter> registration = new FilterRegistrationBean<>(staticResourceFilter);
        registration.addUrlPatterns("*.map");
        registration.setName("staticResourceFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<TraceIdFilter> traceIdFilterRegistration(TraceIdFilter traceIdFilter) {
        FilterRegistrationBean<TraceIdFilter> registration = new FilterRegistrationBean<>(traceIdFilter);
        registration.addUrlPatterns("/*");
        registration.setName("traceIdFilter");
        registration.setOrder(2);
        return registration;
    }

}
