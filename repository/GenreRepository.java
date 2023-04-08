package repository;

import java.util.ArrayList;
import java.util.List;

import domain.Genre;

public class GenreRepository {
    private final List<Genre> genres;
    private Long nextId;

    public GenreRepository() {
        genres = new ArrayList<>();
        nextId = 1L;
    }

    public Genre save(Genre genre) {
        genre.setId(nextId++);
        genres.add(genre);
        return genre;
    }

    public Genre findById(Long id) {
        for (Genre genre : genres) {
            if (genre.getId().equals(id)) {
                return genre;
            }
        }
        return null;
    }

    public List<Genre> findAll() {
        return genres;
    }

    public void delete(Genre genre) {
        genres.remove(genre);
    }

    public void update(Genre genreToUpdate) {
        for (Genre genre : genres) {
            if (genre.getId().equals(genreToUpdate.getId())) {
                genre.setName(genreToUpdate.getName());
                genre.setDescription(genreToUpdate.getDescription());
                genre.setPopularityScore(genreToUpdate.getPopularityScore());
                break;
            }
        }
    }
}
