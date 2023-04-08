package service;

import domain.Author;
import domain.Book;
import domain.Genre;
import repository.BookRepository;

import java.util.List;
import java.util.Optional;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void update(Book book) {
        bookRepository.update(book);
    }

    public void delete(Long id) {
        bookRepository.delete(id);
    }

    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Optional<Book> findFirstByTitle(String title) {
        return bookRepository.findFirstByTitle(title);
    }

    public List<Book> filterByAuthor(Author author) {
        return bookRepository.filterByAuthor(author);
    }
    public List<Book> filterByGenre(Genre genre) {
        return bookRepository.filterByGenre(genre);
    }
}
