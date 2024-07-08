package com.booknic.entity;


import io.jsonwebtoken.ClaimsMutator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mid;
    private String id;
    private String password;
    private String name;
    private String refresh_token;
    private String bookname;

}
