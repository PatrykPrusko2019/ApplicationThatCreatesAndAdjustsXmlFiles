package account;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElement;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Accounts2 {

    @XmlElement(name = "beforeAccount")
    private List<Account> accountList = new ArrayList<>();

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public List<Account> getAccountList2() {
        return accountList;
    }


    public void getAccounts(Path pathOfMainFile, Accounts2 accounts) {

        checkIfCanSort(pathOfMainFile);

        try (BufferedWriter outputMainFile = Files.newBufferedWriter(pathOfMainFile) ) {

            accounts = sortAscendingByAccountNumber(accounts);

            JAXB.marshal(accounts, outputMainFile); //zapisz obiekt jako plik xml

        } catch (IOException e) {
            System.err.println("problem with the mainFile.XML");
            e.printStackTrace();
        }

    }

    private void checkIfCanSort(Path pathOfMainFile) {
        if(getAccountList2().isEmpty()) {
            completeList(pathOfMainFile);
        }
    }

    /**
     * completes the list to be able to sort the data
     * @param pathOfMainFile
     */
    private void completeList(Path pathOfMainFile) {

        try (BufferedReader inputFileMain = Files.newBufferedReader(pathOfMainFile)) {

            Accounts2 accounts2 = JAXB.unmarshal(inputFileMain, Accounts2.class);

            setAccountList(accounts2.getAccountList2());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * sort ascending list of accounts
     * @param accounts
     */
    private Accounts2 sortAscendingByAccountNumber(Accounts2 accounts) {

        List<Account> sortedList = accounts.getAccountList2();
        sortedList = sortedList.stream().sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList());

        sortedList = removeDuplicateAccountNumbers(sortedList); // removes duplicate account number

        accounts.setAccountList(sortedList);

        return accounts;
    }


    /**
     * removes duplicate account numbers form the main file
     * @param sortedList
     * @return returns a list of unique accounts
     */
    private List<Account> removeDuplicateAccountNumbers(List<Account> sortedList) {

        int numberAccount;
        for(int i = 0; i < sortedList.size(); i++) {
            numberAccount = sortedList.get(i).getAccountNumber();
            for(int j = i+1; j < sortedList.size(); j++) {

                if( numberAccount == sortedList.get(j).getAccountNumber() ) {
                    sortedList.remove(j);
                    System.out.println("\ndeletes the given duplicate account number -> " + numberAccount + " ...\n");
                }
            }
        }
        return sortedList;
    }


    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        int i = 1;
        for (Account account : accountList) {
            stringBuilder.append( (i++) + " " + account + "\n" );
        }

        return "Accounts{\n"+ stringBuilder +
                '}';
    }

}
