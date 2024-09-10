package com.booknic.entity;


import io.jsonwebtoken.Jwt;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements JwtSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    private String id;
    private String password;
    private String name;
    private String gender;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Favoritebook> favoriteBooksList;

}
