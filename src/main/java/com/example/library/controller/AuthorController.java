package com.example.library.controller;


import com.example.library.author.Author;
import com.example.library.author.AuthorDTO;
import com.example.library.author.AuthorRepository;
import com.example.library.author.AuthorService;
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


    @GetMapping()
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.status(HttpStatus.OK).body(authorRepository.findAll());
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
}
