package com.booknic.controller;

import com.booknic.entity.User;
import com.booknic.repository.UserRepository;
import com.booknic.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.booknic.jwt.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "https://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/fav")
public class FavoriteController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FavoriteService favoriteService;
    @PostMapping("/register")
    public ResponseEntity<?> toggleFavorite(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> params) {
        System.out.println(token);
        try {
            String bookname = params.get("bookname");
            String library = params.get("library");
            String userId = JwtProvider.getId(token, true);
            User user = userRepository.findUserById(userId);
            if (user != null) {
                    favoriteService.addFavoriteBook(bookname, library, user);
                return ResponseEntity.ok("즐겨찾기 업데이트 성공");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자에게 접근 권한이 없습니다");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

}
