package com.personal.urlshortener.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class SuspiciousRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        //Get the URI sent to the server
        String path = httpServletRequest.getRequestURI();

        //Check for suspicious string patterns from the request
        if(isPathTraversal(path)){
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Suspicious Request Blocked.");
            return;
        }

        //Check if the request is from a bot
        if(isBotRequest(httpServletRequest)){
            Logger.getLogger(SuspiciousRequestFilter.class.getName())
                    .info("Bot detected: " + httpServletRequest.getHeader("User-Agent"));
            httpServletRequest.setAttribute("isBot", true);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }


    private boolean isPathTraversal(String path) {
        String lowerPath = path.toLowerCase();

        return lowerPath.contains("../") ||
                lowerPath.contains("..\\") ||
                lowerPath.contains("%2e%2e") ||
                lowerPath.contains("%2f") ||
                lowerPath.contains("%5c");
    }

    private boolean isBotRequest(HttpServletRequest request){
        String userAgent = request.getHeader("User-Agent");
        if(userAgent == null || userAgent.isEmpty())
            throw new RuntimeException("No Valid User Agent Found!!");

        String userAgentLowerCase = userAgent.toLowerCase();
        return userAgentLowerCase.contains("bot")
                || userAgentLowerCase.contains("spider")
                || userAgentLowerCase.contains("crawler");


    }

}
