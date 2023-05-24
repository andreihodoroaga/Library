package database;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseReaderService<T> {
    private static DatabaseReaderService instance;
    private Connection connection;

    private DatabaseReaderService() {
        // Initialize the database connection
        String url = "jdbc:postgresql://localhost:5432/library";
        String username = "postgres";
        String password = "1234";

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get the singleton instance
    public static DatabaseReaderService getInstance() {
        if (instance == null) {
            instance = new DatabaseReaderService();
        }
        return instance;
    }

    public List<T> readAll(Class<T> objectClass) {
        List<T> resultList = new ArrayList<>();

        if (connection == null) {
            System.err.println("Database connection is not available.");
            return resultList;
        }

        String tableName = getTableName(objectClass);
        String sql = generateSelectStatement(tableName);

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                T object = createObjectFromResultSet(resultSet, objectClass);
                resultList.add(object);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public T read(Class<T> objectClass, Long id) {
        if (connection == null) {
            System.err.println("Database connection is not available.");
            return null;
        }

        String tableName = getTableName(objectClass);
        String sql = generateSelectStatement(tableName) + " WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                T object = createObjectFromResultSet(resultSet, objectClass);
                return object;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getTableName(Class<T> objectClass) {
        String className = objectClass.getSimpleName();
        return className.toLowerCase();
    }

    private String generateSelectStatement(String tableName) {
        return "SELECT * FROM " + tableName;
    }

    private T createObjectFromResultSet(ResultSet resultSet, Class<T> objectClass) throws SQLException {
        try {
            T object = objectClass.getDeclaredConstructor().newInstance();
            Field[] fields = objectClass.getDeclaredFields();

            for (Field field : fields) {
                // Exclude static and synthetic fields
                if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                    continue;
                }

                String columnName = field.getName();

                if (field.getType().equals(LocalDate.class)) {
                    // Handle conversion from java.sql.Date to LocalDate
                    java.sql.Date sqlDate = resultSet.getDate(columnName);
                    LocalDate localDate = sqlDate.toLocalDate();
                    field.setAccessible(true);
                    field.set(object, localDate);
                } else {
                    // Handle other fields normally
                    Object value = resultSet.getObject(columnName);
                    field.setAccessible(true);
                    field.set(object, value);
                }
            }

            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
