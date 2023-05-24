package repository;

import database.DatabaseReaderService;
import database.DatabaseWriterService;
import domain.Genre;

import java.util.List;

public class GenreRepository {
    private final DatabaseReaderService<Genre> readerService;
    private final DatabaseWriterService<Genre> writerService;

    public GenreRepository(DatabaseReaderService<Genre> readerService, DatabaseWriterService<Genre> writerService) {
        this.readerService = readerService;
        this.writerService = writerService;
    }

    public Genre save(Genre genre) {
        writerService.write(genre);
        return genre;
    }

    public Genre findById(Long id) {
        return readerService.read(Genre.class, id);
    }

    public List<Genre> findAll() {
        return readerService.readAll(Genre.class);
    }

    public void delete(Genre genre) {
        writerService.delete(genre);
    }

    public void update(Genre genreToUpdate) {
        writerService.update(genreToUpdate);
    }
}
