package com.aniamadej.Library.Controllers;
import com.aniamadej.Library.Models.Forms.LoginFormModel;
import com.aniamadej.Library.Models.Forms.RegisterForm;
import com.aniamadej.Library.Models.Repositories.UserRepository;
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
public class RegisterController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("registerForm", new RegisterForm() );
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute @Valid RegisterForm registerForm, BindingResult bindingResult, Model model){
        boolean passwordsMatch = registerForm.getPassword().equals(registerForm.getPassword2());
        boolean loginExists = userRepository.existsByLogin(registerForm.getLogin());

        model.addAttribute("passwordsMatch", passwordsMatch);
        model.addAttribute("loginExists", loginExists);


        if (!bindingResult.hasErrors() && passwordsMatch && !loginExists) {
            UserModel userModel = new UserModel();
            userModel.setLogin(registerForm.getLogin());
            userModel.setName(registerForm.getName());
            userModel.setPassword(registerForm.getPassword());
            userModel.setSurname(registerForm.getSurname());
            userRepository.save(userModel);
            model.addAttribute("registrationOK", true);
            LoginFormModel loginForm = new LoginFormModel(registerForm.getLogin());
            model.addAttribute("loginForm", loginForm);
            return "login";
        }

        return "register";
    }
}
