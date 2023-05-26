package repository;

import database.DatabaseReaderService;
import database.DatabaseWriterService;
import domain.Author;
import domain.Book;
import domain.Genre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository {
    private final DatabaseReaderService<Book> readerService;
    private final DatabaseWriterService<Book> writerService;

    public BookRepository(DatabaseReaderService<Book> readerService, DatabaseWriterService<Book> writerService) {
        this.readerService = readerService;
        this.writerService = writerService;
    }

    public Book findById(Long id) {
        return readerService.read(Book.class, id);
    }

    public List<Book> findAll() {
        return readerService.readAll(Book.class);
    }

    public void save(Book book) {
        writerService.write(book);
    }

    public Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:postgresql://localhost:5432/library";
        String DB_USER = "postgres";
        String DB_PASSWORD = "1234";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Failed to load PostgreSQL JDBC driver");
        }

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void update(Book book) {
        String query = "UPDATE book SET title = ?, authorids = ?, publicationdate = ?, genreid = ?, count = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the values for the update query
            statement.setString(1, book.getTitle());
            statement.setArray(2, connection.createArrayOf("bigint", book.getAuthorIds().toArray()));
            statement.setDate(3, java.sql.Date.valueOf(book.getPublicationDate()));
            statement.setLong(4, book.getGenreId());
            statement.setInt(5, book.getCount());
            statement.setLong(6, book.getId());

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