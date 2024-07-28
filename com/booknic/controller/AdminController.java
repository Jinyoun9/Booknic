package com.booknic.controller;


import com.booknic.dto.LoanDto;
import com.booknic.entity.Loan;
import com.booknic.entity.User;
import com.booknic.repository.LoanRepository;
import com.booknic.repository.UserRepository;
import com.booknic.service.AdminService;
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
    private final AdminService adminService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final LoanRepository loanRepository;

    @GetMapping("/overdue")
    public ResponseEntity<?> getOverdueUser(/*@RequestHeader("Authorization") String token,*/ @RequestParam Map<String, String> params){
        String library = params.get("library");
        List<LoanDto> loanDtoList = adminService.fetchOverdueUser(library);
        return ResponseEntity.ok().body(loanDtoList);
    }
    @PutMapping("/extenddue")
    public void updateDuedate(/*@RequestHeader("Authorization") String token,*/ @RequestParam Map<String, String> params){
        User user = userRepository.findUserById(params.get("id"));
        String library = params.get("library");
        String bookname = params.get("bookname");

        Loan updateLoan = adminService.editDuedate(user, library, bookname);
        loanRepository.save(updateLoan);
    }
}
