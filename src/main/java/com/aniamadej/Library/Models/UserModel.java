package com.aniamadej.Library.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String surname;
    private String password;
    private String login;

    @OneToMany(mappedBy = "who")
    private List<BookModel> books;
}
