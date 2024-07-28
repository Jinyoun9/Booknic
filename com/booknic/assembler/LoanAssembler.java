package com.booknic.assembler;

import com.booknic.dto.LoanDto;
import com.booknic.entity.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanAssembler {
    public LoanDto toDto(Loan loan) {
        LoanDto loanDto = new LoanDto();
        loanDto.setBookname(loan.getBookname());
        loanDto.setName(loan.getName());
        loanDto.setDuedate(loan.getDuedate());
        return loanDto;
    }
}
