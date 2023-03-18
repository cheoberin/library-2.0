package com.library.bookservice.dto;

import com.library.bookservice.model.Author;
import com.library.bookservice.model.Genre;
import com.library.bookservice.model.Publisher;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

public record BookUpdate(
        @NotBlank
        String _id,
        String name,
        List<Author> authors,
        int pages,
        List<Genre> genres,
        int publicationYear,
        String asin,
        String summary,
        Publisher publisher,
        String bookCover,
        BigDecimal price
) {


}
