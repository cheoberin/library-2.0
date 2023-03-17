package com.library.bookservice.model;

import com.library.bookservice.dto.AuthorRequest;
import com.library.bookservice.dto.AuthorUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Document(value = "author")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Author {

    public Author(Author author) {
        this._id = author.get_id();
        this.name = author.getName();
        this.birthDate = author.getBirthDate();
        this.nationality = author.getNationality();
        this.description = author.getDescription();
    }

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

    @Id
    private String _id;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private String nationality;
    @NotBlank
    private String description;

}
