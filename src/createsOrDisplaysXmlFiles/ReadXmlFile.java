package createsOrDisplaysXmlFiles;

import transaction.Transaction;
import transaction.TransactionRecord2;
import account.Account;
import account.Accounts2;
import resultAndLogFile.AccountResultXML;
import resultAndLogFile.AccountsResult;
import resultAndLogFile.LogXML;

import javax.xml.bind.JAXB;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReadXmlFile {


    //aby otworzyc pliki xml trzeba uzyc klasy JAXB aby zdekodowac
    public static void ShowReadTextFromFile(Path mainFile, Path transactionFile) {

        try (BufferedReader input = Files.newBufferedReader(mainFile)) {

            Accounts2 accounts = JAXB.unmarshal(input, Accounts2.class); //zdekoduj zawartosc pliku

            List<Account> listOfRecords = accounts.getAccountList2();

            System.out.printf("%-33s%-18s%10s%n" ,"account number in the main file", "name and surname", "balance");
            for(Account record : listOfRecords) {
                System.out.printf("%-33s%-18s%10s%n", record.getAccountNumber(), record.getFirstName()+ " " +record.getLastName(), record.getBalance() );
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("error MainFile.xml");
        }




        try ( BufferedReader input = Files.newBufferedReader(transactionFile) ) {

            TransactionRecord2 transactionRecord = JAXB.unmarshal(input, TransactionRecord2.class); //zdekoduj zawartosc pliku
            List<Transaction> listOfRecords = transactionRecord.getListOfTransactions2();

            System.out.printf("%n%n%n%-40s%14s%n" ,"account number in the transaction file", "transaction amount");

            for(Transaction record : listOfRecords) {
                System.out.printf("%-40s%.2f%n", record.getAccountNumber(), record.getTransactionAmount());
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("error TransactionFile.xml");
        }

    }

    public static void showReadTextFromFilesResultAndLog(Path fileResult, Path fileLog) {
        System.out.println("displays 2 new files: result.txt and log.txt:\n" +
                "- result.txt -> list of customers with their transactions (shopping or payment)\n" +
                "- log.txt -> list of account numbers that are incorrect (from the transaction file)");
        try (BufferedReader inputFileResult = Files.newBufferedReader(fileResult)){

            AccountsResult accountsResult = JAXB.unmarshal(inputFileResult, AccountsResult.class);
            List<AccountResultXML> accountResultXMLList = accountsResult.getListOfResult2();


            System.out.println("\n*********************************\nshow xml result: \n\n");

            for(AccountResultXML record : accountResultXMLList) {
                System.out.printf("%d accountNumber: %d name: %s balance: %.2f%ntransactions:%n%s " , record.getNumberOfCustomer(), record.getAccountNumber(),
                        record.getFirstName() + " " + record.getLastName(), record.getBalance(), record.getTransaction() );
            }
            System.out.println("\n*********************************\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(BufferedReader inputFileLog = Files.newBufferedReader(fileLog)){

            LogXML logXML = JAXB.unmarshal(inputFileLog, LogXML.class);
            List<Transaction> recordsOfFileLog = logXML.getListOfLogs();

            System.out.println("\n*********************************\nshow file log: \n\n");

            int i = 1;
            for(Transaction record : recordsOfFileLog) {
                System.out.printf( "%d. Invalid account number:%naccountNumber: %d transactionAmount: %.2f%n" , (i++), record.getAccountNumber() ,
                        record.getTransactionAmount());
            }

            System.out.println("\n*********************************\n");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
