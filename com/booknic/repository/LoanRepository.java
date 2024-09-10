package com.booknic.repository;

import com.booknic.entity.Loan;
import com.booknic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, String> {

    List<Loan> findLoansByDuedateBeforeAndLibrary(LocalDate duedate, String library);
    List<Loan> findLoansByLibrary(String library);
    Loan findDistinctLoanByNameAndLibraryAndIsbn(String name, String library, String isbn);

}
