package com.booknic.repository;

import com.booknic.entity.Favoritebook;
import com.booknic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favoritebook, String> {
    void deleteFavoriteBookByUserAndBooknameAndLibrary(User user, String bookname, String library);
}

