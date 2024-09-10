package com.booknic.controller;

import com.booknic.dto.FavoritebookDto;
import com.booknic.entity.User;
import com.booknic.service.AuthService;
import com.booknic.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.booknic.jwt.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/fav")
public class FavoriteController {
    @Autowired
    private final FavoriteService favoriteService;
    @PostMapping("/register")
    public ResponseEntity<?> addFavorite(@RequestHeader("Authorization") String token, @RequestBody FavoritebookDto params) throws IllegalAccessException {
        if (favoriteService.addFavoriteBook(token, params)){
            return ResponseEntity.ok().body("즐겨찾기 성공");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 즐겨찾기 되어있습니다.");
    }
    @GetMapping("/load")
    public ResponseEntity<?> getFavoriteBook(@RequestHeader("Authorization") String token){
        List<FavoritebookDto> favoritebookDtos = favoriteService.fetchFavoriteBooks(token);
        return ResponseEntity.ok().body(favoritebookDtos);
    }
    @GetMapping("/check")
    public Boolean isFavoriteBook(@RequestHeader("Authorization") String token, @RequestParam String isbn13, @RequestParam String library){
        return favoriteService.checkFavoriteBook(token, isbn13, library);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFavoriteBook(@RequestHeader("Authorization") String token, @RequestParam String isbn13, @RequestParam String library){
        if (favoriteService.deleteFavoriteBook(token, isbn13, library)){
            return ResponseEntity.ok().body("즐겨찾기가 성공적으로 삭제되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("현재 요청을 처리하는 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
    }
}
