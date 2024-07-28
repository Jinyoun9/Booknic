package com.booknic.service;

import com.booknic.entity.Loan;
import com.booknic.entity.User;
import com.booknic.handler.BookEventWebSocketHandler;
import com.booknic.jwt.JwtProvider;
import com.booknic.repository.FavoriteRepository;
import com.booknic.repository.LoanRepository;
import com.booknic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    @Autowired
    private final BookEventWebSocketHandler webSocketHandler;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    public BookService(@Lazy BookEventWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }
    LocalDateTime localDateTime = LocalDateTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public void returnBook(String bookname, String library) {
        List<User> userList = favoriteRepository.findDistinctUsersByBooknameAndLibrary(bookname, library);
        System.out.println(userList.size());
        webSocketHandler.sendNotification(bookname, userList);
    }
    public void loanBook(String bookname, String library, String token) {
        User user = userRepository.findUserById(JwtProvider.getId(token, true));
        LocalDateTime dueDateTime = localDateTime.plusWeeks(2);
        String dueDate = dueDateTime.format(dateTimeFormatter);
        Loan newLoan = Loan.builder()
                .bookname(bookname)
                .library(library)
                .duedate(dueDate)
                .name(user.getName())
                .user(user)
                .build();
        loanRepository.save(newLoan);
    }
}
