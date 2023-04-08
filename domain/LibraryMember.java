package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryMember extends LibraryPerson {
    private String phoneNumber;
    private final String memberId;
    private final List<BorrowingTransaction> borrowedBooks;

    public LibraryMember(Long id, String memberId, String name, String email, String phoneNumber, Address address) {
        super(id, name, email, address);
        this.memberId = memberId;
        this.phoneNumber = phoneNumber;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public void borrowBook(Book book, LocalDate dueDate) {
        // Check if there are any books left
        if (book.getCount() == 0) {
            throw new IllegalArgumentException("There isn't any book of this kind left");
        }

        // Check if the member has already borrowed the book
        for (BorrowingTransaction transaction : borrowedBooks) {
            if (transaction.getBook().equals(book)) {
                // Book is already borrowed, so just update the transaction date
                transaction.setBorrowDate(LocalDate.now());
                return;
            }
        }

        // If the member hasn't already borrowed the book, create a new transaction
        BorrowingTransaction transaction = new BorrowingTransaction(this, book, LocalDate.now(), dueDate);
        borrowedBooks.add(transaction);
        book.setCount(book.getCount() - 1);
    }

    public void returnBook(Book book) {
        // Find the transaction for the book being returned
        for (BorrowingTransaction transaction : borrowedBooks) {
            if (transaction.getBook().equals(book)) {
                // Update the transaction date and remove it from the borrowed books list
                transaction.setReturnDate(LocalDate.now());
                borrowedBooks.remove(transaction);
                // Increase the count for the book
                book.setCount(book.getCount() + 1);
                return;
            }
        }

        // Book was not found in the borrowed books list
        throw new IllegalArgumentException("Member did not borrow this book");
    }

    public List<BorrowingTransaction> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public String toString() {
        return "LibraryMember{" +
                "memberId='" + memberId + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", address=" + getAddress() +
                ", borrowedBooks=" + borrowedBooks +
                '}';
    }
}
