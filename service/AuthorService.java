package service;

import domain.Author;
import repository.AuthorRepository;

import java.time.LocalDate;
import java.util.List;

public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void addAuthor(String name, LocalDate dateOfBirth) {
        Author author = new Author(name, dateOfBirth);
        authorRepository.add(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.getAll();
    }

    public Author getAuthorById(Long id) {
        return authorRepository.getById(id);
    }

    public void updateAuthor(Long id, String name, LocalDate dateOfBirth) {
        Author author = new Author(name, dateOfBirth);
        author.setId(id);
        authorRepository.update(author);
    }

    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
    }
}
