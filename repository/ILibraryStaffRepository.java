package repository;

import domain.LibraryStaff;

import java.util.List;

public interface ILibraryStaffRepository {

    LibraryStaff findById(Long id);

    List<LibraryStaff> findAll();

    void save(LibraryStaff libraryStaff);

    void update(LibraryStaff libraryStaff);

    void delete(Long id);
}
