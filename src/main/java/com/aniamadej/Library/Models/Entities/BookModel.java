package com.aniamadej.Library.Models.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
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

    @Override
    public String toString() {
        return "BookModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                '}';
    }

    public BookModel(String title, String author, int pages) {
        this.id = null;
        this.title = title;
        this.author = author;
        this.pages = pages;
    }
}
