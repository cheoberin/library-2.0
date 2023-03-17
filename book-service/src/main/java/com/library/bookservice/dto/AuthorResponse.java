package com.library.bookservice.dto;

import com.library.bookservice.model.Author;

public record AuthorResponse(String _id, String name, String nationality) {

    public AuthorResponse(Author author) {
        this(author.get_id(), author.getName(), author.getNationality());
    }
}
