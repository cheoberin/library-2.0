package com.library.bookservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.bookservice.model.Author;

import java.time.LocalDate;

public record AuthorDetails(String _id, String name, @JsonFormat(pattern="yyyy-MM-dd") LocalDate birthDate, String nationality, String description) {
    public AuthorDetails(Author author) {
        this(author.get_id(), author.getName(), author.getBirthDate(), author.getNationality(), author.getDescription());
    }
}
