package service;

import java.util.List;

import domain.Genre;
import repository.GenreRepository;

public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre createGenre(String name, String description, double popularityScore) {
        Genre genre = new Genre(name, description, popularityScore);
        return genreRepository.save(genre);
    }

    public Genre findGenreById(Long id) {
        return genreRepository.findById(id);
    }

    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    public void deleteGenre(Genre genre) {
        genreRepository.delete(genre);
    }

    public void updateGenre(Genre genreToUpdate) {
        genreRepository.update(genreToUpdate);
    }
}
