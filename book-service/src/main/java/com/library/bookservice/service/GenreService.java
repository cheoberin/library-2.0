package com.library.bookservice.service;

import com.library.bookservice.dto.GenreDetails;
import com.library.bookservice.dto.GenreRequest;
import com.library.bookservice.dto.GenreResponse;
import com.library.bookservice.dto.GenreUpdate;
import com.library.bookservice.exceptions.NotFoundException;
import com.library.bookservice.model.Genre;
import com.library.bookservice.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreDetails save(GenreRequest genreRequest) {
        Genre genre = new @Valid Genre(genreRequest);
        GenreDetails genreDetails = new GenreDetails(genreRepository.save(genre));
        log.info("Genre saved: {} - {}", genreDetails._id(), genreDetails.name());
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
        Genre validGenre = new @Valid Genre(genre);
        genreRepository.save(validGenre);
        log.info("Genre updated, id: " + genre.get_id());
        return new GenreDetails(genre);
    }
}
