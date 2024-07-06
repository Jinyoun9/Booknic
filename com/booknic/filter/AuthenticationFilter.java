package com.booknic.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Filter;

@WebFilter("/*")
public abstract class AuthenticationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        HttpSession httpSession = httpServletRequest.getSession(false);
        String uri = httpServletRequest.getRequestURI();

        if(httpSession == null || httpSession.getAttribute("authenticatedUser") == null){
            if(!uri.endsWith("/login") && !uri.startsWith("/public")){
                httpServletResponse.sendRedirect("/login");
                return;
            }
        }
        else{
            String userRole = (String) httpSession.getAttribute("userRole");
            if(uri.startsWith("/admin") && !"ADMIN".equals(userRole)){
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
