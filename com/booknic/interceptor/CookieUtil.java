package com.booknic.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class CookieUtil {

    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        logCookieDetails(cookie);
    }
    private static void logCookieDetails(Cookie cookie) {
        logger.info("Cookie details:");
        logger.info("Name: {}", cookie.getName());
        logger.info("Value: {}", cookie.getValue());
        logger.info("Path: {}", cookie.getPath());
        logger.info("Max Age: {}", cookie.getMaxAge());
        logger.info("Domain: {}", cookie.getDomain());
        logger.info("Secure: {}", cookie.getSecure());
        logger.info("HttpOnly: {}", cookie.isHttpOnly());
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return;

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                response.addCookie(cookie);
            }
        }
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(name))
                .map(Cookie::getValue)
                .findAny().orElse(null);
    }
}