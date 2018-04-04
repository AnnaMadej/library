package com.aniamadej.Library.Controllers;

import com.aniamadej.Library.Models.dtos.CategoryDto;
import com.aniamadej.Library.Models.dtos.CategoryFullDto;
import com.aniamadej.Library.RestResult;
import com.aniamadej.Library.Services.BookService;
import com.aniamadej.Library.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CategoryRestController {

    private BookService bookService;
    private CategoryService categoryService;

    @Autowired
    public CategoryRestController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }

    @GetMapping("/api/category/{categoryId}")
    public ResponseEntity<Object> getCategory(@PathVariable("categoryId") Integer categoryId){
        CategoryDto categoryDto = categoryService.getCategoryDto(categoryId);
        if (categoryDto==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RestResult.REST_RESULT_NO_ENTRY);
        return ResponseEntity.ok().body(categoryDto);
    }

    @GetMapping("/api/category/{categoryId}/books")
    public ResponseEntity<Object> getCategoryBooks(@PathVariable("categoryId") Integer categoryId){
        CategoryFullDto categoryFullDto = categoryService.getCategoryFullDto(categoryId);
        if (categoryFullDto==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RestResult.REST_RESULT_NO_ENTRY);
        return ResponseEntity.ok().body(categoryFullDto);
    }

    @PostMapping("/api/category")
    public ResponseEntity<Object> addCategory(@RequestParam("categoryName") String categoryName){
        Object result = categoryService.addCategory(categoryName);
        if (result==RestResult.REST_RESULT_MULTIPLE_NAME) return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping ("/api/category/{categoryId}")
    public ResponseEntity<Object> renameCategory(@PathVariable("categoryId") Integer categoryId, @RequestParam("categoryName") String categoryName){
       Object result = categoryService.renameCategory(categoryId, categoryName);
       if (result==RestResult.REST_RESULT_NO_ENTRY) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
       if (result==RestResult.REST_RESULT_MULTIPLE_NAME) return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
       return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/api/category/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("categoryId") Integer categoryId, @RequestParam(value = "newCategoryName", required = false) String newCategoryName){
        Object result = categoryService.deleteCategory(categoryId, newCategoryName);
        if (result==RestResult.REST_RESULT_NO_ENTRY) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        if (result==RestResult.REST_RESULT_NOT_EMPTY) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
        return ResponseEntity.ok().body(result);
    }
}
