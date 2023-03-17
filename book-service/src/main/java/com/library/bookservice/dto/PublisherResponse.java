package com.library.bookservice.dto;

import com.library.bookservice.model.Publisher;

public record PublisherResponse(String _id, String name) {

    public PublisherResponse(Publisher publisher) {
        this(publisher.get_id(), publisher.getName());
    }

}
