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
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable(value = "id")UUID id) {
        Optional<Book> bookModel = bookRespository.findById(id);

        if(bookModel.isEmpty()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(bookRespository.findById(id));
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
        if(existingAuthor) {
            var tempAuthor = authorService.getAuthor(bookModel.getAuthor());
            bookModel.setAuthor(tempAuthor);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBookIfNotExists(bookModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value = "id") UUID id) {
        Optional<Book> bookModel = bookRespository.findById(id);

        if(bookModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }

        bookRespository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Book has been deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable(value = "id") UUID id,
                                             @RequestBody BookDTO bookDTO) {
        Optional<Book> bookModel = bookRespository.findById(id);

        if(bookModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }

        var book = bookModel.get();
        BeanUtils.copyProperties(bookDTO, book);
        book.setId(id);
        if(bookDTO.author() == null) {
            book.setAuthor(bookModel.get().getAuthor());
        }

        Boolean existingAuthor = authorService.authorExists(book.getAuthor());
        if(existingAuthor) {
            var tempAuthor = authorService.getAuthor(book.getAuthor());
            book.setAuthor(tempAuthor);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(bookRespository.save(book));
    }
}
