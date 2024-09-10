package com.booknic.controller;

import com.booknic.dto.RegisterDto;
import com.booknic.entity.User;
import com.booknic.repository.UserRepository;
import com.booknic.result.RegisterResult;
import com.booknic.service.EmailService;
import com.booknic.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private RegisterService registerService;

    @GetMapping("/checkid")
    public Boolean checkIdAvailability(@RequestParam String id, @RequestParam String role){
        return registerService.fetchExistById(id, role);
    }
    @GetMapping("/checkemail")
    public Boolean checkEmailAvailability(@RequestParam String email, @RequestParam String role){
        return registerService.fetchExistByEmail(email, role);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterDto params) {
        RegisterResult signUpRes = registerService.trySignUp(params);
        return switch (signUpRes.getStatus()) {
            case "SUCCESS_USER", "SUCCESS_ADMIN" -> ResponseEntity.ok().body(signUpRes.getMessage());
            case "ID_ALREADY_EXISTS", "EMAIL_ALREADY_EXISTS" ->
                    ResponseEntity.status(HttpStatus.CONFLICT).body(signUpRes.getMessage());
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(signUpRes.getMessage());
        };
    }

    @PostMapping("/mailConfirm")
    public String mailConfirm(@RequestBody Map<String, String> emailInfo) throws Exception {
        return emailService.sendSimpleMessage(emailInfo);
    }
}
