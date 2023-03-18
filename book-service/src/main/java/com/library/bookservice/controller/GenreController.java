package com.library.bookservice.controller;


import com.library.bookservice.dto.GenreDetails;
import com.library.bookservice.dto.GenreRequest;
import com.library.bookservice.dto.GenreResponse;
import com.library.bookservice.dto.GenreUpdate;
import com.library.bookservice.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genre")
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid GenreRequest genreRequest, UriComponentsBuilder uriComponentsBuilder) {
        GenreDetails genreDetails = genreService.save(genreRequest);
        URI uri = uriComponentsBuilder.path("/api/genre/{id}").buildAndExpand(genreDetails._id()).toUri();
        return ResponseEntity.created(uri).body(genreDetails);
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<GenreResponse> genres = genreService.findAll();
        return ResponseEntity.ok().body(genres);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        GenreDetails genreDetails = genreService.findById(id);
        return ResponseEntity.ok().body(genreDetails);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid GenreUpdate genreUpdate) {
        GenreDetails genreDetails = genreService.update(genreUpdate);
        return ResponseEntity.ok().body(genreDetails);
    }

}
