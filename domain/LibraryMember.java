package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LibraryMember extends LibraryPerson {
    private final String memberId;
    private List<Long> borrowedBooksIds;
    private Set<Long> favoriteBooksIds;

    public LibraryMember() {
        super("", "", null);
        this.memberId = "";
        this.borrowedBooksIds = new ArrayList<>();
        this.favoriteBooksIds = new HashSet<>();
    }

    public LibraryMember(String memberId, String name, String email, Long addressId) {
        super(name, email, addressId);
        this.memberId = memberId;
        this.borrowedBooksIds = new ArrayList<>();
        this.favoriteBooksIds = new HashSet<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public Long getId() {
        return id;
    }

    public void borrowBook(Book book, LocalDate dueDate) {
        // Check if there are any books left
        if (book.getCount() == 0) {
            throw new IllegalArgumentException("There isn't any book of this kind left");
        }

        // Check if the member has already borrowed the book
        for (Long bookId : borrowedBooksIds) {
            if (bookId.equals(book.getId())) {
                // Book is already borrowed, so just update the transaction date

                return;
            }
        }

        // If the member hasn't already borrowed the book, create a new transaction
        // TODO: Create a new BorrowingTransaction with book.getId() and LocalDate.now()
        borrowedBooksIds = new ArrayList<>(borrowedBooksIds);
        borrowedBooksIds.add(book.getId());
        book.setCount(book.getCount() - 1);
    }

    public void returnBook(Book book) {
        // Check if the member has borrowed the book
        if (borrowedBooksIds.contains(book.getId())) {
            borrowedBooksIds = new ArrayList<>(borrowedBooksIds); // Create a new ArrayList
            borrowedBooksIds.remove(book.getId()); // Remove the book ID from the list
            book.setCount(book.getCount() + 1); // Increase the book count
        }
    }

//    public void borrowBook(Book book, LocalDate dueDate) {
//        // Check if there are any books left
//        if (book.getCount() == 0) {
//            throw new IllegalArgumentException("There isn't any book of this kind left");
//        }
//
//        // Check if the member has already borrowed the book
//        for (BorrowingTransaction transaction : borrowedBooks) {
//            if (transaction.getBook().equals(book)) {
//                // Book is already borrowed, so just update the transaction date
//                transaction.setBorrowDate(LocalDate.now());
//                return;
//            }
//        }
//
//        // If the member hasn't already borrowed the book, create a new transaction
//        BorrowingTransaction transaction = new BorrowingTransaction(this, book, LocalDate.now(), dueDate);
//        borrowedBooks.add(transaction);
//        book.setCount(book.getCount() - 1);
//    }
//
//    public void returnBook(Book book) {
//        returnBook(book, "");
//    }
//
//    public void returnBook(Book book, String review) {
//        // Find the transaction for the book being returned
//        for (BorrowingTransaction transaction : borrowedBooks) {
//            if (transaction.getBook().equals(book)) {
//                // Update the transaction date and remove it from the borrowed books list
//                transaction.setReturnDate(LocalDate.now());
//                borrowedBooks.remove(transaction);
//                // Increase the count for the book
//                book.setCount(book.getCount() + 1);
//                book.addReview(review);
//                return;
//            }
//        }
//
//        // Book was not found in the borrowed books list
//        throw new IllegalArgumentException("Member did not borrow this book");
//    }
//
//    public List<BorrowingTransaction> getBorrowedBooks() {
//        return borrowedBooks;
//    }

    @Override
    public String toString() {
        return "LibraryMember{" +
                "memberId='" + memberId + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", address=" + getAddressId() +
                ", borrowedBooks=" + borrowedBooksIds +
                '}';
    }

    public List<Long> getBorrowedBooksIds() {
        return this.borrowedBooksIds;
    }

    public void setBorrowedBooksIds(List<Long> ids) {
        this.borrowedBooksIds = ids;
    }

    public Set<Long> getFavoriteBooksIds() {
        return favoriteBooksIds;
    }

    public void setFavoriteBooksIds(Set<Long> favoriteBooksIds) {
        this.favoriteBooksIds = favoriteBooksIds;
    }

    public void addFavoriteBook(Book book) {
        favoriteBooksIds.add(book.getId());
    }
}
