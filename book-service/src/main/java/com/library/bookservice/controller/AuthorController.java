package com.library.bookservice.controller;

import com.library.bookservice.dto.AuthorDetails;
import com.library.bookservice.dto.AuthorRequest;
import com.library.bookservice.dto.AuthorResponse;
import com.library.bookservice.dto.AuthorUpdate;
import com.library.bookservice.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorDetails> create(@RequestBody @Valid AuthorRequest authorRequest, UriComponentsBuilder uriComponentsBuilder) {
        AuthorDetails authorDetails = authorService.save(authorRequest);
        URI uri = uriComponentsBuilder.path("/api/author/{id}").buildAndExpand(authorDetails._id()).toUri();
        return ResponseEntity.created(uri).body(authorDetails);
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> findAll() {
        List<AuthorResponse> authors = authorService.findAll();
        return ResponseEntity.ok().body(authors);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorDetails> delete(@PathVariable String id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDetails> findById(@PathVariable String id) {
        AuthorDetails authorDetails = authorService.findById(id);
        return ResponseEntity.ok().body(authorDetails);
    }

    @PutMapping
    public ResponseEntity<AuthorDetails> update(@RequestBody @Valid AuthorUpdate authorUpdate) {
        AuthorDetails authorDetails = authorService.update(authorUpdate);
        return ResponseEntity.ok().body(authorDetails);
    }

}
