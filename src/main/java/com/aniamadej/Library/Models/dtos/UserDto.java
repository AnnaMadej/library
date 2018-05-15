package com.aniamadej.Library.Models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto extends LoggedUserDto {
    private String password;
    private String name;
    private String surname;

    public UserDto(String login, int Id, String password, String name, String surname) {
        super(login, Id);
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
}
