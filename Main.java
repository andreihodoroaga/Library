import database.DatabaseReaderService;
import database.DatabaseWriterService;
import domain.*;
import repository.*;
import service.*;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DatabaseReaderService<Author> authorReaderService = DatabaseReaderService.getInstance();
        DatabaseWriterService<Author> authorWriterService = DatabaseWriterService.getInstance();
        AuthorService authorService = new AuthorService(new AuthorRepository(authorReaderService, authorWriterService));
        DatabaseReaderService<Book> bookReaderService = DatabaseReaderService.getInstance();
        DatabaseWriterService<Book> bookWriterService = DatabaseWriterService.getInstance();
        BookService bookService = new BookService(new BookRepository(bookReaderService, bookWriterService));

        DatabaseReaderService<Address> addressReaderService = DatabaseReaderService.getInstance();
        DatabaseWriterService<Address> addressWriterService = DatabaseWriterService.getInstance();
        AddressService addressService = new AddressService(new AddressRepository(addressReaderService, addressWriterService));

        DatabaseReaderService<Genre> genreReaderService = DatabaseReaderService.getInstance();
        DatabaseWriterService<Genre> genreWriterService = DatabaseWriterService.getInstance();
        GenreService genreService = new GenreService(new GenreRepository(genreReaderService, genreWriterService));

        DatabaseReaderService<Publisher> publisherReaderService = DatabaseReaderService.getInstance();
        DatabaseWriterService<Publisher> publisherWriterService = DatabaseWriterService.getInstance();
        PublisherService publisherService = new PublisherService(new PublisherRepository(publisherReaderService, publisherWriterService));

    }
}

