package com.aniamadej.Library.Models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryFullDto {
    private Integer categoryId;
    private String categoryName;
    private List<BookDto> books;
}
