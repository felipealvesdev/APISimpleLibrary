package com.example.library.book;

import com.example.library.author.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookDTO(@NotBlank String title, Author author, @NotNull Double price, @NotNull Long isnb) {

}
