package com.aniamadej.Library.Controllers;
import com.aniamadej.Library.Models.dtos.CategoryDto;
import com.aniamadej.Library.Services.BookService;
import com.aniamadej.Library.Services.UserService;
import com.aniamadej.Library.Models.dtos.LoggedUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class ControllerAdv {

    private UserService userService;
    private BookService bookService;

    @Autowired
    public ControllerAdv(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @ModelAttribute("currentUser")
    public LoggedUserDto populateUser() {
        return userService.getUser();
    }

    @ModelAttribute("categories")
    public List<CategoryDto> fillCategories() {
        return bookService.getAllCategories();
    }
}