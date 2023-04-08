package repository;

import java.util.ArrayList;
import java.util.List;

import domain.Publisher;

public class PublisherRepository {
    private final List<Publisher> publishers;
    private Long nextId;

    public PublisherRepository() {
        publishers = new ArrayList<>();
        nextId = 1L;
    }

    public Publisher save(Publisher publisher) {
        publisher.setId(nextId++);
        publishers.add(publisher);
        return publisher;
    }

    public Publisher findById(Long id) {
        for (Publisher publisher : publishers) {
            if (publisher.getId().equals(id)) {
                return publisher;
            }
        }
        return null;
    }

    public List<Publisher> findAll() {
        return publishers;
    }

    public void delete(Publisher publisher) {
        publishers.remove(publisher);
    }

    public void update(Publisher publisher) {
        for (int i = 0; i < publishers.size(); i++) {
            Publisher p = publishers.get(i);
            if (p.getId().equals(publisher.getId())) {
                publishers.set(i, publisher);
                return;
            }
        }
    }
}
