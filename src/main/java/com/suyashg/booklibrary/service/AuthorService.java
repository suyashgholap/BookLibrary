package com.suyashg.booklibrary.service;

import com.suyashg.booklibrary.model.Author;
import com.suyashg.booklibrary.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        if (authorRepository.existsById(id)) {
            updatedAuthor.setId(id); // Ensure the ID is set
            return authorRepository.save(updatedAuthor);
        }
        return null; // Or throw an exception indicating author not found
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
