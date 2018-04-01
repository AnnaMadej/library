package com.aniamadej.Library.Controllers;

import com.aniamadej.Library.Models.Forms.NewBookFormModel;
import com.aniamadej.Library.Services.BookService;
import com.aniamadej.Library.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class BookController {

    private BookService bookService;
    private UserService userService;

    @Autowired
    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }


    @GetMapping("/books/{categoryId}/{page}")
    public String books(@PathVariable("categoryId") int categoryId, @PathVariable("page") int page, Model model){
        Pageable pageable = new PageRequest(page,4);
        model.addAttribute("books", bookService.getBooksOfCategory(categoryId, pageable));
        model.addAttribute("categoryName", bookService.getCategoryName(categoryId));
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("pageNo", page);
        return "books";
    }

    @GetMapping("/book/{bookId}")
    public String book(@PathVariable("bookId") int bookId, Model model ){
        model.addAttribute("book", bookService.getBookDto(bookId));
        return "book";
    }

    @GetMapping("/search/{phrase}/{page}")
    public String books(@PathVariable("phrase") String phrase, @PathVariable("page") int page,  Model model){
        Pageable pageable = new PageRequest(page,4);
        model.addAttribute("books", bookService.getBooksWithPhrase(phrase, pageable));
        model.addAttribute("categoryName", "Wyniki wyszukiwania");
        model.addAttribute("categoryId", phrase);
        model.addAttribute("pageNo", page);
        return "books";
    }

    @GetMapping("/search")
    public String search (@RequestParam("phrase") String phrase){
        return "redirect:/search/" + phrase + '/' + 0;
    }

    @GetMapping("/book/{bookId}/edit")
    public String register(@PathVariable int bookId, Model model) {
        if (userService.getUser().getLogin() == null) return "redirect:/login.html";
        model.addAttribute("bookForm", bookService.getBookForm(bookId));
        model.addAttribute("bookId", bookId);
        return "editBook";
    }

    @PostMapping("/book/{bookId}/edit")
    public String register(@ModelAttribute("bookForm") @Valid NewBookFormModel bookForm, BindingResult bindingResult, @PathVariable int bookId, Model model) {
        if (userService.getUser().getLogin() == null) return "redirect:/login.html";
        model.addAttribute("success", bookService.editBook(bookId, bookForm));
        return "editBook";
    }

    @GetMapping("/book/{bookId}/delete")
    public String delete(@PathVariable int bookId, Model model) {
        if (userService.getUser().getLogin() == null) return "redirect:/login.html";
        model.addAttribute("book", bookService.getBookDto(bookId));
        return "deleteBook";
    }


    @PostMapping("/book/{bookId}/delete")
    public String register(Model model, @PathVariable int bookId) {
        if (userService.getUser().getLogin() == null) return "redirect:/login.html";
        model.addAttribute("deleted", bookService.deleteBook(bookId));
        return "blank";
    }

    @GetMapping("/book/add")
    public String register(Model model){
        if (userService.getUser().getLogin()==null) return "redirect:login";
        model.addAttribute("newBookForm", new NewBookFormModel());
        return "addBook";
    }

    @PostMapping("/book/add")
    public String register(@ModelAttribute ("newBookForm") @Valid  NewBookFormModel newBookForm, BindingResult bindingResult, Model model){
        if(!bindingResult.hasErrors()){
            model.addAttribute("success",bookService.addBook(newBookForm) );
        }
        return "addBook";
    }
}
