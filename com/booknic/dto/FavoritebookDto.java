package com.booknic.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoritebookDto {
    String bookname;
    String library;
}
