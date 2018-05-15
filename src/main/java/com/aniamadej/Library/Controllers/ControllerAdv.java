package com.aniamadej.Library.Controllers;
import com.aniamadej.Library.Models.dtos.CategoryDto;
import com.aniamadej.Library.Services.BookService;
import com.aniamadej.Library.Services.CategoryService;
import com.aniamadej.Library.Services.UserService;
import com.aniamadej.Library.Models.dtos.LoggedUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class ControllerAdv {

    private UserService userService;
    private CategoryService categoryService;
    @Autowired
    public ControllerAdv(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("currentUser")
    public String populateUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return "anonymousUser".equalsIgnoreCase(userName)?null:userName;
    }

    @ModelAttribute("categories")
    public List<CategoryDto> fillCategories() {
        return categoryService.getAllCategories();
    }
}