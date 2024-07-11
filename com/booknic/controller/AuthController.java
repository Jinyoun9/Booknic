package com.booknic.controller;

import com.booknic.jwt.JwtProvider;
import com.booknic.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "https://localhost:3000")
@RequiredArgsConstructor
public class AuthController {

    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @GetMapping("/jwt")
    public ResponseEntity<Void> refreshAccessToken(
            @RequestHeader(JwtProvider.HEADER_AUTHORIZATION) String authorizationHeader,
            @CookieValue(JwtProvider.REFRESH_TOKEN_COOKIE_NAME) String refreshToken) {

        String accessToken = JwtProvider.extractToken(authorizationHeader);
        String newAccessToken = jwtProvider.refreshAccessToken(accessToken, refreshToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(JwtProvider.HEADER_AUTHORIZATION, newAccessToken);

        return ResponseEntity.ok().headers(httpHeaders).build();
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> params){
        String id = params.get("id");
        String password = params.get("password");
        if(authService.login(id, password)){
            return ResponseEntity.ok().build(); // 로그인 성공 시 200 OK 반환
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("false"); // 로그인 실패 시 401 Unauthorized 반환
    }

}