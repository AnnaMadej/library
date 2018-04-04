package com.aniamadej.Library.Models.Forms;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class PasswordChangeForm {

    @NotBlank
    private String oldPassword;

    @Size(min=6, max=255)
    @NotBlank
    private String password;

    @Size(min=6, max=255)
    @NotBlank
    private String password2;
}
