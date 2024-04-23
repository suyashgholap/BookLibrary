package com.suyashg.booklibrary.service;

import com.suyashg.booklibrary.model.Author;
import com.suyashg.booklibrary.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAuthors() {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("Author 1");

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Author 2");

        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        List<Author> authors = authorService.getAllAuthors();

        assertNotNull(authors);
        assertEquals(2, authors.size());
        assertEquals("Author 1", authors.get(0).getName());
        assertEquals("Author 2", authors.get(1).getName());
    }

    @Test
    void testGetAuthorById() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Author 1");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Optional<Author> optionalAuthor = authorService.getAuthorById(1L);

        assertTrue(optionalAuthor.isPresent());
        assertEquals("Author 1", optionalAuthor.get().getName());
    }

    @Test
    void testSaveAuthor() {
        Author author = new Author();
        author.setName("New Author");

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author savedAuthor = authorService.saveAuthor(author);

        assertNotNull(savedAuthor);
        assertEquals("New Author", savedAuthor.getName());
    }

    @Test
    void testUpdateAuthor() {
        Author existingAuthor = new Author();
        existingAuthor.setId(1L);
        existingAuthor.setName("Existing Author");

        Author updatedAuthor = new Author();
        updatedAuthor.setId(1L);
        updatedAuthor.setName("Updated Author");

        when(authorRepository.existsById(1L)).thenReturn(true);
        when(authorRepository.save(updatedAuthor)).thenReturn(updatedAuthor);

        Author returnedAuthor = authorService.updateAuthor(1L, updatedAuthor);

        assertNotNull(returnedAuthor);
        assertEquals("Updated Author", returnedAuthor.getName());
    }

    @Test
    void testUpdateAuthor_AuthorNotFound() {
        Author updatedAuthor = new Author();
        updatedAuthor.setId(1L);
        updatedAuthor.setName("Updated Author");

        when(authorRepository.existsById(1L)).thenReturn(false);

        Author returnedAuthor = authorService.updateAuthor(1L, updatedAuthor);

        assertNull(returnedAuthor);
    }

    @Test
    void testDeleteAuthor() {
        doNothing().when(authorRepository).deleteById(1L);

        assertDoesNotThrow(() -> authorService.deleteAuthor(1L));
    }
}
