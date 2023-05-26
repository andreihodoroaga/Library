package repository;

import domain.BorrowingTransaction;
import database.DatabaseReaderService;
import database.DatabaseWriterService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BorrowingTransactionRepository {
    private final DatabaseReaderService<BorrowingTransaction> readerService;
    private final DatabaseWriterService<BorrowingTransaction> writerService;

    public BorrowingTransactionRepository(DatabaseReaderService<BorrowingTransaction> readerService,
                                          DatabaseWriterService<BorrowingTransaction> writerService) {
        this.readerService = readerService;
        this.writerService = writerService;
    }

    public void add(BorrowingTransaction transaction) {
        writerService.write(transaction);
    }

    public Optional<BorrowingTransaction> findById(Long id) {
        return Optional.ofNullable(readerService.read(BorrowingTransaction.class, id));
    }

    public List<BorrowingTransaction> findAll() {
        return readerService.readAll(BorrowingTransaction.class);
    }

    public List<BorrowingTransaction> findByMemberId(String memberId) {
        List<BorrowingTransaction> result = new ArrayList<>();
        List<BorrowingTransaction> allTransactions = findAll();
        for (BorrowingTransaction transaction : allTransactions) {
            if (transaction.getMember().getMemberId().equals(memberId)) {
                result.add(transaction);
            }
        }
        return result;
    }

    public List<BorrowingTransaction> findOverdueTransactions(LocalDate currentDate) {
        List<BorrowingTransaction> result = new ArrayList<>();
        List<BorrowingTransaction> allTransactions = findAll();
        for (BorrowingTransaction transaction : allTransactions) {
            if (transaction.getDueDate().isBefore(currentDate)) {
                result.add(transaction);
            }
        }
        return result;
    }
}
