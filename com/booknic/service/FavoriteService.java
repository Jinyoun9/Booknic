package com.booknic.service;

import com.booknic.dto.FavoritebookDto;
import com.booknic.entity.Favoritebook;
import com.booknic.entity.User;
import com.booknic.jwt.JwtProvider;
import com.booknic.repository.FavoriteRepository;
import com.booknic.assembler.BookAssembler;

import com.booknic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookAssembler bookAssembler;
    @Autowired
    public JwtProvider jwtProvider;
    public Favoritebook buildFavoriteBook(String bookname, String library, User user, String isbn){
        return Favoritebook.builder()
                .bookname(bookname)
                .user(user)
                .library(library)
                .isbn(isbn)
                .build();
    }
    @Transactional
    public boolean addFavoriteBook(String token, FavoritebookDto params) throws IllegalAccessException{
        String isbn = params.getIsbn();
        String library = params.getLibrary();
        String bookname = params.getBookname();
        String userId = JwtProvider.getId(token, true);
        User user = userRepository.findUserById(userId);
        if(user != null && !favoriteRepository.existsByUserAndIsbnAndLibrary(user, isbn, library)){
            favoriteRepository.save(buildFavoriteBook(bookname, library, user, isbn));
            return true;
        }
        else if(favoriteRepository.existsByUserAndIsbnAndLibrary(user, isbn, library)){
            throw new IllegalAccessException("이미 즐겨찾기 되어 있습니다.");
        }
        return false;
    }
    @Transactional
    public boolean deleteFavoriteBook(String token, String isbn13, String library){
        String id = JwtProvider.getId(token, true);
        User user = userRepository.findUserById(id);
        if (favoriteRepository.existsByUserAndIsbnAndLibrary(user, isbn13, library)){
            favoriteRepository.deleteFavoriteBookByUserAndIsbnAndLibrary(user, isbn13, library);
            return true;
        }
        return false;
    }
    @Transactional
    public List<FavoritebookDto> fetchFavoriteBooks(String token){
        String id = JwtProvider.getId(token, true);
        User user = userRepository.findUserById(id);
        List<Favoritebook> favoritebookList = favoriteRepository.findFavoritebooksByUser(user);
        List<FavoritebookDto> favoritebookDtoList =  new ArrayList<>();

        for (Favoritebook favoritebook : favoritebookList){
            FavoritebookDto favoritebookDto = bookAssembler.toDto(favoritebook);
            favoritebookDtoList.add(favoritebookDto);
        }

        return favoritebookDtoList;
    }
    @Transactional
    public Boolean checkFavoriteBook(String token, String isbn13, String library){
        String id = JwtProvider.getId(token, true);
        User user = userRepository.findUserById(id);
        return favoriteRepository.existsByUserAndIsbnAndLibrary(user, isbn13, library);
    }
    public List<User> findUsersByFavoriteBook(String isbn, String library) {
        return favoriteRepository.findDistinctUsersByIsbnAndLibrary(isbn, library);
    }
}
