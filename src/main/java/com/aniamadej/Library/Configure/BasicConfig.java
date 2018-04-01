package com.aniamadej.Library.Configure;

import com.aniamadej.Library.Models.Entities.BookModel;
import com.aniamadej.Library.Models.Forms.NewBookFormModel;
import com.aniamadej.Library.Models.Repositories.CategoryRepository;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

        modelMapper.addConverter(newBookFormModelBookModelConverter);
        return modelMapper;
    }
}