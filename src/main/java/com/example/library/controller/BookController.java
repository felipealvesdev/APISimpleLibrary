package com.example.library.controller;


import com.example.library.book.Book;
import com.example.library.book.BookDTO;
import com.example.library.book.BookRespository;
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


    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookRespository.findAll());
    }

    @PostMapping()
    public ResponseEntity<Book> saveBook(@RequestBody BookDTO bookDTO) {
        var bookModel = new Book();
        BeanUtils.copyProperties(bookDTO, bookModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookRespository.save(bookModel));
    }
}
