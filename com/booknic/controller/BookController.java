package com.booknic.controller;

import com.booknic.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/return")
    public void returnBook(@RequestParam String bookname, @RequestParam String library) {
        System.out.println(bookname);
        bookService.returnBook(bookname, library);
    }
}
