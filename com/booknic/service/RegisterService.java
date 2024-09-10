package com.booknic.service;

import com.booknic.dto.RegisterDto;
import com.booknic.entity.Admin;
import com.booknic.entity.User;
import com.booknic.repository.AdminRepository;
import com.booknic.repository.UserRepository;
import com.booknic.result.RegisterResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
public class RegisterService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public boolean fetchExistById(String id, String role) {
        if(role.equals("user")){
            return userRepository.existsById(id);
        }
        return adminRepository.existsById(id);
    }

    public boolean fetchExistByEmail(String email, String role) {
        if (role.equals("user")){
            return userRepository.existsByEmail(email);
        }
        return adminRepository.existsByEmail(email);
    }

    public RegisterResult trySignUp(RegisterDto params){
        String role = params.getRole();
        String id = params.getId();
        String encodedPW = encoder.encode(params.getRawPW());
        String name = params.getName();
        String gender = params.getGender();
        String email = params.getEmail();

        if(!fetchExistById(id, role) && !fetchExistByEmail(email, role)){
            if (role.equals("user")) {
                User user = User.builder()
                        .id(id)
                        .password(encodedPW)
                        .name(name)
                        .gender(gender)
                        .email(email)
                        .build();
                userRepository.save(user);
                return new RegisterResult("SUCCESS_USER", "사용자 회원가입이 정상적으로 완료되었습니다.");
            }

            else{
                String library = params.getLibrary();
                Admin admin = Admin.builder()
                        .id(id)
                        .password(encodedPW)
                        .name(name)
                        .gender(gender)
                        .email(email)
                        .library(library)
                        .build();
                adminRepository.save(admin);
                return new RegisterResult("SUCCESS_ADMIN", "직원 회원가입이 정상적으로 완료되었습니다.");
            }
        }
        else if(fetchExistById(id, role)){
            return new RegisterResult("ID_ALREADY_EXISTS", "이미 존재하는 아이디입니다.");
        }
        else if (fetchExistByEmail(email, role)){
            return new RegisterResult("EMAIL_ALREADY_EXISTS", "이미 존재하는 이메일입니다.");
        }
        return new RegisterResult("INTERNAL_SERVER_ERROR", "요청을 처리하던 중 서버에 문제가 생겼습니다.");
    }
}
