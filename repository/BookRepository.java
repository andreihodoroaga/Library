package repository;

import domain.Book;

import java.util.*;

public class BookRepository {

    private Map<Long, Book> bookMap = new HashMap<>();
    private long nextId = 1L;

    public Book findById(Long id) {
        return bookMap.get(id);
    }

    public List<Book> findAll() {
        return new ArrayList<>(bookMap.values());
    }

    public void save(Book book) {
        book.setId(nextId++);
        bookMap.put(book.getId(), book);
    }

    public void update(Book book) {
        bookMap.put(book.getId(), book);
    }

    public void delete(Long id) {
        bookMap.remove(id);
    }
   
    public List<Book> findByTitle(String title) {
        List<Book> books = new ArrayList<>();
        for (Book book : bookMap.values()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                books.add(book);
            }
        }
        return books;
    }

    public Optional<Book> findFirstByTitle(String title) {
        return bookMap.values().stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst();
    }
}
