package com.library.bookservice.dto;

import com.library.bookservice.model.Book;

import java.math.BigDecimal;
import java.util.List;

public record BookResponse(String id, String name, List<AuthorResponse> authors, String bookCover,
                           BigDecimal price) {

    public BookResponse(Book book) {
        this(book.get_id(), book.getName(), book.getAuthors().stream().map(AuthorResponse::new).toList(), book.getBookCover(), book.getPrice());
    }
}
