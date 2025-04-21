package com.miles.order.config;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2020/4/18 17:15
 */
@Configuration
public class CasAutoConfig {

    @Value("${cas.server-url-prefix}")
    private String serverUrlPrefix;

    @Value("${cas.server-login-url}")
    private String serverLoginUrl;

    @Value("${cas.client-host-url}")
    private String clientHostUrl;

    /**
     * 授权过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean filterAuthenticationRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthenticationFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        Map<String,String> initParameters = new HashMap<String, String>();
        initParameters.put("casServerLoginUrl", serverUrlPrefix);
        initParameters.put("serverName", clientHostUrl);
        //忽略的url，"|"分隔多个url
        initParameters.put("ignorePattern", "/logout/success|/index");
        registration.setInitParameters(initParameters);
        // 设定加载的顺序
        registration.setOrder(1);
        return registration;
    }
}