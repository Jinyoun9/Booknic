package com.booknic.controller;

import com.booknic.dto.BookDto;
import com.booknic.entity.Favoritebook;
import com.booknic.entity.User;
import com.booknic.repository.FavoriteRepository;
import com.booknic.repository.UserRepository;
import com.booknic.service.AuthService;
import com.booknic.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.booknic.jwt.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/fav")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<?> toggleFavorite(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> params) {
        try {
            String bookname = params.get("bookname");
            String library = params.get("library");
            String userId = JwtProvider.getId(token, true);
            User user = authService.getUser(userId);
            if(user != null){
                favoriteService.addFavoriteBook(bookname, library, user);
                return ResponseEntity.ok("즐겨찾기 업데이트 성공");
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자에게 접근 권한이 없습니다");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }
    @GetMapping("/load")
    public ResponseEntity<?> getFavoriteBook(@RequestHeader("Authorization") String token){
        try{
            String id = JwtProvider.getId(token, true);
            User user = authService.getUser(id);
            List<BookDto> favoritebookList = favoriteService.getFavoriteBooks(user);
            return ResponseEntity.ok(favoritebookList);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 접근 권한이 없습니다.");
        }
    }

}
