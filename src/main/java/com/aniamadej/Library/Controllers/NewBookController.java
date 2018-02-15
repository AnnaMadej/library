package com.aniamadej.Library.Controllers;
import com.aniamadej.Library.Models.BookModel;
import com.aniamadej.Library.Models.CategoryModel;
import com.aniamadej.Library.Models.Forms.LoginFormModel;
import com.aniamadej.Library.Models.Forms.NewBookFormModel;
import com.aniamadej.Library.Models.Forms.RegisterForm;
import com.aniamadej.Library.Models.Repositories.BookRepository;
import com.aniamadej.Library.Models.Repositories.CategoryRepository;
import com.aniamadej.Library.Models.Repositories.UserRepository;
import com.aniamadej.Library.Models.Services.UserService;
import com.aniamadej.Library.Models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class NewBookController {

    @Autowired
    UserService userService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/newBook")
    public String register(Model model){
        if (userService.getUser().getName()==null){
            return "redirect:login";
        }
        model.addAttribute("newBookForm", new NewBookFormModel());
        return "newBook";
    }

   @PostMapping("/newBook")
    public String register(@ModelAttribute @Valid NewBookFormModel newBookForm, BindingResult bindingResult, Model model){

        if(!bindingResult.hasErrors()){
            CategoryModel category = categoryRepository.findByCategoryId(newBookForm.getCategoryId());
            BookModel bookModel = new BookModel();

            System.out.println(userService.getUser().getLogin());
            bookModel.setWho(userService.getUser());
            bookModel.setAuthor(newBookForm.getAuthor());
            bookModel.setCategoryId(category);
            bookModel.setPages(newBookForm.getPages());
            bookModel.setTitle(newBookForm.getTitle());

            bookRepository.save(bookModel);
            model.addAttribute("addingOK", true);
        }

        return "newBook";
    }
}
