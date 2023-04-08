package service;

import java.util.List;

import domain.Publisher;
import repository.PublisherRepository;

public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher create(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher findById(Long id) {
        return publisherRepository.findById(id);
    }

    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    public void delete(Publisher publisher) {
        publisherRepository.delete(publisher);
    }

    public void update(Publisher publisher) {
        publisherRepository.update(publisher);
    }
}
