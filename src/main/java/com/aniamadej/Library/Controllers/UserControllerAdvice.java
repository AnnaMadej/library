package com.aniamadej.Library.Controllers;
import com.aniamadej.Library.Models.CategoryModel;
import com.aniamadej.Library.Models.Repositories.CategoryRepository;
import com.aniamadej.Library.Models.Services.UserService;
import com.aniamadej.Library.Models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class UserControllerAdvice {

    @Autowired
    UserService userService;
    @ModelAttribute("currentUser")
    public UserModel populateUser() {
        return userService.getUser();
    }


    @Autowired
    CategoryRepository categoryRepository;
    @ModelAttribute("categories")
    public List<CategoryModel> fillCategories() {
        return categoryRepository.findAllByOrderByCategoryNameAsc();
    }
}