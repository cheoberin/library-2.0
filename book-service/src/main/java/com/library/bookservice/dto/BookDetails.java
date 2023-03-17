package com.library.bookservice.dto;

import com.library.bookservice.model.Book;

import java.math.BigDecimal;
import java.util.List;

public record BookDetails(String _id, String name, List<AuthorResponse> authors, int pages, List<GenreResponse> genres,
                          int publicationYear, String asin, String summary, PublisherResponse publisherResponse,
                          String bookCover, BigDecimal price) {

    public BookDetails(Book book) {
        this(book.get_id(), book.getName(), book.getAuthors().stream().map(AuthorResponse::new).toList(), book.getPages(),
                book.getGenres().stream().map(GenreResponse::new).toList(), book.getPublicationYear(),
                book.getAsin(), book.getSummary(), new PublisherResponse(book.getPublisher()),
                book.getBookCover(), book.getPrice());
    }
}
