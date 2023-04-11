package com.library.bookservice.model;

import com.library.bookservice.dto.GenreRequest;
import com.library.bookservice.dto.GenreUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "genre")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Genre {

    @Id
    private String _id;

    @NotBlank
    @Indexed(unique = true)
    private String name;
    @NotBlank
    private String description;

    public Genre(GenreRequest genreRequest) {
        this.name = genreRequest.name();
        this.description = genreRequest.description();
    }

    public void update(@Valid GenreUpdate genreUpdate) {

        if (genreUpdate.name() != null) {
            this.name = genreUpdate.name();
        }

        if (genreUpdate.description() != null) {
            this.description = genreUpdate.description();
        }
    }
}
