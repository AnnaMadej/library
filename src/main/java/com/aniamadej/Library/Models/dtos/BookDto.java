package com.aniamadej.Library.Models.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDto {
    private int id;
    private String title;
    private String author;
    private int pages;
}
