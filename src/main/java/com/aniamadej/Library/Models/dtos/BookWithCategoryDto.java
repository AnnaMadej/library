package com.aniamadej.Library.Models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BookWithCategoryDto extends BookDto{
    private CategoryDto category;
    public BookWithCategoryDto(int id, String title, String author, int pages, CategoryDto category) {
        super(id, title, author, pages);
        this.category = category;
    }
}