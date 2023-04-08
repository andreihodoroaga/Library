package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private Long id;
    private String title;
    private List<Author> authors = new ArrayList<>();
    private Publisher publisher;
    private LocalDate publicationDate;
    private Genre genre;

    private int count;

    public Book(String title, Publisher publisher, LocalDate publicationDate, Genre genre, int count) {
        this.title = title;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.genre = genre;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCount() { return count; }

    public void setCount(int count) { this.count = count; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
