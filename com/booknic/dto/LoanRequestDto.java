package com.booknic.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestDto {
    private String name;
    private String library;
    private String isbn13;
}
