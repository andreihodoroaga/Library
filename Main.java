import database.DatabaseReaderService;
import database.DatabaseWriterService;
import domain.*;
import repository.*;
import service.*;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        DatabaseWriterService<Author> dbWriter = DatabaseWriterService.getInstance();
//        dbWriter.write(author);
        DatabaseReaderService<Author> readerService = DatabaseReaderService.getInstance();
        DatabaseWriterService<Author> writerService = DatabaseWriterService.getInstance();

        AuthorService authorService = new AuthorService(new AuthorRepository(readerService, writerService));
        authorService.deleteAuthor(authorService.getAuthorById(3L));
        List<Author> authors = authorService.getAllAuthors();

        for (Author author : authors) {
            System.out.println(author.getName());
            // ... do something with the author object
        }
    }
}