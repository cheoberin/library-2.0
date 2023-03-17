package com.library.bookservice.controller;


import com.library.bookservice.dto.PublisherDetails;
import com.library.bookservice.dto.PublisherRequest;
import com.library.bookservice.dto.PublisherResponse;
import com.library.bookservice.dto.PublisherUpdate;
import com.library.bookservice.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid PublisherRequest publisherRequest, UriComponentsBuilder uriComponentsBuilder) {
        PublisherDetails publisherDetails = publisherService.save(publisherRequest);
        URI uri = uriComponentsBuilder.path("/api/genre/{id}").buildAndExpand(publisherDetails._id()).toUri();
        return ResponseEntity.created(uri).body(publisherDetails);
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<PublisherResponse> publishers = publisherService.findAll();
        return ResponseEntity.ok().body(publishers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        publisherService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        PublisherDetails publisherDetails = publisherService.findById(id);
        return ResponseEntity.ok().body(publisherDetails);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid PublisherUpdate publisherUpdate) {
        PublisherDetails publisherDetails = publisherService.update(publisherUpdate);
        return ResponseEntity.ok().body(publisherDetails);
    }

}
