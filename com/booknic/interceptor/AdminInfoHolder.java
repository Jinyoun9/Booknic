package com.booknic.interceptor;

import com.booknic.entity.Admin;

import java.util.Optional;

public class AdminInfoHolder {
    private static final ThreadLocal<Admin> adminInfo = new ThreadLocal<>();
    public static void setAdminInfo(Admin admin) {
        adminInfo.set(admin);
    }
    public static String getRole(){
        return "staff";
    }
    public static Admin getAdminInfo() {
        return adminInfo.get();
    }

    public static void releaseAdminInfo() {
        adminInfo.remove();
    }

    public static boolean isEmpty() {
        return adminInfo.get() == null;
    }

}