package com.library.bookservice.service;

import com.library.bookservice.config.CustomKeyGenerator;
import com.library.bookservice.dto.AuthorDetails;
import com.library.bookservice.dto.AuthorRequest;
import com.library.bookservice.dto.AuthorResponse;
import com.library.bookservice.dto.AuthorUpdate;
import com.library.bookservice.exceptions.NotFoundException;
import com.library.bookservice.model.Author;
import com.library.bookservice.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CustomKeyGenerator customKeyGenerator;

    @InjectMocks
    private AuthorService authorService;

    private AuthorRequest authorRequest;
    private Author author;
    private AuthorDetails authorDetails;
    private AuthorResponse authorResponse;
    private AuthorUpdate authorUpdate;

    private final String ID = "1";
    private final String NAME = "J. R. R. Tolkien";
    private final LocalDate BIRTH_DATE = LocalDate.of(1892, 1, 3);
    private final String NATIONALITY = "English";
    private final String DESCRIPTION = "John Ronald Reuel Tolkien, CBE was an English writer, poet, philologist, and academic.";
    private final List<Author> AUTHORS_LIST = Arrays.asList(new Author(ID, NAME, BIRTH_DATE, NATIONALITY, DESCRIPTION));
    private final List<AuthorResponse> AUTHORS_RESPONSE_LIST = Arrays.asList(new AuthorResponse(ID, NAME, NATIONALITY));

    @BeforeEach
    void setUp() {
        authorRequest = new AuthorRequest(NAME, BIRTH_DATE, NATIONALITY, DESCRIPTION);
        author = new Author(ID, NAME, BIRTH_DATE, NATIONALITY, DESCRIPTION);
        authorDetails = new AuthorDetails(ID, NAME, BIRTH_DATE, NATIONALITY, DESCRIPTION);
        authorResponse = new AuthorResponse(ID, NAME, NATIONALITY);
        authorUpdate = new AuthorUpdate(ID, NAME, BIRTH_DATE, NATIONALITY, DESCRIPTION);
    }

    @Test
    void testSaveNewAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        when(customKeyGenerator.generate(any(), any(), any())).thenReturn(ID);

        AuthorDetails result = authorService.save(authorRequest);

        assertNotNull(result);
        assertEquals(ID, result._id());
        assertEquals(NAME, result.name());
        assertEquals(BIRTH_DATE, result.birthDate());
        assertEquals(NATIONALITY, result.nationality());
        assertEquals(DESCRIPTION, result.description());

        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void testSaveDuplicateAuthor() {
        when(authorRepository.save(any(Author.class))).thenThrow(new DuplicateKeyException("Duplicate Field"));

        assertThrows(DuplicateKeyException.class, () -> authorService.save(authorRequest));

        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void findAll_shouldReturnAuthorResponseList() {

        List<Author> authors = Arrays.asList(new Author(), new Author());
        when(authorRepository.findAll()).thenReturn(authors);

        List<AuthorResponse> result = authorService.findAll();

        assertNotNull(result);
        assertEquals(authors.size(), result.size());


        verify(authorRepository).findAll();
    }

    @Test
    void delete_existingAuthor_shouldDeleteAuthor() {

        Author author = new Author();
        when(authorRepository.findById(anyString())).thenReturn(Optional.of(author));

        authorService.delete("id");

        verify(authorRepository).findById(anyString());
        verify(authorRepository).deleteById(anyString());
    }


    @Test
    void testFindAuthorById() {
        String authorId = "1234";
        Author author = new Author(new AuthorRequest("John Doe", LocalDate.of(1990, 5, 15), "American", "Description"));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        AuthorDetails result = authorService.findById(authorId);

        assertEquals(author.get_id(), result._id());
        assertEquals(author.getName(), result.name());
        assertEquals(author.getBirthDate(), result.birthDate());
        assertEquals(author.getNationality(), result.nationality());
        assertEquals(author.getDescription(), result.description());

        verify(authorRepository, times(1)).findById(authorId);
       verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void testFindAuthorByIdNotFound() {
        String authorId = "1234";
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            authorService.findById(authorId);
        });

        verify(authorRepository, times(1)).findById(authorId);
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void testUpdateAuthor() {
        String authorId = "1234";
        Author originalAuthor = new Author(new AuthorRequest("John Doe", LocalDate.of(1990, 5, 15), "American", "Description"));
        AuthorUpdate authorUpdate = new AuthorUpdate(authorId, "Jane Doe", LocalDate.of(1991, 6, 16), "Canadian", "New Description");
        Author updatedAuthor = new Author(new AuthorRequest("Jane Doe", LocalDate.of(1991, 6, 16), "Canadian", "New Description"));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(originalAuthor));
        when(authorRepository.save(any(Author.class))).thenReturn(updatedAuthor);

        AuthorDetails result = authorService.update(authorUpdate);

        assertEquals(updatedAuthor.get_id(), result._id());
        assertEquals(updatedAuthor.getName(), result.name());
        assertEquals(updatedAuthor.getBirthDate(), result.birthDate());
        assertEquals(updatedAuthor.getNationality(), result.nationality());
        assertEquals(updatedAuthor.getDescription(), result.description());

        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, times(1)).save(updatedAuthor);
        verifyNoMoreInteractions(authorRepository);
    }

}