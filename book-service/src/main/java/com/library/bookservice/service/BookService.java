package com.library.bookservice.service;

import com.library.bookservice.dto.BookDetails;
import com.library.bookservice.dto.BookRequest;
import com.library.bookservice.dto.BookResponse;
import com.library.bookservice.dto.BookUpdate;
import com.library.bookservice.exceptions.NotFoundException;
import com.library.bookservice.model.Book;
import com.library.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public BookDetails save(BookRequest bookRequest) {
        Book book = new Book(bookRequest);
        BookDetails bookDetails = new BookDetails(bookRepository.save(book));
        log.info("Book saved: {} - {}", bookDetails._id(), bookDetails.name());
        return bookDetails;
    }

    public List<BookResponse> findAll() {
        List<Book> books = bookRepository.findAll();
        log.info("Book's list returned");
        return books.stream().map(BookResponse::new).toList();
    }

    public void delete(String id) {
        findById(id);
        bookRepository.deleteById(id);
        log.warn("Book deleted, id: " + id);
    }

    public BookDetails findById(String id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Object not Found: " + id + " , type: " + Book.class.getName()));
        log.info("Book returned: {} - {}", book.get_id(), book.getName());
        return new BookDetails(book);
    }

    public BookDetails update(BookUpdate bookUpdate) {
        Book book = bookRepository.findById(bookUpdate._id()).orElseThrow(() -> new NotFoundException("Object not Found: " + bookUpdate._id() + " , type: " + Book.class.getName()));
        book.update(bookUpdate);
        bookRepository.save(book);
        log.info("Book updated, id: " + book.get_id());
        return new BookDetails(book);
    }
}
