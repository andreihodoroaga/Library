package service;

import domain.LibraryMember;
import repository.LibraryMemberRepository;

import java.util.List;

public class LibraryMemberService {

    private final LibraryMemberRepository libraryMemberRepository;

    public LibraryMemberService(LibraryMemberRepository libraryMemberRepository) {
        this.libraryMemberRepository = libraryMemberRepository;
    }

    public void save(LibraryMember member) {
        libraryMemberRepository.save(member);
    }

    public LibraryMember findById(Long id) {
        return libraryMemberRepository.findById(id);
    }

    public List<LibraryMember> findAll() {
        return libraryMemberRepository.findAll();
    }

    public void deleteById(Long id) {
        libraryMemberRepository.deleteById(id);
    }
}
