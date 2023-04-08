package repository;

import java.util.ArrayList;
import java.util.List;

import domain.Author;

public class AuthorRepository {
    private List<Author> authors;
    private Long nextId;

    public AuthorRepository() {
        authors = new ArrayList<>();
        nextId = 1L;
    }

    public Author getById(Long id) {
        for (Author author : authors) {
            if (author.getId().equals(id)) {
                return author;
            }
        }
        return null;
    }

    public List<Author> getAll() {
        return authors;
    }

    public Author add(Author author) {
        author.setId(nextId++);
        authors.add(author);
        return author;
    }

    public void delete(Author author) {
        authors.remove(author);
    }

    public void update(Author author) {
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getId().equals(author.getId())) {
                authors.set(i, author);
                break;
            }
        }
    }
}
