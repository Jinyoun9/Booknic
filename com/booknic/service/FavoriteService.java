package com.booknic.service;

import com.booknic.dto.FavoritebookDto;
import com.booknic.entity.Favoritebook;
import com.booknic.entity.User;
import com.booknic.repository.FavoriteRepository;
import com.booknic.assembler.BookAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private BookAssembler bookAssembler;
    public Favoritebook buildFavoriteBook(String bookname, String library, User user){
        return Favoritebook.builder()
                .bookname(bookname)
                .user(user)
                .library(library)
                .build();
    }
    @Transactional
    public void addFavoriteBook(String bookname, String library, User user) throws IllegalAccessException{
        favoriteRepository.save(buildFavoriteBook(bookname, library, user));
    }
    @Transactional
    public void deleteFavoriteBook(User user, String bookname, String library){
        favoriteRepository.deleteFavoriteBookByUserAndBooknameAndLibrary(user, bookname, library);
    }
    @Transactional
    public List<FavoritebookDto> getFavoriteBooks(User user){
        List<Favoritebook> favoritebookList = favoriteRepository.findFavoritebooksByUser(user);
        List<FavoritebookDto> favoritebookDtoList =  new ArrayList<>();

        for (Favoritebook favoritebook : favoritebookList){
            FavoritebookDto favoritebookDto = bookAssembler.toDto(favoritebook);
            favoritebookDtoList.add(favoritebookDto);
        }
        return favoritebookDtoList;
    }
    public List<User> findUsersByFavoriteBook(String bookname, String library) {
        return favoriteRepository.findDistinctUsersByBooknameAndLibrary(bookname, library);
    }
}
