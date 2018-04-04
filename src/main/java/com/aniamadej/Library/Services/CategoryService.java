package com.aniamadej.Library.Services;
import com.aniamadej.Library.Models.Entities.BookModel;
import com.aniamadej.Library.Models.Entities.CategoryModel;
import com.aniamadej.Library.Models.Repositories.BookRepository;
import com.aniamadej.Library.Models.Repositories.CategoryRepository;
import com.aniamadej.Library.Models.dtos.BookDto;
import com.aniamadej.Library.Models.dtos.CategoryDto;
import com.aniamadej.Library.Models.dtos.CategoryFullDto;
import com.aniamadej.Library.RestResult;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;
    BookRepository bookRepository;
    ModelMapper modelMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, BookRepository bookRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoryDto> getAllCategories(){
       return categoryRepository.getAllCategoryDtos();
    }

    public Object addCategory(String categoryName){
        if (categoryRepository.existsBycategoryName(categoryName)) {
            return RestResult.REST_RESULT_MULTIPLE_NAME;
        }
        CategoryModel categoryModel = new CategoryModel(categoryName);
        categoryModel = categoryRepository.save(categoryModel);
        return categoryRepository.getCatDto(categoryModel.getCategoryId());

    }

    public Object renameCategory(Integer categoryId, String categoryName) {
        if (!categoryRepository.existsByCategoryId(categoryId)) return RestResult.REST_RESULT_NO_ENTRY;
        if (categoryRepository.existsBycategoryName(categoryName)){
            return RestResult.REST_RESULT_MULTIPLE_NAME;
        }
        CategoryModel categoryModel = categoryRepository.findByCategoryId(categoryId);
        categoryModel.setCategoryName(categoryName);
        categoryModel = categoryRepository.save(categoryModel);
        return categoryRepository.getCatDto(categoryId);
    }

    public Object deleteCategory(Integer categoryId, String newCategoryName) {
        if (!categoryRepository.existsByCategoryId(categoryId)) return RestResult.REST_RESULT_NO_ENTRY;
        CategoryModel categoryToDelete = categoryRepository.findByCategoryId(categoryId);

        if (categoryToDelete.getBooks().size()!=0) {
            if (newCategoryName == null) return RestResult.REST_RESULT_NOT_EMPTY;
            if (!categoryRepository.existsBycategoryName(newCategoryName)) {
                addCategory(newCategoryName);
            }
            CategoryModel newCategory = categoryRepository.findByCategoryName(newCategoryName);
            categoryToDelete.getBooks().forEach(book -> {
                book.setCategory(newCategory);
                bookRepository.save(book);
            });
            categoryRepository.delete(categoryToDelete);
            return getCategoryDto(newCategory.getCategoryId());
        }
        categoryRepository.delete(categoryToDelete);
        return getAllCategories();
    }

    public String getCategoryName(Integer categoryId){
        return categoryRepository.findByCategoryId(categoryId).getCategoryName();
    }

    public CategoryDto getCategoryDto(Integer categoryId) {
        CategoryDto categoryDto = categoryRepository.getCatDto(categoryId);
        return categoryDto.getCategoryId()!=null?categoryDto:null;
    }

    public CategoryFullDto getCategoryFullDto(Integer categoryId){
        CategoryModel categoryModel = categoryRepository.findByCategoryId(categoryId);
        return categoryModel!=null?modelMapper.map(categoryModel, CategoryFullDto.class):null;
    }
}
