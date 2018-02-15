package com.aniamadej.Library.Controllers;
import com.aniamadej.Library.Models.BookModel;
import com.aniamadej.Library.Models.CategoryModel;
import com.aniamadej.Library.Models.Repositories.BookRepository;
import com.aniamadej.Library.Models.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Controller
public class CatalogueController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @GetMapping("/catalogue/{categoryId}")
    public String catalogue(@PathVariable("categoryId") int categoryId, Model model){
        Pageable pageable = new PageRequest(0,4);
        CategoryModel category;
        Page<BookModel> books;
        category = categoryRepository.findByCategoryId(categoryId);
        books = bookRepository.findAllByCategoryIdOrderByTitle(category, pageable);
        model.addAttribute("books", books);
        model.addAttribute("category", category);
        model.addAttribute("pageNo", 0);
        model.addAttribute("words", null);
        return "catalogue";
    }

    @PostMapping("/catalogue/{categoryId}")
    public String search (@PathVariable("categoryId") int categoryId, @RequestParam("page") int page, Model model){
        Pageable pageable = new PageRequest(page,4);
        CategoryModel category;
        Page<BookModel> books;
        category = categoryRepository.findByCategoryId(categoryId);
        books = bookRepository.findAllByCategoryIdOrderByTitle(category, pageable);
        model.addAttribute("books", books);
        model.addAttribute("category", category );
        return "catalogue";
    }

    @PostMapping("/search")
    public String search (@RequestParam("page") int page, @RequestParam("words") String words, Model model){
        Pageable pageable = new PageRequest(page,4);
        CategoryModel category = new CategoryModel();
        Page<BookModel> books;
        category.setCategoryId(0);
        category.setCategoryName("Wyniki wyszukiwania: ");
        books = bookRepository.findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByTitle(words, words, pageable);
        model.addAttribute("books", books);
        model.addAttribute("category", category );
        model.addAttribute("words", words);
        return "catalogue";
    }
}
