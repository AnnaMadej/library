package com.aniamadej.Library.Models.Entities;
import com.aniamadej.Library.Models.Entities.BookModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer categoryId;
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.MERGE, CascadeType.PERSIST} , fetch = FetchType.LAZY)
    private List<BookModel> books;

    public CategoryModel(String categoryName) {
        this.categoryName = categoryName;
    }
}
