package com.booknic.service;

import com.booknic.dto.LoanDto;
import com.booknic.entity.Loan;
import com.booknic.assembler.LoanAssembler;
import com.booknic.entity.User;
import com.booknic.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Loan editDuedate(User user, String library, String bookname){
        Loan loan = loanRepository.findLoanByUserAndLibraryAndBookname(user, library, bookname);
        loan.setDuedate(loan.getDuedate().plusWeeks(1));
        return loan;
    }
}
