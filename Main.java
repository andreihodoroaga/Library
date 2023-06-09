import database.DatabaseReaderService;
import database.DatabaseService;
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
        BookService bookService = new BookService(new BookRepository(bookReaderService, bookWriterService, new DatabaseService()));

        DatabaseReaderService<Address> addressReaderService = DatabaseReaderService.getInstance();
        DatabaseWriterService<Address> addressWriterService = DatabaseWriterService.getInstance();
        AddressService addressService = new AddressService(new AddressRepository(addressReaderService, addressWriterService));

        DatabaseReaderService<Genre> genreReaderService = DatabaseReaderService.getInstance();
        DatabaseWriterService<Genre> genreWriterService = DatabaseWriterService.getInstance();
        GenreService genreService = new GenreService(new GenreRepository(genreReaderService, genreWriterService));

        DatabaseReaderService<Publisher> publisherReaderService = DatabaseReaderService.getInstance();
        DatabaseWriterService<Publisher> publisherWriterService = DatabaseWriterService.getInstance();
        PublisherService publisherService = new PublisherService(new PublisherRepository(publisherReaderService, publisherWriterService));

        DatabaseReaderService<LibraryMember> libraryMemberReaderService = DatabaseReaderService.getInstance();
        DatabaseWriterService<LibraryMember> libraryMemberWriterService = DatabaseWriterService.getInstance();
        LibraryMemberService libraryMemberService = new LibraryMemberService(new LibraryMemberRepository(libraryMemberReaderService, libraryMemberWriterService, new DatabaseService()));

        LibraryManager libraryManager = new LibraryManager(bookService, authorService, publisherService, genreService, libraryMemberService, addressService);
        libraryManager.start();
    }
}

