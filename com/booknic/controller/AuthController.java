package com.booknic.controller;

import com.booknic.dto.LoginDto;
import com.booknic.jwt.JwtProvider;
import com.booknic.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Map;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "https://localhost:3000")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthService authService;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @GetMapping("/jwt")
    public ResponseEntity<Void> refreshAccessToken(
            @RequestHeader(JwtProvider.HEADER_AUTHORIZATION) String authorizationHeader,
            @CookieValue(JwtProvider.REFRESH_TOKEN_COOKIE_NAME) String refreshToken) {

        String accessToken = JwtProvider.extractToken(authorizationHeader);
        String role = JwtProvider.extractRole(accessToken);
        String newAccessToken = jwtProvider.refreshAccessToken(accessToken, refreshToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(JwtProvider.HEADER_AUTHORIZATION, newAccessToken);

        return ResponseEntity.ok().headers(httpHeaders).build();
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto params){
        String id = params.getId();
        String password = params.getPassword();
        String role = params.getRole();
        if(authService.login(id, password, role)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("false");
    }
}