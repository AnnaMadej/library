package com.aniamadej.Library.Controllers;

import com.aniamadej.Library.Models.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlankController {
    @GetMapping("/blank")
    public String blank(){
        UserService currentUser;
        return "blank";
    }
}
