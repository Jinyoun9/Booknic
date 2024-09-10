package com.booknic.service;

import com.booknic.dto.LoanDto;
import com.booknic.entity.Loan;
import com.booknic.assembler.LoanAssembler;
import com.booknic.entity.User;
import com.booknic.handler.BookEventWebSocketHandler;
import com.booknic.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AdminService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanAssembler loanAssembler;
    @Autowired
    private BookEventWebSocketHandler webSocketHandler;
    private final LocalDate localDate = LocalDate.now();

    public List<LoanDto> fetchOverdueUser(String library){
        List<LoanDto> loanDtoList = new ArrayList<>();
        List<Loan> loanList = loanRepository.findLoansByDuedateBeforeAndLibrary(localDate, library);

        for(Loan loan : loanList){
            LoanDto loanDto = loanAssembler.toDto(loan);
            loanDtoList.add(loanDto);
        }
        return loanDtoList;
    }
    public Loan editDuedate(User user, String library, String isbn){
        Loan loan = loanRepository.findDistinctLoanByNameAndLibraryAndIsbn(user.getName(), library, isbn);
        System.out.println(loan);
        loan.setDuedate(loan.getDuedate().plusWeeks(1));
        webSocketHandler.sendDueExtend(isbn, user);
        return loan;
    }
    public List<LoanDto> fetchLoanDtos(String library){
        List<Loan> loans = loanRepository.findLoansByLibrary(library);
        List<LoanDto> loanDtos = new ArrayList<>();
        for (Loan loan : loans){
            loanDtos.add(loanAssembler.toDto(loan));
        }
        return loanDtos;
    }

}
