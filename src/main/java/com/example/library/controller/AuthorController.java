package com.example.library.controller;


import com.example.library.author.Author;
import com.example.library.author.AuthorDTO;
import com.example.library.author.AuthorRepository;
import com.example.library.author.AuthorService;
import com.example.library.book.Book;
import com.example.library.book.BookDTO;
import com.example.library.book.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;


    @GetMapping()
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.status(HttpStatus.OK).body(authorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAuthorById(@PathVariable(value = "id") Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if(author.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(author.get());
    }


    @PostMapping()
    public ResponseEntity<Author> saveAuthor(@RequestBody AuthorDTO authorDTO) {
        var authorModel = new Author();
        BeanUtils.copyProperties(authorDTO, authorModel);
        Boolean existingAuthor = authorService.authorExists(authorModel);
        if(existingAuthor) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.addAuthorIfNotExists(authorModel));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> addBookToThisAuthor(@PathVariable(value = "id") Long id,
                                                      @RequestBody @Valid BookDTO bookDTO) {
        Optional<Author> author = authorRepository.findById(id);
        if(author.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found.");
        }
        var bookModel = new Book();
        BeanUtils.copyProperties(bookDTO, bookModel);

        bookModel = bookService.getBook(bookModel);

        bookModel.setAuthor(author.get());
        bookService.saveBook(bookService.addBookIfNotExists(bookModel));
        return ResponseEntity.status(HttpStatus.OK).body(author.get());
    }
}
