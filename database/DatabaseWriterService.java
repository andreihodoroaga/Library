package database;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.List;

public class DatabaseWriterService<T> {
    private static DatabaseWriterService instance;
    private AuditService auditService;
    private DatabaseService databaseService;

    private DatabaseWriterService(AuditService auditService, DatabaseService databaseService) {
        this.auditService = auditService;
        this.databaseService = databaseService;
    }

    // Method to get the singleton instance
    public static DatabaseWriterService getInstance() {
        if (instance == null) {
            instance = new DatabaseWriterService(new AuditService("audit.csv"), new DatabaseService());
        }
        return instance;
    }

    public void write(T object) {
        if (databaseService.getConnection() == null) {
            System.err.println("Database databaseService.getConnection() is not available.");
            return;
        }

        String tableName = getTableName(object);
        String sql = generateInsertStatement(object, tableName);

        try (PreparedStatement statement = databaseService.getConnection().prepareStatement(sql)) {
            setStatementParameters(statement, object);
            statement.executeUpdate();

            auditService.writeAuditLog("write to " + tableName);
            System.out.println("Data written to the database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setStatementParameters(PreparedStatement statement, T object) throws SQLException {
        Class<?> objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();

        int parameterIndex = 1;
        for (Field field : fields) {
            // Exclude static and synthetic fields
            if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                continue;
            }

            if (field.getName().equals("id")) {
                continue; // Skip the id field
            }

            field.setAccessible(true); // Enable access to private fields if necessary

            try {
                Object value = field.get(object);

                if (value instanceof List) {
                    List<?> listValue = (List<?>) value;

                    if (field.getName().equals("author_ids")) {
                        Long[] arrayValue = listValue.toArray(new Long[0]);

                        // Manually create an array of java.sql.Array objects
                        Array[] authorIdArrays = new Array[arrayValue.length];
                        for (int i = 0; i < arrayValue.length; i++) {
                            authorIdArrays[i] = databaseService.getConnection().createArrayOf("bigint", new Object[]{arrayValue[i]});
                        }

                        // Set the array of java.sql.Array objects as the parameter value
                        statement.setArray(parameterIndex, databaseService.getConnection().createArrayOf("bigint", authorIdArrays));
                    } else {
                        // Convert the ArrayList to an Array and set it as the parameter value
                        Array array = databaseService.getConnection().createArrayOf("varchar", listValue.toArray());
                        statement.setArray(parameterIndex, array);
                    }
                } else {
                    statement.setObject(parameterIndex, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            parameterIndex++;
        }
    }

    public void update(T object) {
        if (databaseService.getConnection() == null) {
            System.err.println("Database databaseService.getConnection() is not available.");
            return;
        }

        String tableName = getTableName(object);
        String sql = generateUpdateStatement(object, tableName);

        try (PreparedStatement statement = databaseService.getConnection().prepareStatement(sql)) {
            statement.executeUpdate();

            auditService.writeAuditLog("update to " + tableName);
            System.out.println("Data updated in the database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(T object) {
        if (databaseService.getConnection() == null) {
            System.err.println("Database databaseService.getConnection() is not available.");
            return;
        }

        String tableName = getTableName(object);
        String sql = generateDeleteStatement(object, tableName);

        try (PreparedStatement statement = databaseService.getConnection().prepareStatement(sql)) {
            statement.executeUpdate();

            auditService.writeAuditLog("delete from " + tableName);
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

            // Check if the field is an ArrayList
            if (field.getType().equals(List.class)) {
                String columnName = field.getName();

                // Handle the "author_ids" field separately
                if (columnName.equals("authorIds")) {
                    sqlBuilder.append("CAST(? AS bigint[])");
                } else {
                    sqlBuilder.append("CAST(? AS varchar[])");
                }
            } else {
                sqlBuilder.append("?"); // Placeholder for non-ArrayList fields
            }

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
