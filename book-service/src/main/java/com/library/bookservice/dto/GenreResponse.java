package com.library.bookservice.dto;

import com.library.bookservice.model.Genre;

public record GenreResponse(String _id, String name) {

    public GenreResponse(Genre genre) {
        this(genre.get_id(), genre.getName());
    }

}
