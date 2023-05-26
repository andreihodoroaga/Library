package database;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private final String filename;

    public AuditService(String filename) {
        this.filename = filename;
    }

    public void writeAuditLog(String action) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            LocalDateTime timestamp = LocalDateTime.now();
            String formattedTimestamp = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.println(action + "," + formattedTimestamp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
