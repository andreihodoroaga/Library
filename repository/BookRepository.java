package repository;

import database.DatabaseReaderService;
import database.DatabaseWriterService;
import domain.Author;
import domain.Book;
import domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository {
    private final DatabaseReaderService<Book> readerService;
    private final DatabaseWriterService<Book> writerService;

    public BookRepository(DatabaseReaderService<Book> readerService, DatabaseWriterService<Book> writerService) {
        this.readerService = readerService;
        this.writerService = writerService;
    }

    public Book findById(Long id) {
        return readerService.read(Book.class, id);
    }

    public List<Book> findAll() {
        return readerService.readAll(Book.class);
    }

    public void save(Book book) {
        writerService.write(book);
    }

    public void update(Book book) {
        writerService.update(book);
    }

    public void delete(Book book) {
        writerService.delete(book);
    }

    public List<Book> findByTitle(String title) {
        List<Book> books = new ArrayList<>();
        List<Book> allBooks = findAll();

        for (Book book : allBooks) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                books.add(book);
            }
        }

        return books;
    }

    public Optional<Book> findFirstByTitle(String title) {
        List<Book> books = findByTitle(title);
        return books.isEmpty() ? Optional.empty() : Optional.of(books.get(0));
    }

    public List<Book> filterByAuthor(Author author) {
        List<Book> books = new ArrayList<>();
        List<Book> allBooks = findAll();

        for (Book book : allBooks) {
            if (book.getAuthors().contains(author)) {
                books.add(book);
            }
        }

        return books;
    }

    public List<Book> filterByGenre(Genre genre) {
        List<Book> books = new ArrayList<>();
        List<Book> allBooks = findAll();

        for (Book book : allBooks) {
            if (book.getGenre().equals(genre)) {
                books.add(book);
            }
        }

        return books;
    }
}