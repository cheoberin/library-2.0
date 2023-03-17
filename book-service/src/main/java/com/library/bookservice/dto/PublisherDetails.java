package com.library.bookservice.dto;

import com.library.bookservice.model.Publisher;

public record PublisherDetails(String _id, String name, String description) {

    public PublisherDetails(Publisher publisher) {
        this(publisher.get_id(), publisher.getName(), publisher.getDescription());
    }

}
