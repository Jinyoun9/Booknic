package com.booknic.repository;

import com.booknic.entity.Favoritebook;
import com.booknic.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favoritebook, String> {

    void deleteFavoriteBookByUserAndIsbnAndLibrary(User user, String isbn, String library);
    boolean existsByUserAndIsbnAndLibrary(User user, String isbn, String library);
    List<Favoritebook> findFavoritebooksByUser(User user);

    @Query("SELECT DISTINCT fb.user FROM Favoritebook fb WHERE fb.isbn = :isbn AND fb.library = :library")
    List<User> findDistinctUsersByIsbnAndLibrary(@Param("isbn") String isbn, @Param("library") String library);
}

