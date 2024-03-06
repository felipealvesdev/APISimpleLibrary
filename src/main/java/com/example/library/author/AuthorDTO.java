package com.example.library.author;

import com.example.library.book.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record AuthorDTO(@NotBlank String name, @NotNull LocalDate dateOfBirth, List<Book> bookList) {
}
