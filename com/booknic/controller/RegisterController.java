package com.booknic.controller;

import com.booknic.entity.User;
import com.booknic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    UserRepository userRepository;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/checkid")
    public Map<String, Boolean> checkIdAvailability(@RequestParam String id){
        boolean idAvailable = userRepository.existsById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", idAvailable);
        return response;
    }
    @GetMapping("/checkemail")
    public Map<String, Boolean> checkEmailAvailability(@RequestParam String email){
        boolean emailAvailable = userRepository.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", emailAvailable);
        return response;
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> params){
        User user = new User();

        String id = params.get("id");
        String encodedPW = encoder.encode(params.get("password"));
        String name = params.get("username");
        String gender = params.get("gender");
        String email = params.get("email");

        if(!userRepository.existsByEmail(email) && !userRepository.existsById(id)){
            user.setId(id);
            user.setPassword(encodedPW);
            user.setName(name);
            user.setGender(gender);
            user.setEmail(email);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("false");
        }
    }
}
