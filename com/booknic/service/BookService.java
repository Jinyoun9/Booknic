package com.booknic.service;

import com.booknic.entity.Book;
import com.booknic.entity.Loan;
import com.booknic.entity.User;
import com.booknic.handler.BookEventWebSocketHandler;
import com.booknic.jwt.JwtProvider;
import com.booknic.repository.BookRepository;
import com.booknic.repository.FavoriteRepository;
import com.booknic.repository.LoanRepository;
import com.booknic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    @Autowired
    private BookRepository bookRepository;
    public BookService(@Lazy BookEventWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }
    LocalDate localDate = LocalDate.now();

    public void returnBook(String isbn, String library) {
        List<User> userList = favoriteRepository.findDistinctUsersByIsbnAndLibrary(isbn, library);
        System.out.println(userList.size());
        webSocketHandler.sendBookReturn(isbn, userList);
    }

    public void loanBook(String bookname, String library, String token, String isbn) {
        User user = userRepository.findUserById(JwtProvider.getId(token, true));
        LocalDate dueDate = localDate.plusWeeks(2);
        Loan newLoan = Loan.builder()
                .bookname(bookname)
                .library(library)
                .duedate(dueDate)
                .name(user.getName())
                .user(user)
                .isbn(isbn)
                .build();
        loanRepository.save(newLoan);
    }
    public Integer fetchRemain(String isbn, String library) {
        Book book = bookRepository.findDistinctBookByIsbnAndLibrary(isbn, library);
        return book != null ? book.getRemain() : null;
    }

}
