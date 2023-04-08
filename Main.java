import domain.*;
import repository.AddressRepository;
import repository.BookRepository;
import repository.LibraryMemberRepository;
import repository.PublisherRepository;
import service.AddressService;
import service.BookService;
import service.LibraryMemberService;
import service.PublisherService;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        AddressService addressService = new AddressService(new AddressRepository());
        BookService bookService = new BookService(new BookRepository());
        PublisherService publisherService = new PublisherService(new PublisherRepository());
        LibraryMemberService libraryMemberService = new LibraryMemberService(new LibraryMemberRepository());

        Address baseAddress = new Address(1, "Bulevardul Eroilor", "Bucuresti", "Bucuresti", "093453", 10);

        // Create new publisher
        Publisher bestBooksPublisher = new Publisher("Best books", "Bucuresti");
        publisherService.create(bestBooksPublisher);

        // Create new genre
        Genre sfGenre = new Genre("Science Fiction", "Science fiction (sometimes shortened to sf or sci-fi) is a genre of speculative fiction, " +
                                                                    "which typically deals with imaginative and futuristic concepts such as advanced science and technology," +
                                                                    " space exploration, time travel, parallel universes, and extraterrestrial life.", 9.2);

        // Create two new books
        bookService.save(new Book("Dune", bestBooksPublisher, LocalDate.now(), sfGenre, 2));
        bookService.save(new Book("Lord of the Rings", bestBooksPublisher, LocalDate.now(), sfGenre, 1));

        // Create two library members
        LibraryMember member1 = new LibraryMember(1L, "M001", "Marius Popescu", "mariuspopescu@mail.com", "077777777", baseAddress);
        LibraryMember member2 = new LibraryMember(2L, "M002", "Andrei Marcel", "andreimarcel@mail.com", "077777777", baseAddress);

        // Borrow a book that has enough copies left
        Optional<Book> book = bookService.findFirstByTitle("Dune");
        if (book.isPresent()) {
            Book duneBook = book.get();
            member1.borrowBook(duneBook, LocalDate.now().plus(Period.ofWeeks(1)));
        } else {
            System.out.println("Dune not found");
        }

        // Borrow a book without any copies left
        Optional<Book> book1 = bookService.findFirstByTitle("Lord of the Rings");
        if (book1.isPresent()) {
            Book ltrBook = book1.get();
            member1.borrowBook(ltrBook, LocalDate.now().plus(Period.ofWeeks(1)));

           // Try to have the other member book lord of the rings, it should throw an exception
            member2.borrowBook(ltrBook, LocalDate.now().plus(Period.ofWeeks(1)));
        }
    }
}
