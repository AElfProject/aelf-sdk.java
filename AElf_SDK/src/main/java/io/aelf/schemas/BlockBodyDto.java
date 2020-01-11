package io.aelf.schemas;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BlockBodyDto {
  @JsonProperty("transactionsCount")
  private long transactionsCount;
  @JsonProperty("transactions")
  private List<String> transactions;

  public long getTransactionsCount() {
        return transactionsCount;
    }
  public void setTransactionsCount(long transactionsCount) {
        this.transactionsCount = transactionsCount;
    }
  public List<String> getTransactions() {
        return transactions;
    }
  public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }
}
