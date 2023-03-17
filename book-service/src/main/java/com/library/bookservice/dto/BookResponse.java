package com.library.bookservice.dto;

import com.library.bookservice.model.Book;

public record BookResponse(String id, String name, int publicationYear, String asin) {

    public BookResponse(Book book) {
        this(book.get_id(), book.getName(), book.getPublicationYear(), book.getAsin());
    }

}
