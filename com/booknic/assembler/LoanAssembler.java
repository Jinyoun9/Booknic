package com.booknic.assembler;

import com.booknic.dto.LoanDto;
import com.booknic.entity.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanAssembler {
    public LoanDto toDto(Loan loan) {
        return LoanDto.builder()
                .name(loan.getName())
                .bookname(loan.getBookname())
                .isbn(loan.getIsbn())
                .library(loan.getLibrary())
                .duedate(loan.getDuedate())
                .build();
    }
}
