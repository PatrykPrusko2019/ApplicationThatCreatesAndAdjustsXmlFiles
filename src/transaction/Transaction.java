package transaction;

public class Transaction implements Comparable<Transaction> {
    private int accountNumber;
    private double transactionAmount;

    public Transaction() {
        this(0, 0.0);
    }

    public Transaction(int accountNumber, double transactionAmount) {
        this.accountNumber = accountNumber;
        this.transactionAmount = transactionAmount;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public int compareTo(Transaction o) {
        return this.getAccountNumber() - o.getAccountNumber();
    }
}
