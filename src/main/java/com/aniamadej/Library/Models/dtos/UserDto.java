package com.aniamadej.Library.Models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends LoggedUserDto {
    int id;
    String password;
    String name;
    String surname;
}
