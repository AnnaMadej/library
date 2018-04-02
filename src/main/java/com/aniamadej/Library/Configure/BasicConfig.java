package com.aniamadej.Library.Configure;

import com.aniamadej.Library.Models.Entities.BookModel;
import com.aniamadej.Library.Models.Entities.CategoryModel;
import com.aniamadej.Library.Models.Entities.UserModel;
import com.aniamadej.Library.Models.Forms.NewBookFormModel;
import com.aniamadej.Library.Models.Forms.RegisterForm;
import com.aniamadej.Library.Models.Repositories.CategoryRepository;
import com.aniamadej.Library.Models.dtos.CategoryDto;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BasicConfig {

    @Autowired
    CategoryRepository categoryRepository;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<NewBookFormModel, BookModel> newBookFormModelBookModelConverter = new AbstractConverter<NewBookFormModel, BookModel>() {
            @Override
            protected BookModel convert(NewBookFormModel nbfm) {
                BookModel bm = new BookModel();
                bm.setCategory(categoryRepository.findByCategoryId(nbfm.getCategoryId()));
                bm.setAuthor(nbfm.getAuthor());
                bm.setPages(nbfm.getPages());
                bm.setTitle(nbfm.getTitle());
                return bm;
            }
        };

        Converter<RegisterForm, UserModel> registerFormUserModelConverter = new AbstractConverter<RegisterForm, UserModel>() {
            @Override
            protected UserModel convert(RegisterForm rf) {
                return new UserModel(rf.getName(), rf.getSurname(), new BCryptPasswordEncoder().encode(rf.getPassword().trim()), rf.getLogin());
            }
        };

        Converter<CategoryModel, CategoryDto> categoryModelCategoryDtoConverter = new AbstractConverter<CategoryModel, CategoryDto>() {
            @Override
            protected CategoryDto convert(CategoryModel cm) {
                return new CategoryDto(cm.getCategoryId(), cm.getCategoryName(), (cm.getBooks()!=null)?cm.getBooks().size():0L);
            }
        };
        
        modelMapper.addConverter(newBookFormModelBookModelConverter);
        modelMapper.addConverter(registerFormUserModelConverter);
        modelMapper.addConverter(categoryModelCategoryDtoConverter);
        return modelMapper;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}