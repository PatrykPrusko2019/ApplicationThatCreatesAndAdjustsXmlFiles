package comparesFiles;

import transaction.Transaction;
import transaction.TransactionRecord2;
import account.Account;
import account.Accounts2;
import resultAndLogFile.AccountResultXML;
import resultAndLogFile.AccountsResult;
import resultAndLogFile.LogXML;

import javax.xml.bind.JAXB;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * matches files
 */
public class FileMatch {

    Accounts2 accouns;
    TransactionRecord2 transactionRecord;
    AccountsResult accountsResult;
    LogXML logXML;
    Path mainFile, transactionFile, logFile, resultFile;


    public FileMatch(Accounts2 accounts, TransactionRecord2 transactionRecord, Path pathToMainFile, Path pathToTransactionFile) {
        this.accouns = accounts;
        this.transactionRecord = transactionRecord;
        this.mainFile = pathToMainFile;
        this.transactionFile = pathToTransactionFile;
        this.resultFile = Paths.get("src/filesXml/result.xml");
        this.logFile = Paths.get("src/filesXml/log.xml");
        this.accountsResult = new AccountsResult();
        this.logXML = new LogXML();
    }

    public AccountsResult getAccountsResult() {
        return accountsResult;
    }

    public LogXML getLogXML() {
        return logXML;
    }

    public Path getMainFile() {
        return mainFile;
    }
    public void setMainFile(Path mainFile) {
        this.mainFile = mainFile;
    }
    public Path getTransactionFile() {
        return transactionFile;
    }
    public void setTransactionFile(Path transactionFile) {
        this.transactionFile = transactionFile;
    }
    public Accounts2 getAccouns() {
        return accouns;
    }
    public void setAccouns(Accounts2 accouns) {
        this.accouns = accouns;
    }
    public TransactionRecord2 getTransactionRecord() {
        return transactionRecord;
    }
    public void setTransactionRecord(TransactionRecord2 transactionRecord) {
        this.transactionRecord = transactionRecord;
    }
    public Path getLogFile() {
        return logFile;
    }
    public Path getResultFile() {
        return resultFile;
    }
    /**
     * match data records from 2 text files by account number
     */
    public void matchTheGivenFiles() {


        List<Account> recordsOfAccount= getAccouns().getAccountList2();
        List<Transaction> recordsOfTransaction = getTransactionRecord().getListOfTransactions2();

        boolean[] transactionInstanceCounter = new boolean[recordsOfTransaction.size() + 1];

        Account customerRecord;
        String strNewMainFile = "";
        int counter = 0;

        for(int i = 0; i < recordsOfAccount.size(); i++, counter = 0) {

           customerRecord = recordsOfAccount.get(i);

           AccountResultXML record = new AccountResultXML (i+1, customerRecord.getAccountNumber(), customerRecord.getFirstName(),
                   customerRecord.getLastName(), customerRecord.getBalance(), strNewMainFile); //creates a new record

            //compares by account number

            for(int j = 0; j < recordsOfTransaction.size(); j++) {

                if( customerRecord.combine(recordsOfTransaction.get(j)) ) { // if true they are the same
                    String str = getResultOfTransaction(recordsOfTransaction.get(j).getTransactionAmount() );

                    strNewMainFile += String.format("account balance: %.2f -> %s%n", customerRecord.getBalance(), str);
                    counter++;
                }
            }

            if(counter == 0) { // if there were no transactions, rewrite the given record without any changes
                strNewMainFile += String.format("account balance: %.2f, no transactions%n", customerRecord.getBalance() );
            }
            record.setTransaction(strNewMainFile); // updates the list
            getAccountsResult().getListOfResult2().add(record);
            strNewMainFile = "";
        }

        transactionInstanceCounter = returnIndexWhereNoTransactions(transactionInstanceCounter, recordsOfAccount, recordsOfTransaction);


        createsResultFileAndLogFile( transactionInstanceCounter); // creates a new result.txt and log.txt file

    }

    /**
     * returns boolean arrays with indexes that are invalid true -> transactions
     * @param transactionInstanceCounter
     * @param recordsOfAccount
     * @param recordsOfTransaction
     * @return boolean result
     */
    private boolean[] returnIndexWhereNoTransactions(boolean[] transactionInstanceCounter, List<Account> recordsOfAccount, List<Transaction> recordsOfTransaction) {
        int transaction, counter = 0;

        for(int i = 0; i < recordsOfTransaction.size(); i++, counter = 0) {
            transaction = recordsOfTransaction.get(i).getAccountNumber();
                    for(int j = 0; j < recordsOfAccount.size(); j++) {

                        if( transaction == recordsOfAccount.get(j).getAccountNumber() ) {
                            counter++;
                        }
                    }

                    if(counter == 0) {
                        transactionInstanceCounter[i+1] = true;
                    }
        }
        return transactionInstanceCounter;
    }


    /**
     * if the transaction amount is positive return "purchases" if the transaction
     * amount is negative return "payment"
     * @param transactionAmount
     * @return
     */
    private String getResultOfTransaction(double transactionAmount) {
        if(transactionAmount == 0) {
            return "zero transaction";
        } else if(transactionAmount > 0) {
            return "shopping transaction";
        } else {
            return "payment transaction";
        }
    }

    /**
     * creates 2 files: new main file and file with transaction logs
     * @param transactionInstanceCounter
     */
    private void createsResultFileAndLogFile( boolean[] transactionInstanceCounter) {

        //first creates the result file

        try (BufferedWriter outputResultFile = Files.newBufferedWriter(resultFile )) {

            JAXB.marshal( accountsResult, outputResultFile);

        } catch (IOException ex) {
            System.err.println("wrong result.xml");
            ex.printStackTrace();
        }

        //next creates the log file


        try (BufferedWriter outputLogFile = Files.newBufferedWriter(logFile )) {

            createTextTologFile(transactionInstanceCounter);

            JAXB.marshal( logXML, outputLogFile);

        } catch (IOException ex) {
            System.err.println("wrong log.xml");
            ex.printStackTrace();
        }




    }

    /**
     * creates text into a log file

</logXML>
     */
    private void createTextTologFile(boolean[] transactionInstanceCounter) {
        //todo logi gdy false transactionInstanceCounter[1] == recordsOfTransaction[0] to dopisujemy do log.txt
        List<Transaction> recordsOfTransaction = getTransactionRecord().getListOfTransactions2();

        for(int i = 0; i < transactionInstanceCounter.length-1; i++) {

            if(transactionInstanceCounter[i+1] == true) { //when true -> then the given account number did not appear and saves to log.txt
                Transaction record = new Transaction(recordsOfTransaction.get(i).getAccountNumber(), recordsOfTransaction.get(i).getTransactionAmount() );
                getLogXML().getListOfLogs().add(record);
            }
        }

    }
}
