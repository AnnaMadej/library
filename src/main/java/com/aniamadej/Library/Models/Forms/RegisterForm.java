package com.aniamadej.Library.Models.Forms;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class RegisterForm {
    @Size(min=3, max=30)
    @NotBlank
    private String name;

    @Size(min=3, max=30)
    @NotBlank
    private String surname;

    @Size(min=3, max=30)
    @NotBlank
    private String login;

    @Size(min=6, max=255)
    @NotBlank
    private String password;

    @Size(min=6, max=255)
    @NotBlank
    private String password2;

}
