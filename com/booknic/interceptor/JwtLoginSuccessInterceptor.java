package com.booknic.interceptor;

import com.booknic.entity.JwtSubject;
import com.booknic.jwt.JwtProperties;
import com.booknic.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class JwtLoginSuccessInterceptor implements LoginSuccessInterceptor {


    private final JwtProperties jwtProperties;
    private final JwtProvider jwtProvider;
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        JwtSubject subject = getSubject();
        String role = getRole();
        if (subject != null) {
            String refreshToken = jwtProvider.generateRefreshToken(subject, role);
            String accessToken = jwtProvider.generateAccessToken(subject, role);
            addRefreshTokenToCookie(request, response, refreshToken);
            response.addHeader(JwtProvider.HEADER_AUTHORIZATION, accessToken);
        }
    }

    private JwtSubject getSubject() {
        if (!UserInfoHolder.isEmpty()) {
            return UserInfoHolder.getUserInfo();
        } else if (!AdminInfoHolder.isEmpty()) {
            return AdminInfoHolder.getAdminInfo();
        }
        return null;
    }

    private String getRole() {
        if (!UserInfoHolder.isEmpty()) {
            return UserInfoHolder.getRole();
        } else if (!AdminInfoHolder.isEmpty()) {
            return AdminInfoHolder.getRole();
        }
        return null;
    }

    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) jwtProperties.getRefreshExpiration();
        CookieUtil.addCookie(response, JwtProvider.REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }
}
