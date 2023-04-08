package repository;

import domain.LibraryMember;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryMemberRepository {

    private Map<Long, LibraryMember> libraryMembers = new HashMap<>();

    public void save(LibraryMember member) {
        libraryMembers.put(member.getId(), member);
    }

    public LibraryMember findById(Long id) {
        return libraryMembers.get(id);
    }

    public List<LibraryMember> findAll() {
        return new ArrayList<>(libraryMembers.values());
    }

    public void deleteById(Long id) {
        libraryMembers.remove(id);
    }
}
