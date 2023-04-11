package com.library.bookservice.model;

import com.library.bookservice.dto.PublisherRequest;
import com.library.bookservice.dto.PublisherUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "publisher")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Publisher {

    @Id
    private String _id;
    @NotBlank
    @Indexed(unique = true)
    private String name;
    @NotBlank
    private String description;

    public Publisher(@Valid PublisherRequest publisherRequest) {
        this.name = publisherRequest.name();
        this.description = publisherRequest.description();
    }

    public void update(@Valid PublisherUpdate publisherUpdate) {

        if (publisherUpdate.name() != null) {
            this.name = publisherUpdate.name();
        }

        if (publisherUpdate.description() != null) {
            this.description = publisherUpdate.description();
        }
    }
}
