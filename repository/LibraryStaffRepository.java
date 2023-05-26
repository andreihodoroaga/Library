package repository;

import domain.LibraryStaff;
import database.DatabaseReaderService;
import database.DatabaseWriterService;

import java.util.List;

public class LibraryStaffRepository {
    private final DatabaseReaderService<LibraryStaff> readerService;
    private final DatabaseWriterService<LibraryStaff> writerService;

    public LibraryStaffRepository(DatabaseReaderService<LibraryStaff> readerService,
                                  DatabaseWriterService<LibraryStaff> writerService) {
        this.readerService = readerService;
        this.writerService = writerService;
    }

    public LibraryStaff findById(Long id) {
        return readerService.read(LibraryStaff.class, id);
    }

    public List<LibraryStaff> findAll() {
        return readerService.readAll(LibraryStaff.class);
    }

    public void save(LibraryStaff libraryStaff) {
        writerService.write(libraryStaff);
    }

    public void update(LibraryStaff libraryStaff) {
        writerService.update(libraryStaff);
    }

    public void delete(LibraryStaff libraryStaff) {
        writerService.delete(libraryStaff);
    }
}
