package com.library.bookservice.service;

import com.library.bookservice.dto.AuthorDetails;
import com.library.bookservice.dto.AuthorRequest;
import com.library.bookservice.dto.AuthorResponse;
import com.library.bookservice.dto.AuthorUpdate;
import com.library.bookservice.exceptions.NotFoundException;
import com.library.bookservice.model.Author;
import com.library.bookservice.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorDetails save(AuthorRequest authorRequest) {
        Author author = new @Valid Author(authorRequest);
        AuthorDetails authorDetails = new AuthorDetails(authorRepository.save(author));
        log.info("Author saved: {} - {}", authorDetails._id(), authorDetails.name());
        return authorDetails;
    }

    public List<AuthorResponse> findAll() {
        List<Author> authors = authorRepository.findAll();
        log.info("Author's list returned");
        return authors.stream().map(AuthorResponse::new).toList();
    }

    public void delete(String id) {
        findById(id);
        authorRepository.deleteById(id);
        log.warn("Author deleted, id: " + id);
    }

    public AuthorDetails findById(String id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Object not Found: " + id + " , type: " + Author.class.getName()));
        log.info("Author returned: {} - {}", author.get_id(), author.getName());
        return new AuthorDetails(author);
    }

    public AuthorDetails update(AuthorUpdate authorUpdate) {
        Author author = authorRepository.findById(authorUpdate._id()).orElseThrow(() -> new NotFoundException("Object not found: " + authorUpdate._id() + ", type: " + Author.class.getName()));
        author.update(authorUpdate);
        Author validAuthor = new @Valid Author(author);
        authorRepository.save(validAuthor);
        log.info("Author updated, id: " + author.get_id());
        return new AuthorDetails(author);
    }
}
