package com.booknic.interceptor;

import com.booknic.entity.User;
import com.booknic.jwt.JwtProperties;
import com.booknic.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Order;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class JwtLoginSuccessInterceptor implements LoginSuccessInterceptor {

    private final JwtProperties jwtProperties;
    private final JwtProvider jwtProvider;
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        User user = UserInfoHolder.getUserInfo();
        String refreshToken = jwtProvider.generateRefreshToken(user);
        String accessToken = jwtProvider.generateAccessToken(user);

        addRefreshTokenToCookie(request, response, refreshToken);
        response.addHeader(JwtProvider.HEADER_AUTHORIZATION, accessToken);
    }
    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) jwtProperties.getRefreshExpiration();
        CookieUtil.addCookie(response, JwtProvider.REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }


}
