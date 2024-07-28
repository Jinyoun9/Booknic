package com.booknic.dto;


import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {
    private String bookname;
    private String name;
    private LocalDate duedate;
}
