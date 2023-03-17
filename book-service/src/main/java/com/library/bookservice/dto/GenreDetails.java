package com.library.bookservice.dto;

import com.library.bookservice.model.Genre;

public record GenreDetails(String _id, String name, String description) {

    public GenreDetails(Genre genre) {
        this(genre.get_id(), genre.getName(), genre.getDescription());
    }

}
