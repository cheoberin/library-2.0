package com.library.bookservice.service;

import com.library.bookservice.config.CustomKeyGenerator;
import com.library.bookservice.dto.AuthorDetails;
import com.library.bookservice.dto.AuthorRequest;
import com.library.bookservice.dto.AuthorResponse;
import com.library.bookservice.dto.AuthorUpdate;
import com.library.bookservice.exceptions.NotFoundException;
import com.library.bookservice.model.Author;
import com.library.bookservice.repository.AuthorRepository;
import com.mongodb.MongoWriteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = {"authors"})
public class AuthorService {

    @Autowired
    private final AuthorRepository authorRepository;

    @Autowired
    private CustomKeyGenerator customKeyGenerator;

    private final int DUPLICATE_ERROR_CODE = 11000;

    @CacheEvict(cacheNames = "authors", allEntries = true)
    public AuthorDetails save(AuthorRequest authorRequest) {
        Author author = new Author(authorRequest);
        AuthorDetails authorDetails = null;
        try {
            authorDetails = new AuthorDetails(authorRepository.save(author));
            log.info("Author saved: {} - {}", authorDetails._id(), authorDetails.name());
        } catch (MongoWriteException exception) {
            if (exception.getError().getCode() == DUPLICATE_ERROR_CODE) {
                throw new DuplicateKeyException("Duplicate Field: " + exception.getMessage(), exception);
            }
        }
        return authorDetails;
    }

    @Cacheable(keyGenerator = "customKeyGenerator")
    public List<AuthorResponse> findAll() {
        List<Author> authors = authorRepository.findAll();
        log.info("Author's list returned");
        return authors.stream().map(AuthorResponse::new).toList();
    }

    @CacheEvict(cacheNames = "authors", allEntries = true)
    public void delete(String id) {
        findById(id);
        authorRepository.deleteById(id);
        log.warn("Author deleted, id: " + id);
    }

    @Cacheable(keyGenerator = "customKeyGenerator")
    public AuthorDetails findById(String id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Object not Found: " + id + " , type: " + Author.class.getName()));
        log.info("Author returned: {} - {}", author.get_id(), author.getName());
        return new AuthorDetails(author);
    }

    @CacheEvict(cacheNames = "authors", allEntries = true)
    public AuthorDetails update(AuthorUpdate authorUpdate) {
        Author author = authorRepository.findById(authorUpdate._id()).orElseThrow(() -> new NotFoundException("Object not found: " + authorUpdate._id() + ", type: " + Author.class.getName()));
        author.update(authorUpdate);

        try {
            authorRepository.save(author);
            log.info("Author updated, id: " + author.get_id());
        } catch (MongoWriteException exception) {
            if (exception.getError().getCode() == DUPLICATE_ERROR_CODE) {
                throw new DuplicateKeyException("Duplicate Field: " + exception.getMessage(), exception);
            }
        }
        return new AuthorDetails(author);
    }
}
