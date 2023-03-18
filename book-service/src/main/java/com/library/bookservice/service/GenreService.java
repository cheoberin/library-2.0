package com.library.bookservice.service;

import com.library.bookservice.dto.GenreDetails;
import com.library.bookservice.dto.GenreRequest;
import com.library.bookservice.dto.GenreResponse;
import com.library.bookservice.dto.GenreUpdate;
import com.library.bookservice.exceptions.NotFoundException;
import com.library.bookservice.model.Genre;
import com.library.bookservice.repository.GenreRepository;
import com.mongodb.MongoWriteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {

    private final GenreRepository genreRepository;
    private final int DUPLICATE_ERROR_CODE = 11000;

    public GenreDetails save(GenreRequest genreRequest) {
        Genre genre = new Genre(genreRequest);
        GenreDetails genreDetails = null;
        try {
            genreDetails = new GenreDetails(genreRepository.save(genre));
            log.info("Genre saved: {} - {}", genreDetails._id(), genreDetails.name());

        } catch (MongoWriteException exception) {
            if (exception.getError().getCode() == DUPLICATE_ERROR_CODE) {
                throw new DuplicateKeyException("Duplicate Field: " + exception.getMessage(), exception);
            }
        }
        return genreDetails;
    }

    public List<GenreResponse> findAll() {
        List<Genre> genres = genreRepository.findAll();
        log.info("Genre's list returned");
        return genres.stream().map(GenreResponse::new).toList();
    }

    public void delete(String id) {
        findById(id);
        genreRepository.deleteById(id);
        log.warn("Genre deleted, id: " + id);
    }

    public GenreDetails findById(String id) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NotFoundException("Object not Found: " + id + " , type: " + Genre.class.getName()));
        log.info("Genre returned: {} - {}", genre.get_id(), genre.getName());
        return new GenreDetails(genre);
    }

    public GenreDetails update(GenreUpdate genreUpdate) {
        Genre genre = genreRepository.findById(genreUpdate._id()).orElseThrow(() -> new NotFoundException("Object not Found: " + genreUpdate._id() + " , type: " + Genre.class.getName()));
        genre.update(genreUpdate);
        try {
            genreRepository.save(genre);
            log.info("Genre updated, id: " + genre.get_id());
        } catch (MongoWriteException exception) {
            if (exception.getError().getCode() == DUPLICATE_ERROR_CODE) {
                throw new DuplicateKeyException("Duplicate Field: " + exception.getMessage(), exception);
            }
        }
        return new GenreDetails(genre);
    }
}