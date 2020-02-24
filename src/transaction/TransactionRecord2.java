package transaction;

import account.Accounts2;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionRecord2 {

    @XmlElement(name = "transaction")
    private List<Transaction> transactionList = new ArrayList<>();

    public List<Transaction> getListOfTransactions2() {
        return transactionList;
    }
    public void setListOfTransactions(List<Transaction> listOfTransactions) {
        this.transactionList = listOfTransactions;
    }

    public void getTransactionRecord(Path pathToTransactionFile, TransactionRecord2 transactionRecord) {

        checkIfCanSort(pathToTransactionFile);

        try (BufferedWriter outputTransactionFile = Files.newBufferedWriter(pathToTransactionFile)) {

            transactionRecord = sortAscendingByAccountNumber(transactionRecord);

            JAXB.marshal(transactionRecord, outputTransactionFile);

        } catch (IOException e) {
            System.err.println("problem with the transactionFile.txt");
            e.printStackTrace();
        }

    }


    private void checkIfCanSort(Path pathToTransactionFile) {
        if(getListOfTransactions2().isEmpty()) {
            completeList(pathToTransactionFile);
        }
    }

    /**
     * completes the list to be able to sort the data
     * @param pathToTransactionFile
     */
    private void completeList(Path pathToTransactionFile) {

        try (BufferedReader inputFileMain = Files.newBufferedReader(pathToTransactionFile)) {

            TransactionRecord2 transactionRecord2 = JAXB.unmarshal(inputFileMain, TransactionRecord2.class);

            setListOfTransactions(transactionRecord2.getListOfTransactions2());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sort ascending list of accounts in transaction file
     * @param transactionRecord
     */
    private TransactionRecord2 sortAscendingByAccountNumber( TransactionRecord2 transactionRecord) {

        List<Transaction> sortedList = transactionRecord.getListOfTransactions2();

        sortedList = sortedList.stream().sorted((o1, o2) -> o1.compareTo( o2 ) )
                .collect(Collectors.toList());

        transactionRecord.setListOfTransactions(sortedList); // assigns a new sorted list

        return transactionRecord;
    }

    @Override
    public String toString() {
        int counter = 1;
        StringBuilder stringBuilder = new StringBuilder();
        for(Transaction tr : transactionList) {
            stringBuilder.append( String.format("%d %d %.2f%n" , (counter++), tr.getAccountNumber(), tr.getTransactionAmount() ) );
        }

        return String.valueOf(stringBuilder);
    }
}


