package com.library.bookservice.dto;

import com.library.bookservice.model.Author;
import com.library.bookservice.model.Genre;
import com.library.bookservice.model.Publisher;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record BookRequest(
        @NotBlank
        String name,
        @NotNull
        List<Author> authors,
        @NotNull
        int pages,
        @NotNull
        List<Genre> genres,
        @NotNull
        int publicationYear,
        @NotBlank
        String asin,
        @NotBlank
        String summary,
        @NotNull
        Publisher publisher,
        @NotBlank
        String bookCover,
        @NotNull
        BigDecimal price
) {
}
