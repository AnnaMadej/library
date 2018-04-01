package com.aniamadej.Library.Services;

import com.aniamadej.Library.Models.Entities.BookModel;
import com.aniamadej.Library.Models.Forms.NewBookFormModel;
import com.aniamadej.Library.Models.Repositories.BookRepository;
import com.aniamadej.Library.Models.Repositories.CategoryRepository;
import com.aniamadej.Library.Models.dtos.BookDto;
import com.aniamadej.Library.Models.dtos.BookWithCategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    CategoryRepository categoryRepository;
    BookRepository bookRepository;
    ModelMapper modelMapper;

    @Autowired
    public BookService(CategoryRepository categoryRepository, BookRepository bookRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public boolean addBook(NewBookFormModel newBookForm) {
        BookModel  bookModel=  modelMapper.map(newBookForm,BookModel.class);
        bookModel.setCategory(categoryRepository.findByCategoryId(newBookForm.getCategoryId()));
        bookModel.setId(null);
        bookRepository.save(bookModel);
        return true;
    }

    public Page<BookDto> getBooksOfCategory(int categoryId, Pageable pageable){
       Page<BookModel> bookModels =  bookRepository.findAllByCategoryCategoryIdOrderByTitle(categoryId, pageable);
       Page<BookDto> bookDtos = bookModels.map(bookModel -> modelMapper.map(bookModel, BookDto.class));
       return bookDtos;
    }

    public String getCategoryName(int categoryId){
        return categoryRepository.findByCategoryId(categoryId).getCategoryName();
    }

    public Page<BookDto> getBooksWithPhrase(String phrase, Pageable pageable){
        Page<BookModel> bookModels = bookRepository.findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByTitle(phrase, phrase, pageable);
        Page<BookDto> bookDtos = bookModels.map(bookModel -> modelMapper.map(bookModel, BookDto.class));
        return bookDtos;
    }

    public BookWithCategoryDto getBookWithCategoryDto(int bookId) {
        return modelMapper.map(bookRepository.findById(bookId), BookWithCategoryDto.class);
    }

    public NewBookFormModel getBookForm(int bookId){
        return modelMapper.map(getBookWithCategoryDto(bookId), NewBookFormModel.class);
    }

    public boolean editBook(Integer bookId, NewBookFormModel newBookForm) {
        BookModel bookModel = modelMapper.map(newBookForm,BookModel.class);
        bookModel.setId(bookId);
        System.out.println(bookModel.getPages());
        bookRepository.save(bookModel);
        return true;
    }

    public BookDto getBookDto(Integer bookId){
        return modelMapper.map(bookRepository.findById(bookId),BookDto.class);
    }

    public boolean deleteBook(int bookId) {
        if (bookRepository.findById(bookId) == null)return false;
        bookRepository.deleteById(bookId);
        return true;
    }

}
