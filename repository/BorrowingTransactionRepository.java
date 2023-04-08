package repository;
import domain.BorrowingTransaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BorrowingTransactionRepository {
    private final List<BorrowingTransaction> transactions = new ArrayList<>();

    public void add(BorrowingTransaction transaction) {
        transactions.add(transaction);
    }

    public Optional<BorrowingTransaction> findById(Long id) {
        return transactions.stream()
                .filter(t -> t.getBook().getId().equals(id))
                .findFirst();
    }

    public List<BorrowingTransaction> findAll() {
        return new ArrayList<>(transactions);
    }

    public List<BorrowingTransaction> findByMemberId(String memberId) {
        List<BorrowingTransaction> result = new ArrayList<>();
        for (BorrowingTransaction transaction : transactions) {
            if (transaction.getMember().getMemberId().equals(memberId)) {
                result.add(transaction);
            }
        }
        return result;
    }

    public List<BorrowingTransaction> findOverdueTransactions(LocalDate currentDate) {
        List<BorrowingTransaction> result = new ArrayList<>();
        for (BorrowingTransaction transaction : transactions) {
            if (transaction.getDueDate().isBefore(currentDate)) {
                result.add(transaction);
            }
        }
        return result;
    }
}
