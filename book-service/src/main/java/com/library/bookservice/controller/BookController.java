package com.library.bookservice.controller;

import com.library.bookservice.dto.BookDetails;
import com.library.bookservice.dto.BookRequest;
import com.library.bookservice.dto.BookResponse;
import com.library.bookservice.dto.BookUpdate;
import com.library.bookservice.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    @Autowired
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDetails> create(@RequestBody @Valid BookRequest bookRequest, UriComponentsBuilder uriComponentsBuilder) {
        BookDetails bookDetails = bookService.save(bookRequest);
        URI uri = uriComponentsBuilder.path("/api/book/{id}").buildAndExpand(bookDetails._id()).toUri();
        return ResponseEntity.created(uri).body(bookDetails);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAll() {
        List<BookResponse> books = bookService.findAll();
        return ResponseEntity.ok().body(books);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDetails> delete(@PathVariable String id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        log.info(id);
        BookDetails bookDetails = bookService.findById(id);
        return ResponseEntity.ok().body(bookDetails);
    }

    @PutMapping
    public ResponseEntity<BookDetails> update(@RequestBody @Valid BookUpdate bookUpdate) {
        BookDetails bookDetails = bookService.update(bookUpdate);
        return ResponseEntity.ok().body(bookDetails);
    }

}
