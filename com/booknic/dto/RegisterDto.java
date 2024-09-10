package com.booknic.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String id;
    private String rawPW;
    private String name;
    private String gender;
    private String email;
    private String role;
    @JsonIgnore
    private String library;
}
