package com.booknic.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Favoritebook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fid;
    private String bookname;
    private String library;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
}
