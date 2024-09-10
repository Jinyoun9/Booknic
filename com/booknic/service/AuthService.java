package com.booknic.service;

import com.booknic.entity.Admin;
import com.booknic.entity.User;
import com.booknic.interceptor.AdminInfoHolder;
import com.booknic.interceptor.UserInfoHolder;
import com.booknic.repository.AdminRepository;
import com.booknic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public boolean login(String id, String password, String role) {
        if (role.equals("user")){
            User user = userRepository.findUserById(id);
            if (user != null) {
                if(encoder.matches(password, user.getPassword())){
                    UserInfoHolder.setUserInfo(user);
                    return true;
                }
            }
        }
        else{
            Admin admin = adminRepository.findAdminById(id);
            if (admin != null) {
                if (encoder.matches(password, admin.getPassword())){
                    AdminInfoHolder.setAdminInfo(admin);
                    return true;
                }
            }
        }
        return false;
    }
}
