package com.aniamadej.Library.Controllers;

import com.aniamadej.Library.Models.Forms.LoginFormModel;
import com.aniamadej.Library.Models.Forms.PasswordChangeForm;
import com.aniamadej.Library.Models.Forms.ProfileForm;
import com.aniamadej.Library.Models.Forms.RegisterForm;
import com.aniamadej.Library.Services.UserService;
import com.aniamadej.Library.Models.dtos.LoggedUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginForm", new LoginFormModel() );
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginForm") @Valid LoginFormModel loginForm, BindingResult bindingResult, Model model){

        if (userService.loginUser(loginForm.getLogin(), loginForm.getPassword())){
            return "redirect:/";
        }
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        userService.setUser(new LoggedUserDto());
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        if (userService.getUser().getLogin()==null){
            return "redirect:login";
        }
        model.addAttribute("profileForm", userService.getProfileForm());
        return ("profile");
    }

    @PostMapping ("/profile")
    public String profile(@ModelAttribute @Valid ProfileForm profileForm, BindingResult bindingResult, Model model){

        if (!bindingResult.hasErrors()){
            model.addAttribute("changed", userService.updateProfile(profileForm));
        }
        return ("profile");
    }
    @GetMapping("/passwordChange")
    public String passwordChange(Model model){
        if (userService.getUser().getLogin()==null){
            return "redirect:login";
        }
        model.addAttribute("passwordChangeForm", new PasswordChangeForm());
        return ("passwordChange");
    }

    @PostMapping ("/passwordChange")
    public String passwordChange(@ModelAttribute("passwordChangeForm") @Valid PasswordChangeForm passwordChangeForm, BindingResult bindingResult, Model model){

        if (!bindingResult.hasErrors()) {
            userService.changePasswd(passwordChangeForm).ifPresent(str ->  model.addAttribute("result", str));
        }
        return ("passwordChange");

    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("registerForm", new RegisterForm() );
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute @Valid RegisterForm registerForm, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            Optional<String> result = userService.registerUser(registerForm);
            if (!result.isPresent()) {
                LoginFormModel loginForm = new LoginFormModel(registerForm.getLogin());
                model.addAttribute("loginForm", loginForm);
                return "login";
            }
            model.addAttribute("registerError", result.get());
        }
        return "register";
    }




}
