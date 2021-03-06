package com.aniamadej.Library.Models.Repositories;

import com.aniamadej.Library.Models.Entities.BookModel;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends CrudRepository<BookModel, Integer> {
    Page<BookModel> findAllByCategoryCategoryIdOrderByTitle(int categoryId, Pageable pageable);
    Page<BookModel> findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByTitle(String words1, String words2, Pageable pageable);
    Page<BookModel> findAll(Pageable pageable);
}
