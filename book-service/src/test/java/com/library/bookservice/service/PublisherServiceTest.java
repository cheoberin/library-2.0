package com.library.bookservice.service;

import com.library.bookservice.dto.PublisherDetails;
import com.library.bookservice.dto.PublisherRequest;
import com.library.bookservice.dto.PublisherResponse;
import com.library.bookservice.dto.PublisherUpdate;
import com.library.bookservice.exceptions.NotFoundException;
import com.library.bookservice.model.Publisher;
import com.library.bookservice.repository.PublisherRepository;
import com.mongodb.MongoWriteException;
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
class PublisherServiceTest {
    public static final String ID = "stre754677522r";
    public static final String NAME = "Milenium";
    public static final String DESCRIP = "testes";

    public static final String TESTID_INVALID = "testidInvalido";

    public static final String OBJECT_NOT_FOUND = "Object not Found: " + TESTID_INVALID + " , type: " +
            Publisher.class.getName();

    public static final String DATA_VIOLATION_MESSAGE = "Error creating :" + Publisher.class.getName();

    @InjectMocks
    private PublisherService publisherService;

    @Spy
    private PublisherRepository publisherRepository;

    @Mock
    private Publisher publisher;

    Optional<Publisher> publisherOptional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        initModels();
    }

    @Test
    void whenFindByIdThenReturnAnPublisherInstance() {
        when(publisherRepository.findById(anyString())).thenReturn(publisherOptional);

        PublisherDetails response = publisherService.findById(ID);

        assertNotNull(response);
        assertEquals(PublisherDetails.class, response.getClass());
        assertEquals(ID, response._id());


    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {

        try {
            publisherService.findById(TESTID_INVALID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals(OBJECT_NOT_FOUND, ex.getMessage());
        }


    }

    @Test
    void whenFindAllThenReturnAListOfPublishers() {
        when(publisherRepository.findAll()).thenReturn(List.of(publisher));

        List<PublisherResponse> response = publisherService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(PublisherResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0)._id());

    }


    @Test
    void whenCreateThenReturnSuccess() {
        when(publisherRepository.save(any())).thenReturn(publisher);

        PublisherRequest publisherRequest = new PublisherRequest(publisher.getName(), publisher.getDescription());

        PublisherDetails response = publisherService.save(publisherRequest);

        assertNotNull(response);
        assertEquals(PublisherDetails.class, response.getClass());
        assertEquals(ID, response._id());
        assertEquals(NAME, response.name());
    }

    @Test
    void whenCreateThenReturnDataViolationException() {
        PublisherRequest publisherRequest = new PublisherRequest(NAME,DESCRIP);
        when(publisherRepository.save(any())).thenThrow(new NullPointerException());

        try {
            publisherService.save(publisherRequest);
        } catch (Exception ex) {
            assertEquals(NullPointerException.class, ex.getClass());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(publisherRepository.findById(anyString())).thenReturn(publisherOptional);
        when(publisherRepository.save(any())).thenReturn(publisher);

        PublisherUpdate publisherUpdate = new PublisherUpdate(ID,NAME,DESCRIP);

        PublisherDetails response = publisherService.update(publisherUpdate);

        assertNotNull(response);
        assertEquals(PublisherDetails.class, response.getClass());
        assertEquals(ID, response._id());
        assertEquals(NAME, response.name());


    }

    @Test
    void whenUpdateThenReturnObjectNotFound() {
        when(publisherRepository.findById(any())).thenThrow(new NotFoundException(OBJECT_NOT_FOUND));
        PublisherUpdate publisherUpdate = new PublisherUpdate(ID,NAME,DESCRIP);

        try {
            publisherService.update(publisherUpdate);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals(OBJECT_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {
        when(publisherRepository.findById(anyString())).thenReturn(publisherOptional);
        doNothing().when(publisherRepository).delete(any());
        publisherService.delete(ID);
        verify(publisherRepository, times(1)).deleteById(any());

    }

    @Test
    void deleteWithObjectNotFound() {
        when(publisherRepository.findById(any())).thenThrow(new NotFoundException(OBJECT_NOT_FOUND));

        try {
            publisherService.delete(ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals(OBJECT_NOT_FOUND, ex.getMessage());
        }
    }

    void initModels() {
        publisher = new Publisher(ID, NAME, DESCRIP);
        publisherOptional = Optional.of(new Publisher(ID, NAME, DESCRIP));

    }
}