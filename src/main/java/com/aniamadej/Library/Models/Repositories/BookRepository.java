package com.aniamadej.Library.Models.Repositories;

import com.aniamadej.Library.Models.BookModel;
import com.aniamadej.Library.Models.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BookRepository extends PagingAndSortingRepository<BookModel, Integer> {
    Page<BookModel> findAllByCategoryIdOrderByTitle(CategoryModel categoryId, Pageable pageable);
    BookModel findById(int id);
    Page<BookModel> findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByTitle(String words1, String words2, Pageable pageable);




    @Transactional
    void deleteById(int id);
}
