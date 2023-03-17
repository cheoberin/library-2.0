package com.library.bookservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.bookservice.dto.BookRequest;
import com.library.bookservice.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class BookServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.3");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookRepository bookRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

//    @Test
//    void shouldCreateBook() throws Exception {
//        BookRequest bookRequest = getBookCreate();
//        String bookRequestString = objectMapper.writeValueAsString(bookRequest);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/book")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(bookRequestString))
//                .andExpect(status().isCreated());
//
//        Assertions.assertEquals(1, bookRepository.findAll().size());
//    }

//    private BookRequest getBookCreate() {
//        return BookRequest.builder()
//                .name("Livro Livro")
//                .pages(1500)
//                .publicationYear(2020)
//                .asin("ASDF12345")
//                .summary("SUMMARY OF BOOK")
//                .bookCover("BOOK COVER LINK")
//                .build();
//    }

}
