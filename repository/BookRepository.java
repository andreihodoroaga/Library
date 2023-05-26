package repository;

import database.DatabaseReaderService;
import database.DatabaseService;
import database.DatabaseWriterService;
import domain.Author;
import domain.Book;
import domain.Genre;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BookRepository {
    private final DatabaseReaderService<Book> readerService;
    private final DatabaseWriterService<Book> writerService;
    private final DatabaseService databaseService;

    public BookRepository(DatabaseReaderService<Book> readerService, DatabaseWriterService<Book> writerService, DatabaseService databaseService) {
        this.readerService = readerService;
        this.writerService = writerService;
        this.databaseService = databaseService;
    }

    public Book findById(Long id) {
        return readerService.read(Book.class, id);
    }

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book";

        try (Connection connection = databaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                Array authorIdsArray = resultSet.getArray("authorids");
                List<Long> authorIds = Arrays.asList((Long[]) authorIdsArray.getArray());
                LocalDate publicationDate = resultSet.getDate("publicationdate").toLocalDate();
                Long genreId = resultSet.getLong("genreid");
                int count = resultSet.getInt("count");
                Long publisherId = resultSet.getLong("publisherid");
                Array reviewsArray = resultSet.getArray("reviews");
                List<String> reviews = null;

                if (reviewsArray != null) {
                    reviews = Arrays.asList((String[]) reviewsArray.getArray());
                }

                Book book = new Book(title, authorIds, publisherId, publicationDate, genreId, count);
                book.setId(id);
                book.setPublisherId(publisherId);
                book.setReviews(reviews);

                books.add(book);
            }
        } catch (SQLException e) {
            // Handle any exceptions
            e.printStackTrace();
        }

        return books;
    }

    public void save(Book book) {
        writerService.write(book);
    }

    public void update(Book book) {
        String query = "UPDATE book SET title = ?, authorids = ?, publicationdate = ?, genreid = ?, count = ?, reviews = CAST(? AS text[]) WHERE id = ?";

        try (Connection connection = databaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the values for the update query
            statement.setString(1, book.getTitle());
            statement.setArray(2, connection.createArrayOf("bigint", book.getAuthorIds().toArray()));
            statement.setDate(3, java.sql.Date.valueOf(book.getPublicationDate()));
            statement.setLong(4, book.getGenreId());
            statement.setInt(5, book.getCount());

            // Convert the reviews list to a suitable array format
            Array reviewsArray = connection.createArrayOf("text", book.getReviews().toArray());
            statement.setArray(6, reviewsArray);

            statement.setLong(7, book.getId());

            // Execute the update query
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle any exceptions
            e.printStackTrace();
        }
    }


    public void delete(Book book) {
        writerService.delete(book);
    }

    public List<Book> findByTitle(String title) {
        List<Book> books = new ArrayList<>();
        List<Book> allBooks = findAll();

        for (Book book : allBooks) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                books.add(book);
            }
        }

        return books;
    }

    public Optional<Book> findFirstByTitle(String title) {
        List<Book> books = findByTitle(title);
        return books.isEmpty() ? Optional.empty() : Optional.of(books.get(0));
    }

    public List<Book> filterByAuthor(Author author) {
        List<Book> books = new ArrayList<>();
        List<Book> allBooks = findAll();

        for (Book book : allBooks) {
            if (book.getAuthorIds().contains(author.getId())) {
                books.add(book);
            }
        }

        return books;
    }

    public List<Book> filterByGenre(Genre genre) {
        List<Book> books = new ArrayList<>();
        List<Book> allBooks = findAll();

        for (Book book : allBooks) {
            if (book.getGenreId().equals(genre.getId())) {
                books.add(book);
            }
        }

        return books;
    }
}