package service;

import domain.Book;
import domain.BorrowingTransaction;
import domain.LibraryMember;
import repository.BorrowingTransactionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BorrowingTransactionService {
    private final BorrowingTransactionRepository repository;

    public BorrowingTransactionService(BorrowingTransactionRepository repository) {
        this.repository = repository;
    }

    public void borrowBook(Book book, LibraryMember member, LocalDate borrowDate, LocalDate dueDate) {
        BorrowingTransaction transaction = new BorrowingTransaction(member, book, borrowDate, dueDate);
        repository.add(transaction);
    }

    public void returnBook(Book book, LibraryMember member, LocalDate returnDate) {
        Optional<BorrowingTransaction> transaction = repository.findById(book.getId());
        if (transaction.isPresent()) {
            transaction.get().setReturnDate(returnDate);
        }
    }

    public List<BorrowingTransaction> getAllTransactions() {
        return repository.findAll();
    }

    public List<BorrowingTransaction> getTransactionsForMember(LibraryMember member) {
        return repository.findByMemberId(member.getMemberId());
    }

    public List<BorrowingTransaction> getOverdueTransactions() {
        return repository.findOverdueTransactions(LocalDate.now());
    }
}
