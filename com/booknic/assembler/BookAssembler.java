package com.booknic.assembler;


import com.booknic.dto.FavoritebookDto;
import com.booknic.entity.Favoritebook;
import org.springframework.stereotype.Component;


@Component
public class BookAssembler {
    public FavoritebookDto toDto(Favoritebook favoritebook){
        return FavoritebookDto.builder()
                .bookname(favoritebook.getBookname())
                .library(favoritebook.getLibrary())
                .isbn(favoritebook.getIsbn())
                .build();
    }
}
