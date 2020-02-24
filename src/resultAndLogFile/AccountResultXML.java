package resultAndLogFile;

import account.Account;

public class AccountResultXML extends Account {

    private final String customer = "customer of bank";
    private String transaction;
    private int numberOfCustomer;

    public AccountResultXML() {
        this(0, 0, "----", "----", 0.00, "----");

    }


    public AccountResultXML(int numberOfCustomer ,int accountNumber, String firstName, String lastName, double balance,
                            String transaction) {
        super(accountNumber, firstName, lastName, balance);
        this.numberOfCustomer = numberOfCustomer;
        this.transaction = transaction;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getCustomer() {
        return customer;
    }

    public int getNumberOfCustomer() {
        return numberOfCustomer;
    }

    public void setNumberOfCustomer(int numberOfCustomer) {
        this.numberOfCustomer = numberOfCustomer;
    }

    @Override
    public int getAccountNumber() {
        return super.getAccountNumber();
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @Override
    public double getBalance() {
        return super.getBalance();
    }
}
