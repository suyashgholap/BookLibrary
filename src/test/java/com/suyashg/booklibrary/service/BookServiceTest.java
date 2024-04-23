package com.suyashg.booklibrary.service;

import com.suyashg.booklibrary.model.Book;
import com.suyashg.booklibrary.repository.BookRepository;
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

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();

        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("Book 1", books.get(0).getTitle());
        assertEquals("Book 2", books.get(1).getTitle());
    }

    @Test
    void testGetBookById() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> optionalBook = bookService.getBookById(1L);

        assertTrue(optionalBook.isPresent());
        assertEquals("Book 1", optionalBook.get().getTitle());
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setTitle("New Book");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.saveBook(book);

        assertNotNull(savedBook);
        assertEquals("New Book", savedBook.getTitle());
    }

    @Test
    void testUpdateBook() {
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Existing Book");

        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Book");

        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);

        Book returnedBook = bookService.updateBook(1L, updatedBook);

        assertNotNull(returnedBook);
        assertEquals("Updated Book", returnedBook.getTitle());
    }

    @Test
    void testUpdateBook_BookNotFound() {
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Book");

        when(bookRepository.existsById(1L)).thenReturn(false);

        Book returnedBook = bookService.updateBook(1L, updatedBook);

        assertNull(returnedBook);
    }

    @Test
    void testDeleteBook() {
        doNothing().when(bookRepository).deleteById(1L);

        assertDoesNotThrow(() -> bookService.deleteBook(1L));
    }
}
