package com.suyashg.booklibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suyashg.booklibrary.model.Author;
import com.suyashg.booklibrary.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    void testGetAllAuthors() throws Exception {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("Author 1");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Author 2");

        when(authorService.getAllAuthors()).thenReturn(Arrays.asList(author1, author2));

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Author 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Author 2"));
    }

    @Test
    void testGetAuthorById() throws Exception {
        Author author = new Author();
        author.setId(1L);
        author.setName("Author 1");

        when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author));

        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Author 1"));
    }

    @Test
    void testAddAuthor() throws Exception {
        Author author = new Author();
        author.setName("New Author");

        when(authorService.saveAuthor(any(Author.class))).thenReturn(author);

        mockMvc.perform(post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(author)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("New Author"));
    }

    @Test
    void testUpdateAuthor() throws Exception {
        Author existingAuthor = new Author();
        existingAuthor.setId(1L);
        existingAuthor.setName("Existing Author");

        Author updatedAuthor = new Author();
        updatedAuthor.setId(1L);
        updatedAuthor.setName("Updated Author");

        when(authorService.updateAuthor(anyLong(), any(Author.class))).thenReturn(updatedAuthor);

        mockMvc.perform(put("/api/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedAuthor)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Author"));
    }

    @Test
    void testDeleteAuthor() throws Exception {
        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isNoContent());
    }
}
