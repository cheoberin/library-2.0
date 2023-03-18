package com.library.bookservice.service;

import com.library.bookservice.dto.PublisherDetails;
import com.library.bookservice.dto.PublisherRequest;
import com.library.bookservice.dto.PublisherResponse;
import com.library.bookservice.dto.PublisherUpdate;
import com.library.bookservice.exceptions.NotFoundException;
import com.library.bookservice.model.Publisher;
import com.library.bookservice.repository.PublisherRepository;
import com.mongodb.MongoWriteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final int DUPLICATE_ERROR_CODE = 11000;

    public PublisherDetails save(PublisherRequest publisherRequest) {
        Publisher publisher = new Publisher(publisherRequest);
        PublisherDetails publisherDetails = null;

        try {
            publisherDetails = new PublisherDetails(publisherRepository.save(publisher));
            log.info("Publisher saved: {} - {}", publisherDetails._id(), publisherDetails.name());
        } catch (MongoWriteException exception) {
            if (exception.getError().getCode() == DUPLICATE_ERROR_CODE) {
                throw new DuplicateKeyException("Duplicate Field: " + exception.getMessage(), exception);
            }
        }
        return publisherDetails;
    }

    public List<PublisherResponse> findAll() {
        List<Publisher> publishers = publisherRepository.findAll();
        log.info("Publisher's list returned");
        return publishers.stream().map(PublisherResponse::new).toList();
    }

    public void delete(String id) {
        findById(id);
        publisherRepository.deleteById(id);
        log.warn("Genre deleted, id: " + id);
    }

    public PublisherDetails findById(String id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new NotFoundException("Object not Found: " + id + " , type: " + Publisher.class.getName()));
        log.info("Publisher returned: {} - {}", publisher.get_id(), publisher.getName());
        return new PublisherDetails(publisher);
    }

    public PublisherDetails update(PublisherUpdate publisherUpdate) {
        Publisher publisher = publisherRepository.findById(publisherUpdate._id()).orElseThrow(() -> new NotFoundException("Object not Found: " + publisherUpdate._id() + " , type: " + PublisherUpdate.class.getName()));
        publisher.update(publisherUpdate);
        try {
            publisherRepository.save(publisher);
            log.info("Publisher updated, id: " + publisher.get_id());
        } catch (MongoWriteException exception) {
            if (exception.getError().getCode() == DUPLICATE_ERROR_CODE) {
                throw new DuplicateKeyException("Duplicate Field: " + exception.getMessage(), exception);
            }
        }
        return new PublisherDetails(publisher);
    }
}
