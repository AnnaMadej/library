package com.aniamadej.Library.Models.Entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book")
public class BookModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn (name="who")
    private UserModel who;
    private String title;
    private String author;
    private int pages;

    @ManyToOne
    @JoinColumn (name="categoryId")
    private CategoryModel category;

    public BookModel(UserModel who, String title, String author, int pages, CategoryModel category) {
        this.who = who;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.category = category;
    }

    public BookModel(String title, String author, int pages) {
        this.id = null;
        this.title = title;
        this.author = author;
        this.pages = pages;
    }
}
