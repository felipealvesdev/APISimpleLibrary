
package com.example.library.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Boolean authorExists(Author author) {
        Author existingAuthor = authorRepository.findByName(author.getName());
        if(existingAuthor != null) {
            return true;
        }
        return false;
    }

}
