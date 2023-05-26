import database.DatabaseReaderService;
import database.DatabaseWriterService;
import domain.*;
import repository.BookRepository;
import service.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManager {
    BookService bookService;
    AuthorService authorService;
    PublisherService publisherService;
    GenreService genreService;
    LibraryMemberService libraryMemberService;
    AddressService addressService;
    Scanner scanner = new Scanner(System.in);

    public LibraryManager(BookService bookService, AuthorService authorService, PublisherService publisherService, GenreService genreService, LibraryMemberService libraryMemberService, AddressService addressService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.genreService = genreService;
        this.libraryMemberService = libraryMemberService;
        this.addressService = addressService;
    }

    public void start() {
        while (true) {
            System.out.println("Library App");
            System.out.println("1. Add new book");
            System.out.println("2. Search book");
            System.out.println("3. Remove book");
            System.out.println("4. Add member");
            System.out.println("5. Borrow book for member");
            System.out.println("6. Return book for member");
            System.out.println("7. Favorite book for member");
            System.out.println("8. Don't click this yet");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addNewBook();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    removeBook();
                    break;
                case 4:
                    addMember();
                    break;
                case 5:
                    borrowBook();
                    break;
                case 6:
                    returnBook();
                    break;
                case 7:
                    addFavoriteBook();
                    break;
                case 8:
                    break;
                case 9:
                    System.out.println("Exiting the application...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println(); // Add a newline for readability
        }
    }

    private void addNewBook() {
        System.out.println("Adding new book...");

        // Get book details from the user
        System.out.print("Enter the title: ");
        String title = scanner.nextLine();
        List<Long> authorIDs = selectAuthorIDs();
        Long publisher = selectPublisherID();
        Long genre = selectGenreID();
        System.out.print("Enter the number of copies for this book: ");
        int count = scanner.nextInt();

        Book newBook = new Book(title, authorIDs, publisher, LocalDate.now(), genre, count);
        bookService.save(newBook);
        System.out.println("New book added: " + newBook.getTitle());
    }

    private void searchBook() {
        System.out.println("Searching book...");

        // Get the title from the user
        System.out.print("Enter the title to search: ");
        String title = scanner.nextLine();

        // Use the BookRepository to search for books with the given title
        List<Book> books = bookService.findByTitle(title);

        // Display the search results
        if (books.isEmpty()) {
            System.out.println("No books found with the title: " + title);
        } else {
            System.out.println("Books found with the title: " + title);
            for (Book book : books) {
                System.out.println("Title: " + book.getTitle());
                List<Long> authorIds = book.getAuthorIds();
                for (int i = 0; i < authorIds.size(); i++) {
                    Long authorId = authorIds.get(i);
                    Author author = authorService.getAuthorById(authorId);
                    if (author != null) {
                        System.out.print(author.getName());
                        if (i < authorIds.size() - 1) {
                            System.out.print(", ");
                        }
                    }
                }
                System.out.println();
                System.out.println("Number of available books: " + book.getCount());
                System.out.println();
            }
        }
    }

    private void removeBook() {
        System.out.println("Removing book...");
        System.out.print("Enter the title of the book to remove: ");
        String title = scanner.nextLine();

        List<Book> books = bookService.findByTitle(title);

        if (books.isEmpty()) {
            System.out.println("No books found with the title: " + title);
        } else {
            System.out.println("Books found with the title: " + title);
            for (int i = 0; i < books.size(); i++) {
                System.out.println((i + 1) + ". " + books.get(i).getTitle());
            }

            System.out.print("Enter the number of the book to remove: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < 1 || choice > books.size()) {
                System.out.println("Invalid book choice.");
            } else {
                Book bookToRemove = books.get(choice - 1);
                bookService.delete(bookToRemove);
                System.out.println("Book removed: " + bookToRemove.getTitle());
            }
        }
    }

    private void addMember() {
        System.out.println("Adding new member...");

        // Get member details from the user
        System.out.print("Enter the member's library ID: ");
        String memberId = scanner.nextLine();
        System.out.print("Enter the member name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the member email: ");
        String email = scanner.nextLine();
        Long addressId = getAddressIdFromUser();

        LibraryMember newMember = new LibraryMember(memberId, name, email, addressId);
        libraryMemberService.save(newMember);
        System.out.println("New member added: " + newMember.getName());
    }

    private void borrowBook() {
        System.out.println("Borrowing book...");

        // Select a member
        LibraryMember member = selectMember();
        if (member == null) {
            System.out.println("No members found.");
            return;
        }

        // Select a book
        Book book = selectBook();
        if (book == null) {
            System.out.println("No books found.");
            return;
        }

        // Add the book to the member's borrowed books
        member.borrowBook(book, LocalDate.now());

        // Update the member in the database
        libraryMemberService.update(member);
        // Update the book count in the database
        bookService.update(book);

        System.out.println("Book borrowed successfully!");
    }

    private void returnBook() {
        System.out.println("Returning book...");

        // Select a member
        LibraryMember member = selectMember();
        if (member == null) {
            System.out.println("No members found.");
            return;
        }

        // Select a book from the member's borrowed books
        Book book = selectBookFromMember(member);
        if (book == null) {
            System.out.println("No books found.");
            return;
        }

        // Return the book from the member's borrowed books
        member.returnBook(book);

        // Update the member in the database
        libraryMemberService.update(member);
        // Update the book count in the database
        bookService.update(book);

        System.out.println("Book returned successfully!");
    }

    private void addFavoriteBook() {
        System.out.println("Adding a favorite book...");

        // Select a member
        LibraryMember member = selectMember();
        if (member == null) {
            System.out.println("No members found.");
            return;
        }

        // List the books of the member
        List<Long> memberBooksIds = member.getBorrowedBooksIds();
        if (memberBooksIds.isEmpty()) {
            System.out.println("No books found for this member.");
            return;
        }

        System.out.println("Select a book to add to favorites:");
        for (int i = 0; i < memberBooksIds.size(); i++) {
            System.out.println((i + 1) + ". " + bookService.findById(memberBooksIds.get(i)).getTitle());
        }

        System.out.print("Enter the number of the book: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        Book selectedBook = bookService.findById(memberBooksIds.get(choice - 1));
        // Add the selected book to the member's favorite books
        member.addFavoriteBook(selectedBook);

        // Update the member in the database
        libraryMemberService.update(member);

        System.out.println("Book added to favorites successfully!");
    }


    private Book selectBookFromMember(LibraryMember member) {
        List<Long> borrowedBooksIds = member.getBorrowedBooksIds();
        if (borrowedBooksIds.isEmpty()) {
            System.out.println("No books borrowed by this member.");
            return null;
        }

        System.out.println("Select a book:");
        for (int i = 0; i < borrowedBooksIds.size(); i++) {
            System.out.println((i + 1) + ". " + bookService.findById(borrowedBooksIds.get(i)).getTitle());
        }

        System.out.print("Enter the book number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice < 1 || choice > borrowedBooksIds.size()) {
            System.out.println("Invalid selection.");
            return null;
        }

        return bookService.findById(borrowedBooksIds.get(choice - 1));
    }

    private LibraryMember selectMember() {
        System.out.println("Select a member:");
        List<LibraryMember> members = libraryMemberService.findAll();

        for (int i = 0; i < members.size(); i++) {
            LibraryMember member = members.get(i);
            System.out.println((i + 1) + ". " + member.getName());
        }

        System.out.print("Enter the member number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > members.size()) {
            System.out.println("Invalid member choice.");
            return null;
        } else {
            return members.get(choice - 1);
        }
    }

    private Book selectBook() {
        System.out.println("Select a book:");
        List<Book> books = bookService.findAll();

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println((i + 1) + ". " + book.getTitle());
        }

        System.out.print("Enter the book number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > books.size()) {
            System.out.println("Invalid book choice.");
            return null;
        } else {
            return books.get(choice - 1);
        }
    }

    private List<Long> selectAuthorIDs() {
        System.out.println("Select author(s):");
        List<Author> authors = this.authorService.getAllAuthors();
        for (int i = 0; i < authors.size(); i++) {
            System.out.println((i + 1) + ". " + authors.get(i).getName());
        }
        System.out.print("Enter the author number(s) separated by commas: ");
        String input = scanner.nextLine();
        String[] choices = input.split(",");
        List<Long> selectedAuthors = new ArrayList<>();

        for (String choice : choices) {
            int index = Integer.parseInt(choice.trim()) - 1;
            if (index >= 0 && index < authors.size()) {
                selectedAuthors.add(authors.get(index).getId());
            } else {
                System.out.println("Invalid author choice: " + choice);
            }
        }

        return selectedAuthors;
    }

    private Long selectPublisherID() {
        System.out.println("Select a publisher:");
        List<Publisher> publishers = this.publisherService.findAll();
        for (int i = 0; i < publishers.size(); i++) {
            System.out.println((i + 1) + ". " + publishers.get(i).getName());
        }
        System.out.print("Enter the publisher number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice < 1 || choice > publishers.size()) {
            System.out.println("Invalid publisher choice.");
            return null;
        } else {
            return publishers.get(choice - 1).getId();
        }
    }

    private Long selectGenreID() {
        System.out.println("Select a genre:");
        List<Genre> genres = this.genreService.findAllGenres();
        for (int i = 0; i < genres.size(); i++) {
            System.out.println((i + 1) + ". " + genres.get(i).getName());
        }
        System.out.print("Enter the genre number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice < 1 || choice > genres.size()) {
            System.out.println("Invalid genre choice.");
            return null;
        } else {
            return genres.get(choice - 1).getId();
        }
    }

    private Long getAddressIdFromUser() {
        System.out.println("Select an address:");

        List<Address> addresses = addressService.getAllAddresses();
        for (int i = 0; i < addresses.size(); i++) {
            Address address = addresses.get(i);
            System.out.println((i + 1) + ". " + address);
        }

        System.out.print("Enter the address number: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (choice < 1 || choice > addresses.size()) {
            System.out.println("Invalid address choice.");
            return null;
        } else {
            return addresses.get(choice - 1).getId();
        }
    }

}
