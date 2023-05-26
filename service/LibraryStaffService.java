package service;
import domain.LibraryStaff;
import repository.LibraryStaffRepository;

import java.util.List;

public class LibraryStaffService {

    private LibraryStaffRepository libraryStaffRepository;

    public LibraryStaffService(LibraryStaffRepository libraryStaffRepository) {
        this.libraryStaffRepository = libraryStaffRepository;
    }

    public LibraryStaff findById(Long id) {
        return libraryStaffRepository.findById(id);
    }

    public List<LibraryStaff> findAll() {
        return libraryStaffRepository.findAll();
    }

    public void save(LibraryStaff libraryStaff) {
        libraryStaffRepository.save(libraryStaff);
    }

    public void update(LibraryStaff libraryStaff) {
        libraryStaffRepository.update(libraryStaff);
    }

    public void delete(LibraryStaff libraryStaff) {
        libraryStaffRepository.delete(libraryStaff);
    }
}
