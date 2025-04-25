package com.personal.urlshortener.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FaviconFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        String path = httpReq.getRequestURI();

        // If request is for favicon.ico, skip the rest
        if (path.equals("/favicon.ico")) {
            httpRes.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
            return;
        }

        // Otherwise, continue with the filter chain
        chain.doFilter(request, response);
    }
}
