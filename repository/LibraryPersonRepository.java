package repository;

import java.util.ArrayList;
import java.util.List;

import domain.LibraryPerson;

public class LibraryPersonRepository {
    private final List<LibraryPerson> libraryPersons;
    private Long nextId;

    public LibraryPersonRepository() {
        libraryPersons = new ArrayList<>();
        nextId = 1L;
    }

    public LibraryPerson save(LibraryPerson libraryPerson) {
        libraryPerson.setId(nextId++);
        libraryPersons.add(libraryPerson);
        return libraryPerson;
    }

    public LibraryPerson findById(Long id) {
        for (LibraryPerson libraryPerson : libraryPersons) {
            if (libraryPerson.getId().equals(id)) {
                return libraryPerson;
            }
        }
        return null;
    }

    public List<LibraryPerson> findAll() {
        return libraryPersons;
    }

    public void delete(LibraryPerson libraryPerson) {
        libraryPersons.remove(libraryPerson);
    }

    public void update(LibraryPerson libraryPerson) {
        for (LibraryPerson lp : libraryPersons) {
            if (lp.getId().equals(libraryPerson.getId())) {
                lp.setName(libraryPerson.getName());
                lp.setEmail(libraryPerson.getEmail());
                lp.setAddress(libraryPerson.getAddress());
            }
        }
    }
}

