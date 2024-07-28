package com.booknic.entity;


import jakarta.persistence.*;
import lombok.*;

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
    private String duedate;
    private String library;
    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
}
