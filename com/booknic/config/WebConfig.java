package com.booknic.config;

import com.booknic.interceptor.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuthCheckInterceptor authCheckInterceptor;
    private final AuthInfoHoldInterceptor authInfoHoldInterceptor;
    private final LoginSuccessInterceptor loginSuccessInterceptor;
    private final LogoutSuccessInterceptor logoutSuccessInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInfoHoldInterceptor)
                .order(1)
                .addPathPatterns("/auth/**");

        registry.addInterceptor(authCheckInterceptor)
                .order(2)
                .addPathPatterns("/user")
                .excludePathPatterns("/auth/login", "/auth/signUp", "/auth/jwts");

        registry.addInterceptor(loginSuccessInterceptor)
                .order(3)
                .addPathPatterns("/auth/login");

        registry.addInterceptor(logoutSuccessInterceptor)
                .order(4)
                .addPathPatterns("/auth/logout");
    }
}