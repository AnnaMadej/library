package com.aniamadej.Library.Models.Repositories;
import com.aniamadej.Library.Models.Entities.CategoryModel;
import com.aniamadej.Library.Models.Entities.BookModel;
import com.aniamadej.Library.Models.dtos.CategoryDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryModel, Integer> {
    List<CategoryModel> findAllByOrderByCategoryNameAsc();
    CategoryModel findByCategoryId(int categoryId);
    boolean existsBycategoryName(String categoryName);
    CategoryModel findByCategoryName(String categoryName);
    boolean existsByCategoryId(int categoryId);

    @Query("SELECT new com.aniamadej.Library.Models.dtos.CategoryDto(c.categoryId, c.categoryName, count(b.id)) from CategoryModel c left join BookModel b on c.categoryId=b.category where c.categoryId=:categoryId")
    CategoryDto getCatDto(@Param("categoryId") Integer categoryId);

    @Query("SELECT new com.aniamadej.Library.Models.dtos.CategoryDto(c.categoryId, c.categoryName, count(b.id)) from CategoryModel c left join BookModel b on c.categoryId=b.category group by c.categoryId")
    List<CategoryDto> getAllCategoryDtos();

}