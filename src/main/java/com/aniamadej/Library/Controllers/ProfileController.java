package com.aniamadej.Library.Controllers;

import com.aniamadej.Library.Models.Forms.PasswordChangeForm;
import com.aniamadej.Library.Models.Forms.ProfileForm;
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
public class ProfileController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public String profile(Model model){

        if (userService.getUser().getName()==null){
            return "redirect:login";
        }

            ProfileForm profileForm = new ProfileForm();
            profileForm.setName(userService.getUser().getName());
            profileForm.setSurname(userService.getUser().getSurname());
            model.addAttribute("profileForm", profileForm);
            return ("profile");
    }

    @PostMapping ("/profile")
    public String profile(@ModelAttribute @Valid ProfileForm profileForm, BindingResult bindingResult, Model model){
        UserModel userModel = userService.getUser();
        boolean passwordCorrect = userService.getUser().getPassword().equals(profileForm.getOldPassword());
        if (passwordCorrect && !bindingResult.hasErrors()){
            userModel.setName(profileForm.getName());
            userModel.setSurname(profileForm.getSurname());
            userRepository.save(userModel);
            userService.setUser(userModel);
            model.addAttribute("changeOK", true);
        }
        else model.addAttribute("passwordError", true);
        return ("profile");
    }

    @GetMapping("/passwordChange")
    public String passwordChange(Model model){

        if (userService.getUser().getName()==null){
            return "redirect:login";
        }
        PasswordChangeForm passwordChangeForm = new PasswordChangeForm();
        model.addAttribute("passwordChangeForm", passwordChangeForm);
        return ("passwordChange");
    }

    @PostMapping ("/passwordChange")
    public String passwordChange(@ModelAttribute("passwordChangeForm") @Valid PasswordChangeForm passwordChangeForm, BindingResult bindingResult, Model model){
        UserModel userModel = userService.getUser();
        boolean passwordCorrect = userService.getUser().getPassword().equals(passwordChangeForm.getOldPassword());
        if (passwordCorrect && !bindingResult.hasErrors()){
          userModel.setPassword(passwordChangeForm.getPassword());
          userRepository.save(userModel);
          userService.setUser(userModel);
          model.addAttribute("changeOK", true);
        }
        else model.addAttribute("passwordError", true);
        return ("passwordChange");

    }
}
