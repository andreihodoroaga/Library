package service;

import domain.LibraryStaff;

import java.util.List;

public interface ILibraryStaffService {

    LibraryStaff findById(Long id);

    List<LibraryStaff> findAll();

    void save(LibraryStaff libraryStaff);

    void update(LibraryStaff libraryStaff);

    void delete(Long id);
}
