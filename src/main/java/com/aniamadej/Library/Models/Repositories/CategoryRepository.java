package com.aniamadej.Library.Models.Repositories;
import com.aniamadej.Library.Models.Entities.CategoryModel;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryModel, Integer> {
    List<CategoryModel> findAllByOrderByCategoryNameAsc();
    CategoryModel findByCategoryId(int categoryId);
}