package com.library.bookservice.model;

import com.library.bookservice.dto.PublisherRequest;
import com.library.bookservice.dto.PublisherUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document(value = "publisher")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Publisher {

    public Publisher(PublisherRequest publisherRequest) {
        this.name = publisherRequest.name();
        this.description = publisherRequest.description();
    }

    public Publisher(Publisher publisher) {
        this._id = publisher.get_id();
        this.name = publisher.getName();
        this.description = publisher.getDescription();
    }

    public void update(PublisherUpdate publisherUpdate) {

        if (publisherUpdate._id() != null) {
            this._id = publisherUpdate._id();
        }

        if (publisherUpdate.name() != null) {
            this.name = publisherUpdate.name();
        }

        if (publisherUpdate.description() != null) {
            this.description = publisherUpdate.description();
        }
    }

    @Id
    private String _id;
    @NotBlank
    @Indexed(unique = true)
    private String name;
    @NotBlank
    private String description;
}
