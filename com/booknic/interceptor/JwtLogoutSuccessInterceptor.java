package com.booknic.interceptor;

import com.booknic.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtLogoutSuccessInterceptor implements LogoutSuccessInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        CookieUtil.deleteCookie(request, response, JwtProvider.REFRESH_TOKEN_COOKIE_NAME);
        response.addHeader(JwtProvider.HEADER_AUTHORIZATION, "logout");

        return true;
    }
}
