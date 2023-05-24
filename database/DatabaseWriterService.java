package database;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseWriterService<T> {
    private static DatabaseWriterService instance;
    private Connection connection;

    private DatabaseWriterService() {
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
    public static DatabaseWriterService getInstance() {
        if (instance == null) {
            instance = new DatabaseWriterService();
        }
        return instance;
    }

    public void write(T object) {
        if (connection == null) {
            System.err.println("Database connection is not available.");
            return;
        }

        String tableName = getTableName(object);
        String sql = generateInsertStatement(object, tableName);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();

            System.out.println("Data written to the database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(T object) {
        if (connection == null) {
            System.err.println("Database connection is not available.");
            return;
        }

        String tableName = getTableName(object);
        String sql = generateUpdateStatement(object, tableName);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();

            System.out.println("Data updated in the database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(T object) {
        if (connection == null) {
            System.err.println("Database connection is not available.");
            return;
        }

        String tableName = getTableName(object);
        String sql = generateDeleteStatement(object, tableName);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();

            System.out.println("Data deleted from the database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getTableName(T object) {
        Class<?> objectClass = object.getClass();
        String className = objectClass.getSimpleName();
        return className.toLowerCase();
    }

    private String generateInsertStatement(T object, String tableName) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO ").append(tableName).append(" (");

        Class<?> objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();

        // Build the column names part of the statement
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            // Exclude static and synthetic fields
            if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                continue;
            }

            String columnName = field.getName();
            if (columnName.equals("id")) {
                continue; // Skip the id column
            }
            sqlBuilder.append(columnName);

            // Append comma for all columns except the last one
            if (i < fields.length - 1) {
                sqlBuilder.append(", ");
            }
        }

        sqlBuilder.append(") VALUES (");

        // Build the parameter placeholders part of the statement
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            // Exclude static and synthetic fields
            if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                continue;
            }

            if (field.getName().equals("id")) {
                continue; // Skip the id column
            }
            sqlBuilder.append(getFieldValue(object, field));

            // Append comma for all placeholders except the last one
            if (i < fields.length - 1) {
                sqlBuilder.append(", ");
            }
        }

        sqlBuilder.append(")");

        return sqlBuilder.toString();
    }

    private String generateUpdateStatement(T object, String tableName) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("UPDATE ").append(tableName).append(" SET ");

        Class<?> objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();

        // Build the update statement
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            // Exclude static and synthetic fields
            if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                continue;
            }

            String columnName = field.getName();

            // Skip the id column
            if (columnName.equals("id")) {
                continue;
            }

            sqlBuilder.append(columnName).append(" = ").append(getFieldValue(object, field));

            // Append comma for all columns except the last one
            if (i < fields.length - 1) {
                sqlBuilder.append(", ");
            }
        }

        sqlBuilder.append(" WHERE id = ").append(getFieldValue(object, getIdField(objectClass)));

        return sqlBuilder.toString();
    }

    private String generateDeleteStatement(T object, String tableName) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("DELETE FROM ").append(tableName);
        sqlBuilder.append(" WHERE id = ").append(getFieldValue(object, getIdField(object.getClass())));
        return sqlBuilder.toString();
    }

    private Field getIdField(Class<?> objectClass) {
        Field[] fields = objectClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("id")) {
                return field;
            }
        }
        throw new IllegalArgumentException("Id field not found in the class: " + objectClass.getName());
    }

    private String getFieldValue(T object, Field field) {
        try {
            // Enable access to private fields if necessary
            field.setAccessible(true);
            Object value = field.get(object);
            return "'" + value + "'";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "NULL";
    }
}
