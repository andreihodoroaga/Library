package service;
import domain.LibraryStaff;
import repository.LibraryStaffRepository;

import java.util.List;

public class LibraryStaffService implements ILibraryStaffService {

    private LibraryStaffRepository libraryStaffRepository;

    public LibraryStaffService(LibraryStaffRepository libraryStaffRepository) {
        this.libraryStaffRepository = libraryStaffRepository;
    }

    @Override
    public LibraryStaff findById(Long id) {
        return libraryStaffRepository.findById(id);
    }

    @Override
    public List<LibraryStaff> findAll() {
        return libraryStaffRepository.findAll();
    }
    @Override
    public void save(LibraryStaff libraryStaff) {
        libraryStaffRepository.save(libraryStaff);
    }

    @Override
    public void update(LibraryStaff libraryStaff) {
        libraryStaffRepository.update(libraryStaff);
    }

    @Override
    public void delete(Long id) {
        libraryStaffRepository.delete(id);
    }
}
