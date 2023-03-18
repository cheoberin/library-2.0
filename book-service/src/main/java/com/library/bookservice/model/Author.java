package com.library.bookservice.model;

import com.library.bookservice.dto.AuthorRequest;
import com.library.bookservice.dto.AuthorUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(value = "author")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Author {

    @Id
    private String _id;
    @NotBlank
    @Indexed(unique = true)
    private String name;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private String nationality;
    @NotBlank
    private String description;

    public Author(AuthorRequest authorRequest) {
        this.name = authorRequest.name();
        this.birthDate = authorRequest.birthDate();
        this.nationality = authorRequest.nationality();
        this.description = authorRequest.description();
    }

    public void update(AuthorUpdate authorUpdate) {

        if (authorUpdate._id() != null) {
            this.name = authorUpdate.name();
        }

        if (authorUpdate._id() != null) {
            this.birthDate = authorUpdate.birthDate();
        }

        if (authorUpdate._id() != null) {
            this.nationality = authorUpdate.nationality();
        }

        if (authorUpdate._id() != null) {
            this.description = authorUpdate.description();
        }
    }

}
