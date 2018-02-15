package com.aniamadej.Library.Models.Repositories;

import com.aniamadej.Library.Models.BookModel;
import com.aniamadej.Library.Models.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.zip.CRC32;

public interface CategoryRepository extends CrudRepository<CategoryModel, Integer> {
    List<CategoryModel> findAllByOrderByCategoryNameAsc();
    CategoryModel findByCategoryId(int categoryId);
}