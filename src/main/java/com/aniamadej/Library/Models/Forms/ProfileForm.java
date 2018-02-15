package com.aniamadej.Library.Models.Forms;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ProfileForm {
    @Size(min=3, max=30)
    @NotBlank
    private String name;

    @Size(min=3, max=30)
    @NotBlank
    private String surname;

    @Size(min=6, max=255)
    @NotBlank
    private String oldPassword;
}
