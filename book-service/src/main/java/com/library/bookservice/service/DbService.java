package com.library.bookservice.service;

import com.library.bookservice.dto.AuthorRequest;
import com.library.bookservice.dto.BookRequest;
import com.library.bookservice.dto.GenreRequest;
import com.library.bookservice.dto.PublisherRequest;
import com.library.bookservice.model.Author;
import com.library.bookservice.model.Book;
import com.library.bookservice.model.Genre;
import com.library.bookservice.model.Publisher;
import com.library.bookservice.repository.AuthorRepository;
import com.library.bookservice.repository.BookRepository;
import com.library.bookservice.repository.GenreRepository;
import com.library.bookservice.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class DbService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public DbService(AuthorRepository authorRepository, BookRepository bookRepository, GenreRepository genreRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.publisherRepository = publisherRepository;
//        this.userRepository = userRepository;
//        this.userRepository = userRepository;
    }


    public void initDataBaseTest() {

        Author author1 = new Author(new AuthorRequest("Antonie de Saint-Exupéry", LocalDate.of(1900, Month.JUNE, 29),"França",
                "ANTOINE JEAN-BAPTISTE MARIE ROGER FOSCOLOMBE, popularmente conhecido como Antonie de Saint-Exupéry foi escritor, ilustrador e piloto francês."));


        var authors1 = List.of(author1);

        Genre genre1 = new Genre(new GenreRequest("Policial","policial"));
        Genre genre2 = new Genre(new GenreRequest("Supense","suspense"));
        Genre genre3 = new Genre(new GenreRequest("Misterio","misterio"));

        var genres2 = List.of(genre3, genre2);

        Publisher publisher1 = new Publisher(new PublisherRequest("Millenium","mi"));
        Publisher publisher2 = new Publisher(new PublisherRequest("Ms books","ms"));

        Book book1 = new Book(
                new BookRequest(
                        "O pequeno príncipe",
                        authors1,
                        103,
                        genres2,
                        1943,
                        "B0BPR1HQGX",
                        "Um avião pousado no deserto com o motor avariado, um piloto com uma pequena quantidade de água, muito calor durante o dia e frio durante a noite",
                        publisher1,
                        "https://m.media-amazon.com/images/I/41yQ1HW1SwL.jpg",
                        new BigDecimal("111.79")));


        authorRepository.saveAll(List.of(author1));
        genreRepository.saveAll(List.of(genre1, genre2, genre3));
        publisherRepository.saveAll(List.of(publisher1, publisher2));
        bookRepository.saveAll(List.of(book1));

    }

}
