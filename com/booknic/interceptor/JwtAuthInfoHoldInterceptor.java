package com.booknic.interceptor;


import com.booknic.entity.User;
import com.booknic.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class JwtAuthInfoHoldInterceptor implements AuthInfoHoldInterceptor{
    private final JwtProvider jwtProvider;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader(JwtProvider.HEADER_AUTHORIZATION);
        String accessToken = JwtProvider.extractToken(header);

        if (jwtProvider.verifyToken(accessToken)){
            UserInfoHolder.releaseUserInfo();
            UserInfoHolder.setUserInfo(jwtProvider.extractUserInfo(accessToken));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfoHolder.releaseUserInfo();
    }
}
