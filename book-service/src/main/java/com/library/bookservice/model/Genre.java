package com.library.bookservice.model;

import com.library.bookservice.dto.GenreRequest;
import com.library.bookservice.dto.GenreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document(value = "genre")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Genre {

    public Genre(GenreRequest genreRequest) {
        this.name = genreRequest.name();
        this.description = genreRequest.description();
    }

    public Genre(Genre genre) {
        this._id = genre.get_id();
        this.name = genre.getName();
        this.description = genre.getDescription();
    }

    public void update(GenreUpdate genreUpdate) {

        if (genreUpdate._id() != null) {
            this._id = genreUpdate._id();
        }

        if (genreUpdate.name() != null) {
            this.name = genreUpdate.name();
        }

        if (genreUpdate.description() != null) {
            this.description = genreUpdate.description();
        }
    }

    @Id
    private String _id;

    @NotBlank
    @Indexed(unique = true)
    private String name;
    private String description;

}
