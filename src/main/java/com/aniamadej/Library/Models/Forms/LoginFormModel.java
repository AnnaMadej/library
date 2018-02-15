package com.aniamadej.Library.Models.Forms;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginFormModel {
    private String login;
    private String password;

    public LoginFormModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LoginFormModel(String login) {
        this.login = login;
    }

}
