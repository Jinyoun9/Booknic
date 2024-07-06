package com.booknic.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public abstract class LoggingFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException{
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        long startTime = System.currentTimeMillis();

        try{
            chain.doFilter(request, response);
        }finally {
            long duration = System.currentTimeMillis() - startTime;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            System.out.println("Request to " + httpServletRequest.getRequestURI() + " took " + duration + " ms");
            System.out.println("Response status: " + httpServletResponse.getStatus());
        }

    }
}
