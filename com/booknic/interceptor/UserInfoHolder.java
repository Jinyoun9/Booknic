package com.booknic.interceptor;

import com.booknic.entity.User;

import java.util.Optional;

public class UserInfoHolder {
    private static final ThreadLocal<User> userInfo = new ThreadLocal<>();
    public static void setUserInfo(User user) {
        userInfo.set(user);
    }

    public static User getUserInfo() {
        return userInfo.get();
    }

    public static void releaseUserInfo() {
        userInfo.remove();
    }

    public static boolean isEmpty() {
        return userInfo.get() == null;
    }

}
