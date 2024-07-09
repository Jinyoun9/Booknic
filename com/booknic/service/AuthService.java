package com.booknic.service;

import com.booknic.entity.User;
import com.booknic.interceptor.UserInfoHolder;
import com.booknic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    public boolean login(String id, String password) {
        User user = userRepository.findUserById(id);
        if (user != null) {
            UserInfoHolder.setUserInfo(user);
            return true;
        }
        return false;
    }
}
