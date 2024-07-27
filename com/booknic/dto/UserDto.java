package com.booknic.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    String id;
    String gender;
    String name;
    String email;
}
