package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book {
    private Long id;
    private String title;
    private List<Long> authorIds;
    private Long publisherId;
    private LocalDate publicationDate;
    private Long genreId;
    private int count;
    private List<String> reviews;

    public Book(String title, List<Long> authorIds, Long publisherId, LocalDate publicationDate, Long genreId, int count) {
        this.title = title;
        this.authorIds = authorIds;
        this.publisherId = publisherId;
        this.publicationDate = publicationDate;
        this.genreId = genreId;
        this.count = count;
    }

    public Book() {
        // default constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Long> authorIds) {
        this.authorIds = authorIds;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void addReview(String review) {
        if (!Objects.equals(review, "")) {
            if (reviews == null) {
                reviews = new ArrayList<>();
            }
            reviews.add(review);
        }
    }
}
