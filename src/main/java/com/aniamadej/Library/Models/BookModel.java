package com.aniamadej.Library.Models;

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
    private int id;
    @ManyToOne
    @JoinColumn (name="who")
    private UserModel who;
    private String title;
    private String author;
    private int pages;

    @ManyToOne
    @JoinColumn (name="categoryId")
    private CategoryModel categoryId;

    public BookModel(UserModel who, String title, String author, int pages, CategoryModel categoryId) {


        this.who = who;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.categoryId = categoryId;
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
}
