package com.library.bookservice.service;

import com.library.bookservice.dto.*;
import com.library.bookservice.exceptions.NotFoundException;
import com.library.bookservice.model.Genre;
import com.library.bookservice.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
@SpringBootTest
class GenreServiceTest {

    public static final String ID = "stre7trtewyrtew212";
    public static final String NAME = "Romance";

    public static final String TESTID_INVALID = "testidInvalido";

    public static final String DESCRIP = "testes";
    public static final String OBJECT_NOT_FOUND = "Object not Found: " + TESTID_INVALID + " , type: " +
            Genre.class.getName();

    public static final String DATA_VIOLATION_MESSAGE = "Error creating :" + Genre.class.getName();

    @InjectMocks
    private GenreService genreService;

    @Spy
    private GenreRepository genreRepository;
    @Mock
    private Genre genre;

    Optional<Genre> genreOptional;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        initModels();
    }

    @Test
    void whenFindByIdThenReturnAnGenreInstance() {
        when(genreRepository.findById(anyString())).thenReturn(genreOptional);

        GenreDetails response = genreService.findById(ID);

        assertNotNull(response);
        assertEquals(GenreDetails.class,response.getClass());
        assertEquals(ID,response._id());


    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){

        try{
            genreService.findById(TESTID_INVALID);
        }
        catch(Exception ex){
            assertEquals(NotFoundException.class,ex.getClass());
            assertEquals(OBJECT_NOT_FOUND,ex.getMessage());
        }


    }

    @Test
    void whenFindAllThenReturnAListOfGenres() {
        when(genreRepository.findAll()).thenReturn(List.of(genre));

        List<GenreResponse> response = genreService.findAll();

        assertNotNull(response);
        assertEquals(1,response.size());
        assertEquals(GenreResponse.class,response.get(0).getClass());
        assertEquals(ID,response.get(0)._id());

    }


    @Test
    void whenCreateThenReturnSuccess() {
        when(genreRepository.save(any())).thenReturn(genre);

        GenreRequest genreRequest = new GenreRequest(genre.getName(), genre.getDescription());
        GenreDetails response = genreService.save(genreRequest);

        assertNotNull(response);
        assertEquals(GenreDetails.class,response.getClass());
        assertEquals(ID,response._id());
        assertEquals(NAME,response.name());

    }
    @Test
    void whenCreateThenReturnDataViolationException() {
        GenreRequest genreRequest = new GenreRequest(NAME,DESCRIP);
        when(genreRepository.save(any())).thenThrow(new NullPointerException());

        try {
            genreService.save(genreRequest);
        }catch (Exception ex){
            assertEquals(NullPointerException.class, ex.getClass());
        }

    }
    @Test
    void whenUpdateThenReturnSuccess() {
        GenreUpdate genreUpdate = new GenreUpdate(ID,NAME,DESCRIP);
        when(genreRepository.findById(anyString())).thenReturn(genreOptional);
        when(genreRepository.save(any())).thenReturn(genre);

        GenreDetails response = genreService.update(genreUpdate);

        assertNotNull(response);
        assertEquals(GenreDetails.class,response.getClass());
        assertEquals(ID,response._id());
        assertEquals(NAME,response.name());


    }

    @Test
    void whenUpdateThenReturnObjectNotFound() {
        GenreUpdate genreUpdate = new GenreUpdate(ID,NAME,DESCRIP);
        when(genreRepository.findById(any())).thenThrow(new NotFoundException(OBJECT_NOT_FOUND));

        try{
            genreService.update(genreUpdate);
        }
        catch(Exception ex){
            assertEquals(NotFoundException.class,ex.getClass());
            assertEquals(OBJECT_NOT_FOUND,ex.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {
        when(genreRepository.findById(anyString())).thenReturn(genreOptional);
        doNothing().when(genreRepository).delete(any());
        genreService.delete(ID);
        verify(genreRepository,times(1)).deleteById(any());

    }

    @Test
    void deleteWithObjectNotFound() {
        when(genreRepository.findById(any())).thenThrow(new NotFoundException(OBJECT_NOT_FOUND));

        try{
            genreService.delete(ID);
        }
        catch(Exception ex){
            assertEquals(NotFoundException.class,ex.getClass());
            assertEquals(OBJECT_NOT_FOUND,ex.getMessage());
        }
    }
    void initModels(){
        genre = new Genre(ID, NAME,DESCRIP);
        genreOptional = Optional.of(new Genre(ID, NAME,DESCRIP));

    }
}