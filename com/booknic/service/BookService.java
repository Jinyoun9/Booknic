package com.booknic.service;

import com.booknic.entity.User;
import com.booknic.handler.BookEventWebSocketHandler;
import com.booknic.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    @Autowired
    private final BookEventWebSocketHandler webSocketHandler;
    @Autowired
    private FavoriteRepository favoriteRepository;

    public BookService(@Lazy BookEventWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    public void returnBook(String bookname, String library) {
        List<User> userList = favoriteRepository.findDistinctUsersByBooknameAndLibrary(bookname, library);
        System.out.println(userList.size());
        webSocketHandler.sendNotification(bookname, userList);
    }
}
