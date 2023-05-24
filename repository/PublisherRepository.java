package repository;

import database.DatabaseReaderService;
import database.DatabaseWriterService;
import domain.Publisher;

import java.util.List;

public class PublisherRepository {
    private final DatabaseReaderService<Publisher> readerService;
    private final DatabaseWriterService<Publisher> writerService;

    public PublisherRepository(DatabaseReaderService<Publisher> readerService, DatabaseWriterService<Publisher> writerService) {
        this.readerService = readerService;
        this.writerService = writerService;
    }

    public Publisher save(Publisher publisher) {
        writerService.write(publisher);
        return publisher;
    }

    public Publisher findById(Long id) {
        return readerService.read(Publisher.class, id);
    }

    public List<Publisher> findAll() {
        return readerService.readAll(Publisher.class);
    }

    public void delete(Publisher publisher) {
        writerService.delete(publisher);
    }

    public void update(Publisher publisher) {
        writerService.update(publisher);
    }
}
