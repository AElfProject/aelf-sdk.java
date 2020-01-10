package io.aelf.schemas;

import java.util.List;

public class BlockBodyDto {
    private long TransactionsCount;
    private List<String> Transactions;
    public long getTransactionsCount() {
        return TransactionsCount;
    }
    public void setTransactionsCount(long transactionsCount) {
        this.TransactionsCount = transactionsCount;
    }
    public List<String> getTransactions() {
        return Transactions;
    }
    public void setTransactions(List<String> transactions) {
        this.Transactions = transactions;
    }
}
