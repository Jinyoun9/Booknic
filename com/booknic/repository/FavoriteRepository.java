package com.booknic.repository;

import com.booknic.entity.Favoritebook;
import com.booknic.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favoritebook, String> {
    void deleteFavoriteBookByUserAndBooknameAndLibrary(User user, String bookname, String library);
    List<Favoritebook> findFavoritebooksByUser(User user);

    @Query("SELECT DISTINCT fb.user FROM Favoritebook fb WHERE fb.bookname = :bookname AND fb.library = :library")
    List<User> findDistinctUsersByBooknameAndLibrary(@Param("bookname") String bookname, @Param("library") String library);
}

