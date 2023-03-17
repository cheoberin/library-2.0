package com.library.bookservice.model;

import com.library.bookservice.dto.BookRequest;
import com.library.bookservice.dto.BookUpdate;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Document(value = "book")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Book {

    @Id
    private String _id;
    @NotBlank
    @Indexed(unique = true)
    private String name;
    @DBRef
    @ToString.Exclude
    private List<Author> authors;
    @NotNull
    private int pages;
    @DBRef
    @ToString.Exclude
    private List<Genre> genres;
    @NotNull
    private int publicationYear;
    @NotBlank
    private String asin;
    @NotBlank
    private String summary;
    @DBRef
    @ToString.Exclude
    private Publisher publisher;
    @NotBlank
    private String bookCover;
    @NotNull
    private BigDecimal price;

    public Book(BookRequest bookRequest) {
        this.name = bookRequest.name();
        this.authors = bookRequest.authors();
        this.pages = bookRequest.pages();
        this.genres = bookRequest.genres();
        this.publicationYear = bookRequest.publicationYear();
        this.asin = bookRequest.asin();
        this.summary = bookRequest.summary();
        this.publisher = bookRequest.publisher();
        this.bookCover = bookRequest.bookCover();
        this.price = bookRequest.price();
    }

    public void update(BookUpdate bookUpdate) {

        if (bookUpdate._id() != null) {
            this._id = bookUpdate._id();
        }

        if (bookUpdate.name() != null) {
            this.name = bookUpdate.name();
        }

        if (bookUpdate.authors() != null) {
            this.authors = bookUpdate.authors();
        }

        if (bookUpdate.pages() != 0) {
            this.pages = bookUpdate.pages();
        }

        if (bookUpdate.genres() != null) {
            this.genres = bookUpdate.genres();
        }

        if (bookUpdate.publicationYear() != 0) {
            this.publicationYear = bookUpdate.publicationYear();
        }

        if (bookUpdate.asin() != null) {
            this.asin = bookUpdate.asin();
        }

        if (bookUpdate.summary() != null) {
            this.summary = bookUpdate.summary();
        }

        if (bookUpdate.publisher() != null) {
            this.publisher = bookUpdate.publisher();
        }

        if (bookUpdate.bookCover() != null) {
            this.bookCover = bookUpdate.bookCover();
        }

        if (bookUpdate.price() != null) {
            this.price = bookUpdate.price();
        }
    }
}
