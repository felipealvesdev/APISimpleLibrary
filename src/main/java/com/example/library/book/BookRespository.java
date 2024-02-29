package com.example.library.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRespository extends JpaRepository<Book, UUID> {
}
