package com.example.library.book;

import com.example.library.author.Author;
import com.example.library.author.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRespository bookRespository;

    public Book addBookIfNotExists(Book book) {
        Book existingBook = bookRespository.findBookByIsnb(book.getIsnb());
        if(existingBook != null) {
            return existingBook;
        }
        return bookRespository.save(book);
    }

    public Book getBook(Book book) {
        Book existingBook = bookRespository.findBookByIsnb(book.getIsnb());
        if(existingBook != null) {
            return existingBook;
        }
        return book;
    }

    public void saveBook(Book book) {
        bookRespository.save(book);
    }


    public Boolean bookExists(Book book) {
        Book existingBook = bookRespository.findBookByIsnb(book.getIsnb());
        if(existingBook != null) {
            return true;
        }
        return false;
    }
}
