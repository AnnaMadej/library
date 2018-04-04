package com.aniamadej.Library.Models.Forms;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class NewBookFormModel {
    @Size(min=3, max=100)
    @NotBlank
    private String title;
    @Size(min=3, max=100)
    @NotBlank
    private String author;
    private int pages;
    private int categoryId;
}
