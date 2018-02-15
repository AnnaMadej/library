package com.aniamadej.Library.Controllers;

import com.aniamadej.Library.Models.BookModel;
import com.aniamadej.Library.Models.CategoryModel;
import com.aniamadej.Library.Models.Forms.NewBookFormModel;
import com.aniamadej.Library.Models.Repositories.BookRepository;
import com.aniamadej.Library.Models.Repositories.CategoryRepository;
import com.aniamadej.Library.Models.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.awt.print.Book;

@Controller
public class EditBookController {

    @Autowired
    UserService userService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/editBook/{bookId}")
    public String register(@PathVariable int bookId,  Model model){
        if (userService.getUser().getName()==null){
            return "redirect:/login.html";
        }
        BookModel book = bookRepository.findById(bookId);
        NewBookFormModel bookFormModel = new NewBookFormModel();
        bookFormModel.setAuthor(book.getAuthor());
        bookFormModel.setCategoryId(book.getCategoryId().getCategoryId());
        bookFormModel.setPages(book.getPages());
        bookFormModel.setTitle(book.getTitle());
        model.addAttribute("bookForm", bookFormModel);
        model.addAttribute("bookId", bookId);

        return "editBook";
    }

   @PostMapping("/editBook/{bookId}")
    public String register(@ModelAttribute("bookForm") @Valid NewBookFormModel bookForm, BindingResult bindingResult, @PathVariable int bookId, Model model){

            if (!bindingResult.hasErrors()){

            CategoryModel category = categoryRepository.findByCategoryId(bookForm.getCategoryId());
            BookModel bookModel = new BookModel();

            System.out.println(userService.getUser().getLogin());
            bookModel.setId(bookId);
            bookModel.setWho(userService.getUser());
            bookModel.setAuthor(bookForm.getAuthor());
            bookModel.setCategoryId(category);
            bookModel.setPages(bookForm.getPages());
            bookModel.setTitle(bookForm.getTitle());

            bookRepository.save(bookModel);
            model.addAttribute("addingOK", true);

            }

        return "editBook";
    }

    @GetMapping("/delete/{bookId}")
    public String delete (@PathVariable int bookId, Model model){

        if (userService.getUser().getName()==null){
            return "redirect:/login.html";
        }
        BookModel book = bookRepository.findById(bookId);
        model.addAttribute("book", book);
        return "delete";
    }


    @PostMapping("/delete/{bookId}")
    public String register(Model model, @PathVariable int bookId){
        if (userService.getUser().getName()==null){
            return "redirect:/login.html";
        }

        if (bookRepository.findById(bookId)!=null){
            bookRepository.deleteById(bookId);
            model.addAttribute("info", "Książka usunięta");
        }
        return "catalogue";
    }
}
