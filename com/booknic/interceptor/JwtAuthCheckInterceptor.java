package com.booknic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthCheckInterceptor implements AuthCheckInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(UserInfoHolder.isEmpty()){
            throw new IllegalArgumentException("Unauthorized Access");
        }
        return true;
    }
}
