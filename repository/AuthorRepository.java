package repository;

import domain.Author;
import database.DatabaseReaderService;
import database.DatabaseWriterService;

import java.util.List;

public class AuthorRepository {
    private final DatabaseReaderService<Author> readerService;
    private final DatabaseWriterService<Author> writerService;

    public AuthorRepository(DatabaseReaderService<Author> readerService, DatabaseWriterService<Author> writerService) {
        this.readerService = readerService;
        this.writerService = writerService;
    }

    public Author getById(Long id) {
        return readerService.read(Author.class, id);
    }

    public List<Author> getAll() {
        return readerService.readAll(Author.class);
    }

    public Author add(Author author) {
        writerService.write(author);
        return author;
    }

    public void delete(Author author) {
        writerService.delete(author);
    }

    public void update(Author author) {
        writerService.update(author);
    }
}
