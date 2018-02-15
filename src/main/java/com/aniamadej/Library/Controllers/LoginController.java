package com.aniamadej.Library.Controllers;
import com.aniamadej.Library.Models.Forms.LoginFormModel;
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
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginForm", new LoginFormModel() );
        return "login";
    }

    @PostMapping ("/login")
    public String login(@ModelAttribute("loginForm") @Valid LoginFormModel loginForm, BindingResult bindingResult, Model model){

        if (userRepository.existsByLoginAndPassword(loginForm.getLogin(), loginForm.getPassword()))
        {
            userService.setUser(userRepository.findByLogin(loginForm.getLogin()));
            return "redirect:/";
        }
        else model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        userService.setUser(new UserModel());
        return "redirect:/login";
    }


}
