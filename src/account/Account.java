package account;


import transaction.Transaction;

/**
 * Account class for storing records as objects
 */
public class Account implements Comparable<Account>{
   private int accountNumber;
   private String firstName;
   private String lastName;
   private double balance;
   

   public Account() {
      this(1, "----", "----", 0.00);
   }

   public Account(int accountNumber, String firstName,
                  String lastName, double balance) {

      this.accountNumber = checksTheAccountNumber(accountNumber);
      this.firstName = checksTheString(firstName);
      this.lastName = checksTheString(lastName);
      this.balance = balance;
   }

   private int checksTheAccountNumber(int accountNumber) {
      if(accountNumber < 1) {
         return 1;
      } else {
         return accountNumber;
      }
   }

   private String checksTheString(String name) {
      if(name.isEmpty() || name.charAt(0) == ' ') { //if it is empty or the beginning begins with a space
         return "----";
      } else {
         return name;
      }
   }

   public int getAccountNumber() {return accountNumber;} 

   public void setAccountNumber(int accountNumber) 
      {this.accountNumber = accountNumber;}

   public String getFirstName() {return firstName;} 

   public void setFirstName(String firstName) 
      {this.firstName = firstName;} 

   public String getLastName() {return lastName;} 

   public void setLastName(String lastName) {this.lastName = lastName;}

   public double getBalance() {return balance;} 

   public void setBalance(double balance) {this.balance = balance;}

   /**
    * changes the balance of a given customer
    * @param tr
    * @return
    */
   public boolean combine(Transaction tr) {
      if(tr.getAccountNumber() == getAccountNumber()) {
         balance += tr.getTransactionAmount();
         return true;
      }
      return false;
   }

   @Override
   public String toString() {
      return "Account{" +
              "accountNumber=" + accountNumber +
              ", firstName='" + firstName + '\'' +
              ", lastName='" + lastName + '\'' +
              ", balance=" + balance +
              '}';
   }

   @Override
   public int compareTo(Account o) {
      return this.getAccountNumber() - o.getAccountNumber(); //if positive result -> change positions (200 - 100)
   }


}
