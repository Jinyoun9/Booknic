package com.booknic.repository;

import com.booknic.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
    Book findDistinctBookByIsbnAndLibrary(String isbn, String library);
}
