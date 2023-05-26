package repository;

import domain.LibraryMember;
import database.DatabaseReaderService;
import database.DatabaseWriterService;

import java.sql.*;
import java.util.*;

public class LibraryMemberRepository {
    private final DatabaseReaderService<LibraryMember> readerService;
    private final DatabaseWriterService<LibraryMember> writerService;

    public LibraryMemberRepository(DatabaseReaderService<LibraryMember> readerService,
                                   DatabaseWriterService<LibraryMember> writerService) {
        this.readerService = readerService;
        this.writerService = writerService;
    }

    public void save(LibraryMember libraryMember) {
        // Exclude the borrowedBooks property from the database insert query
        String query = "INSERT INTO librarymember (name, email, addressid, memberid) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the values for the insert query
            statement.setString(1, libraryMember.getName());
            statement.setString(2, libraryMember.getEmail());
            statement.setLong(3, libraryMember.getAddressId());
            statement.setString(4, libraryMember.getMemberId());

            // Execute the insert query
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle any exceptions
            e.printStackTrace();
        }
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

    public LibraryMember findById(Long id) {
        return readerService.read(LibraryMember.class, id);
    }

    public List<LibraryMember> findAll() {
        List<LibraryMember> libraryMembers = new ArrayList<>();
        String query = "SELECT id, name, email, addressid, memberid, borrowedBooksIds, favoriteBooksIds FROM librarymember";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Long addressId = resultSet.getLong("addressid");
                String memberId = resultSet.getString("memberid");

                Array borrowedBooksArray = resultSet.getArray("borrowedBooksIds");
                List<Long> borrowedBooksList = new ArrayList<>();
                if (borrowedBooksArray != null) {
                    Long[] borrowedBooksIds = (Long[]) borrowedBooksArray.getArray();
                    borrowedBooksList = Arrays.asList(borrowedBooksIds);
                }

                Array favoriteBooksArray = resultSet.getArray("favoriteBooksIds");
                Set<Long> favoriteBooksSet = new HashSet<>();
                if (favoriteBooksArray != null) {
                    Long[] favoriteBooksIds = (Long[]) favoriteBooksArray.getArray();
                    favoriteBooksSet = new HashSet<>(Arrays.asList(favoriteBooksIds));
                }

                LibraryMember libraryMember = new LibraryMember(memberId, name, email, addressId);
                libraryMember.setId(id);
                libraryMember.setBorrowedBooksIds(borrowedBooksList);
                libraryMember.setFavoriteBooksIds(favoriteBooksSet);

                libraryMembers.add(libraryMember);
            }
        } catch (SQLException e) {
            // Handle any exceptions
            e.printStackTrace();
        }

        return libraryMembers;
    }


    public void delete(LibraryMember libraryMember) {
        writerService.delete(libraryMember);
    }

    public void update(LibraryMember libraryMember) {
        String query = "UPDATE librarymember SET name = ?, email = ?, addressid = ?, memberid = ?, borrowedBooksIds = CAST(? AS bigint[]), favoriteBooksIds = CAST(? AS bigint[]) WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set the values for the update query
            statement.setString(1, libraryMember.getName());
            statement.setString(2, libraryMember.getEmail());
            statement.setLong(3, libraryMember.getAddressId());
            statement.setString(4, libraryMember.getMemberId());

            // Convert the borrowedBooksIds list to a suitable array format
            Array borrowedBooksIdsArray = connection.createArrayOf("bigint", libraryMember.getBorrowedBooksIds().toArray());
            statement.setArray(5, borrowedBooksIdsArray);

            // Convert the favoriteBooksIds set to a suitable array format
            Array favoriteBooksIdsArray = connection.createArrayOf("bigint", libraryMember.getFavoriteBooksIds().toArray());
            statement.setArray(6, favoriteBooksIdsArray);

            statement.setLong(7, libraryMember.getId());

            // Execute the update query
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle any exceptions
            e.printStackTrace();
        }
    }

    // Helper method to convert a list of Long values to a string representation
    private String convertListToString(List<Long> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(","); // Use a comma as a delimiter between values
            }
        }
        return sb.toString();
    }

}
