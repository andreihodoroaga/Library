package service;

import java.util.List;

import domain.LibraryPerson;
import repository.LibraryPersonRepository;

public class LibraryPersonService {
    private final LibraryPersonRepository libraryPersonRepository;

    public LibraryPersonService(LibraryPersonRepository libraryPersonRepository) {
        this.libraryPersonRepository = libraryPersonRepository;
    }

    public LibraryPerson save(LibraryPerson libraryPerson) {
        return libraryPersonRepository.save(libraryPerson);
    }

    public LibraryPerson findById(Long id) {
        return libraryPersonRepository.findById(id);
    }

    public List<LibraryPerson> findAll() {
        return libraryPersonRepository.findAll();
    }

    public void delete(LibraryPerson libraryPerson) {
        libraryPersonRepository.delete(libraryPerson);
    }

    public void update(LibraryPerson libraryPerson) {
        libraryPersonRepository.update(libraryPerson);
    }
}
