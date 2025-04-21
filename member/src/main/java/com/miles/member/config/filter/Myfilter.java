package com.miles.member.config.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@Component
public class Myfilter implements Filter {


    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Principal userPrincipal = request.getUserPrincipal();
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }
}
