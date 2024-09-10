package com.booknic.controller;


import com.booknic.dto.LoanDto;
import com.booknic.dto.LoanRequestDto;
import com.booknic.entity.Loan;
import com.booknic.entity.User;
import com.booknic.repository.LoanRepository;
import com.booknic.repository.UserRepository;
import com.booknic.service.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/getloan")
    public ResponseEntity<?> getLoan(@RequestHeader("Authorization") String token, @RequestParam String library){
        List<LoanDto> loanInfos = adminService.fetchLoanDtos(library);
        return ResponseEntity.ok().body(loanInfos);
    }
    @GetMapping("/overdue")
    public ResponseEntity<?> getOverdueUser(@RequestHeader("Authorization") String token, @RequestParam String library){
        List<LoanDto> overDueUsers = adminService.fetchOverdueUser(library);
        return ResponseEntity.ok().body(overDueUsers);
    }
    @PutMapping("/extenddue")
    @Transactional
    public void updateDuedate(@RequestHeader("Authorization") String token, @RequestBody LoanRequestDto params){
        User user = userRepository.findUserByName(params.getName());
        String library = params.getLibrary();
        String isbn = params.getIsbn13();

        Loan updateLoan = adminService.editDuedate(user, library, isbn);
        loanRepository.save(updateLoan);
    }
}
