package com.example.library.book;

import com.example.library.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRespository extends JpaRepository<Book, UUID> {

    Book findBookByIsnb(Long isnb);
}
