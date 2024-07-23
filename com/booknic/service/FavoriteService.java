package com.booknic.service;

import com.booknic.entity.Favoritebook;
import com.booknic.entity.User;
import com.booknic.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FavoriteService {
    @Autowired
    FavoriteRepository favoriteRepository;
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
}
