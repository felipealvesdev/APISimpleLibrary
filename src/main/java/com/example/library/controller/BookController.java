package com.example.library.controller;


import com.example.library.author.AuthorService;
import com.example.library.book.Book;
import com.example.library.book.BookDTO;
import com.example.library.book.BookRespository;
import com.example.library.book.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {


    @Autowired
    private BookRespository bookRespository;
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;


    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookRespository.findAll());
    }

    @PostMapping()
    public ResponseEntity<Book> saveBook(@RequestBody BookDTO bookDTO) {
        var bookModel = new Book();
        BeanUtils.copyProperties(bookDTO, bookModel);
        Boolean existingBook = bookService.bookExists(bookModel);
        Boolean existingAuthor = authorService.authorExists(bookModel.getAuthor());
        if(existingBook) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(!existingAuthor) {
            authorService.addAuthorIfNotExists(bookModel.getAuthor());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBookIfNotExists(bookModel));
    }
}
