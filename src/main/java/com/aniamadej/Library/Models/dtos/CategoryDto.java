package com.aniamadej.Library.Models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
    private int categoryId;
    private String categoryName;
    private Long numberOfBooks;

    public CategoryDto(int categoryId, String categoryName, Long numberOfBooks) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.numberOfBooks = numberOfBooks;
    }
}
