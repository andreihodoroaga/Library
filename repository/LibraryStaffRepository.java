package repository;

import domain.LibraryStaff;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;;
import java.util.Map;


public class LibraryStaffRepository implements ILibraryStaffRepository {

    private Map<Long, LibraryStaff> staffMap = new HashMap<>();
    private long nextId = 1L;

    @Override
    public LibraryStaff findById(Long id) {
        return staffMap.get(id);
    }

    @Override
    public List<LibraryStaff> findAll() {
        return new ArrayList<>(staffMap.values());
    }

    @Override
    public void save(LibraryStaff libraryStaff) {
        libraryStaff.setId(nextId++);
        staffMap.put(libraryStaff.getId(), libraryStaff);
    }

    @Override
    public void update(LibraryStaff libraryStaff) {
        staffMap.put(libraryStaff.getId(), libraryStaff);
    }

    @Override
    public void delete(Long id) {
        staffMap.remove(id);
    }
}
