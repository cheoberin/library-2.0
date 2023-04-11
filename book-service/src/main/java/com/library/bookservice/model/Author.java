package com.library.bookservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.bookservice.dto.AuthorRequest;
import com.library.bookservice.dto.AuthorUpdate;
import jakarta.validation.Valid;
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
    @JsonFormat(pattern="yyyy-MM-dd")
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

    public void update(@Valid AuthorUpdate authorUpdate) {

        if (authorUpdate.name() != null) {
            this.name = authorUpdate.name();
        }

        if (authorUpdate.birthDate() != null) {
            this.birthDate = authorUpdate.birthDate();
        }

        if (authorUpdate.nationality() != null) {
            this.nationality = authorUpdate.nationality();
        }

        if (authorUpdate.description() != null) {
            this.description = authorUpdate.description();
        }
    }

}
