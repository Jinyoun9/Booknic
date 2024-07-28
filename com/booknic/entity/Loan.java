package com.booknic.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lid;
    private String name;
    private String bookname;
    private LocalDate duedate;
    private String library;
    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
}
