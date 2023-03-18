package com.library.bookservice.controller;

import com.library.bookservice.dto.BookDetails;
import com.library.bookservice.dto.BookRequest;
import com.library.bookservice.dto.BookResponse;
import com.library.bookservice.dto.BookUpdate;
import com.library.bookservice.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid BookRequest bookRequest, UriComponentsBuilder uriComponentsBuilder) {
        BookDetails bookDetails = bookService.save(bookRequest);
        URI uri = uriComponentsBuilder.path("/api/book/{id}").buildAndExpand(bookDetails._id()).toUri();
        return ResponseEntity.created(uri).body(bookDetails);
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<BookResponse> books = bookService.findAll();
        return ResponseEntity.ok().body(books);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        BookDetails bookDetails = bookService.findById(id);
        return ResponseEntity.ok().body(bookDetails);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid BookUpdate bookUpdate) {
        BookDetails bookDetails = bookService.update(bookUpdate);
        return ResponseEntity.ok().body(bookDetails);
    }

}
