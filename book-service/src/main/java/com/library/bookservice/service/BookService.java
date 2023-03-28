package com.library.bookservice.service;

import com.library.bookservice.config.CustomKeyGenerator;
import com.library.bookservice.dto.BookDetails;
import com.library.bookservice.dto.BookRequest;
import com.library.bookservice.dto.BookResponse;
import com.library.bookservice.dto.BookUpdate;
import com.library.bookservice.exceptions.NotFoundException;
import com.library.bookservice.model.Book;
import com.library.bookservice.repository.BookRepository;
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
@CacheConfig(cacheNames = {"books"})
public class BookService {
    @Autowired
    private final BookRepository bookRepository;
    private final int DUPLICATE_ERROR_CODE = 11000;

    @Autowired
    private CustomKeyGenerator customKeyGenerator;

    @CacheEvict(cacheNames = "books", allEntries = true)
    public BookDetails save(BookRequest bookRequest) {
        Book book = new Book(bookRequest);
        BookDetails bookDetails = null;

        try {
            bookDetails = new BookDetails(bookRepository.save(book));
            log.info("Book saved: {} - {}", bookDetails._id(), bookDetails.name());
        } catch (MongoWriteException exception) {
            if (exception.getError().getCode() == DUPLICATE_ERROR_CODE) {
                throw new DuplicateKeyException("Duplicate Field: " + exception.getMessage(), exception);
            }
        }

        return bookDetails;
    }

    @Cacheable(keyGenerator = "customKeyGenerator")
    public List<BookResponse> findAll() {
        List<Book> books = bookRepository.findAll();
        log.info("Book's list returned");
        return books.stream().map(BookResponse::new).toList();
    }

    @CacheEvict(cacheNames = "books", allEntries = true)
    public void delete(String id) {
        findById(id);
        bookRepository.deleteById(id);
        log.warn("Book deleted, id: " + id);
    }

    @Cacheable(keyGenerator = "customKeyGenerator")
    public BookDetails findById(String id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Object not Found: " + id + " , type: " + Book.class.getName()));
        log.info("Book returned: {} - {}", book.get_id(), book.getName());
        return new BookDetails(book);
    }

    @CacheEvict(cacheNames = "books", allEntries = true)
    public BookDetails update(BookUpdate bookUpdate) {
        Book book = bookRepository.findById(bookUpdate._id()).orElseThrow(() -> new NotFoundException("Object not Found: " + bookUpdate._id() + " , type: " + Book.class.getName()));
        book.update(bookUpdate);
        try {
            bookRepository.save(book);
            log.info("Book updated, id: " + book.get_id());
        } catch (MongoWriteException exception) {
            if (exception.getError().getCode() == DUPLICATE_ERROR_CODE) {
                throw new DuplicateKeyException("Duplicate Field: " + exception.getMessage(), exception);
            }
        }

        return new BookDetails(book);
    }
}
