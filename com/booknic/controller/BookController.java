package com.booknic.controller;

import com.booknic.jwt.JwtProvider;
import com.booknic.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/return")
    public void returnBook(@RequestParam String bookname, @RequestParam String library) {
        bookService.returnBook(bookname, library);
    }
    @GetMapping("/loan")
    public void loanBook(@RequestParam String bookname,
                         @RequestParam String library,
                         @RequestParam String token,
                         @RequestParam String isbn){
        bookService.loanBook(bookname, library, token, isbn);
    }
    @GetMapping("/remain")
    public ResponseEntity<?> getRemain(@RequestParam String isbn,
                                    @RequestParam String library){
        int remain = bookService.fetchRemain(isbn, library);
        if(remain > 0){
            return ResponseEntity.ok().body(remain);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books remaining.");
    }
}
