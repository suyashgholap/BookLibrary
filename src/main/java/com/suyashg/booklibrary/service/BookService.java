package com.suyashg.booklibrary.service;

import com.suyashg.booklibrary.model.Book;
import com.suyashg.booklibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        if (bookRepository.existsById(id)) {
            updatedBook.setId(id); // Ensure the ID is set
            return bookRepository.save(updatedBook);
        }
        return null; // Or throw an exception indicating book not found
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
