
package com.example.library.author;

import com.example.library.book.Book;
import com.example.library.book.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author addAuthorIfNotExists(Author author) {
        Author existingAuthor = authorRepository.findByName(author.getName());
        if(existingAuthor != null) {
            return existingAuthor;
        }
        return authorRepository.save(author);
    }

    public Author getAuthor(Author author) {
        Author existingAuthor = authorRepository.findByName(author.getName());
        if(existingAuthor != null) {
            return existingAuthor;
        }
        return author;
    }


    public Boolean authorExists(Author author) {
        Author existingAuthor = authorRepository.findByName(author.getName());
        if(existingAuthor != null) {
            return true;
        }
        return false;
    }

    public Object addBookToAuthor(Author author, Book book) {
        Boolean authorValidation = this.authorExists(author);
        if(authorValidation) {
            author.getBookList().add(book);
        }
        return null;
    }
}
