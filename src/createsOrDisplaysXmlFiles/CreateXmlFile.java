package createsOrDisplaysXmlFiles;

import transaction.Transaction;
import transaction.TransactionRecord2;
import account.Account;
import account.Accounts2;

import javax.xml.bind.JAXB;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * creates 2 text file : mainFile.xml and transactionFile.xml
 */
public class CreateXmlFile {

    private Scanner input = new Scanner(System.in);
    private Path mainFile, transactionFile;
    private TransactionRecord2 transactionRecord2;
    private Accounts2 accounts2;

    public CreateXmlFile(Path pathToMainFile, Path pathToTransactionFile) {
        this.mainFile = pathToMainFile;
        this.transactionFile = pathToTransactionFile;
        this.transactionRecord2 = new TransactionRecord2();
        this.accounts2 = new Accounts2();
    }

    public TransactionRecord2 getTransactionRecord2() {
        return transactionRecord2;
    }

    public void setTransactionRecord2(TransactionRecord2 transactionRecord2) {
        this.transactionRecord2 = transactionRecord2;
    }

    public Accounts2 getAccounts2() {
        return accounts2;
    }

    public void setAccounts2(Accounts2 accounts2) {
        this.accounts2 = accounts2;
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

    public void checksForAnyTxtFile() {
        File file = getMainFile().toFile();
        File file2 = getTransactionFile().toFile();

        checkTheFiles(file, file2);

        choiceUser();

    }

    private boolean checkTheFiles(File file, File file2) {
        System.out.println("******************\n");
        if( file.exists() || file2.exists() ) { // if files exist, notify the user
            System.out.println("full file -> filesText, there are xml files : main file and transaction file\n\n" +
                    "******************\n");
            return true;
        } else {
            System.out.println("empty file -> filesText\n\n" + "******************\n");
            return false;
        }
    }


    /**
     * user selection
     */
    private void choiceUser() {
            System.out.println("******************\nProgram for comparing xml files\n******************\n" + "what you want to do, select the appropriate option:");
            showOptions();
    }

    private void showOptions() {
        showMenu();

        boolean exit = true;

        while(exit) {
            exit = false;
            System.out.print("> ");
            if(input.hasNextInt()) {
                int valueByUser = input.nextInt();
                input.nextLine();

                switch (valueByUser) {
                    case 1: {
                        System.out.println("*******************************\nCREATE 2 NEW FILES: main.xml and transactional.xml\n" +
                                "*******************************\n");
                        createTwoFilesByTheUser();
                        System.out.println("currently created 2 files by the user:");
                        showTwoFiles();
                        break;
                    }
                    case 2: {
                        System.out.println("*******************************\n\nCREATE 2 NEW FILES AUTOMATICALLY: main.xml and transactional.xml\n" +
                                "*******************************\n");
                        createTwoFilesAutomatically();
                        System.out.println("currently created 2 files automatically:");
                        showTwoFiles();
                        break;
                    }
                    case 3: {
                        System.out.println("*******************************\nshow current 2 files\n" +
                                "*******************************\n");
                        if(checkTheFiles(getMainFile().toFile(), getTransactionFile().toFile())) {
                            showTwoFiles();
                        } else {
                            System.out.println("no Main file and no transaction file ...");
                        }
                        showMenu();
                        exit = true; //continue
                        break;
                    }
                    case 4: {
                        System.out.println("*******************************\n2 current files\n" +
                                "*******************************\n");

                        if( checkTheFiles( getMainFile().toFile(), getTransactionFile().toFile() ) ) {
                            showTwoFiles();
                        } else {
                            System.out.println("no Main file and no transaction file ...");
                            showMenu();
                            exit = true;
                        }
                        break;
                    }
                    case 5: {
                        System.out.println("*******************************\nexit\n" +
                                "*******************************\n");
                        System.exit(0); //exit from application
                    }
                    default: {
                        System.out.println("wrong value, range 1 - 4");
                        exit = true; //continue
                    }
                }

            } else {
                System.out.println("wrong value ... please again");
                input.nextLine();
            }
        }

    }

    private void showTwoFiles() {
        ReadXmlFile.ShowReadTextFromFile(getMainFile(), getTransactionFile());
        System.out.println("\n\n");
    }

    private void showMenu() {
        System.out.println("1. Creates new 2 xml files by user\n2. Creates new 2 xml files automatically" +
                "\n3. show current 2 files\n4. use the current two files and show the result\n5. exit ");
    }


    /**
     * for example : 200 Patrick Pallot 8909.22
     *               600 Andrew Jakd 899
     */
    public void createTwoFilesByTheUser() {

        System.out.println("*******************************\n\n" +
                "the customer account number must be greater than 0, if the user enters several accounts " +
                "with the number less than 0, then all users are removed by the program and leaves only one of" +
                " them and sets the account number 1\n" +
                "*******************************\n");

        System.out.println("Provide customer data as in the example:\n" +
                "300 Patryk Prusko 9000.99\n" +
                "(use spaces between data, don't use comma)");

        boolean exit = true;
        try(  BufferedWriter outputMainFile = Files.newBufferedWriter(getMainFile()) ) { //mainFile.xml

            System.out.printf("%s%n",
                    "give me a number of account, name , surname, balance( for example: 100 Patrick Pat 567.99 or Pati Pat 200 -13.92 ):" );
            while(exit) {

                try {
                    Account record = new Account(input.nextInt(), input.next(), input.next(), input.nextDouble()); //create new record
                    System.out.println("adding a new record ...");

                    getAccounts2().getAccountList2().add(record);

                } catch (NoSuchElementException ex) {
                    System.err.println("error , wrong value");
                    input.nextLine();
                }

                 exit = checkTheUserDecision();
            }

            JAXB.marshal(getAccounts2(), outputMainFile); //save object AccountList -> XML

        } catch (IOException e) {
            e.printStackTrace();
        }


        exit = true;
        try(BufferedWriter outputTransactionFile = Files.newBufferedWriter(getTransactionFile()) ) { //TransactionFile

            System.out.printf("%s%n", "give me the account number for the transaction, transaction amount( for example: 100 567.99 or 200 -13.92 ): ");
            while(exit) {

                try {
                    Transaction record = new Transaction(input.nextInt(), input.nextDouble()); //adds new record to transactionFile.txt
                    System.out.println("adding a new record ...");
                    getTransactionRecord2().getListOfTransactions2().add(record);

                } catch (NoSuchElementException ex) {
                    System.err.println("error, wrong value");
                    input.nextLine();
                }

                exit = checkTheUserDecision();
            }

            JAXB.marshal(getTransactionRecord2(), outputTransactionFile);

        } catch (IOException e) {
            System.err.println("error TransactionFile.xml");
            e.printStackTrace();
        }

    }

    // checks the user's decision
    private boolean checkTheUserDecision() {

        boolean exit = false, decision = false;
        System.out.println("if you want to finish adding records, press: y or continue press : n");
        while(! exit) {

                String str = input.next();
                if (str.equals("y")) {
                    exit = true;
                } else if (str.equals("n")) {
                    decision = true;
                    exit = true;
                } else {
                    System.err.println("wrong value ... please again");
                    System.out.println("if you want to finish adding records, press: y or continue press : n");
                    input.nextLine();
                }

        }
        return decision;
    }

    /**
     * Sort records by account number in ascending orde
     * @param pathOfMainFile
     * @param
     * @return accounts
     */
    public void segregateRecordsInTheMainTextFile(Path pathOfMainFile)  {

       getAccounts2().getAccounts(pathOfMainFile, getAccounts2() );
    }


    private void createTwoFilesAutomatically() {

        try (BufferedWriter outputFileMain = Files.newBufferedWriter(getMainFile()) ) {

            String str = "100 Jan Kowalski 24.98 200 Anna Nowak -345.67 800 Jakub Sroka 224.62 " +
                    "400 Ola Rudnik -42.16 "   +
                    "300 Zofia Czekaj 0.00 "   +
                    "500 Jakub Sroka 224.62 "  +
                    "700 Artur Mistrz 800.00 " +
                    "500 Jakub Sroka 32.00 "   +
                    "0 Jakub Sroka 32.00 "     +
                    "-13 Jakub Sroka 32.00 "   +
                    "300 Zo Fa 0.00";
            String[] arrayStrings = str.split(" ");

            for(int i = 0; i+3 < arrayStrings.length; i += 4) {
                getAccounts2().getAccountList2().add(new Account( (Integer.valueOf(arrayStrings[i])), arrayStrings[i+1], arrayStrings[i+2], (Double.valueOf(arrayStrings[i+3])) )  );
            }
            JAXB.marshal(getAccounts2(), outputFileMain);
            System.out.println("created mainFile.XML");
        } catch (IOException e) {
            System.err.println("problem file main.XML");
            e.printStackTrace();
        }

        try (BufferedWriter outputFileTransaction = Files.newBufferedWriter(getTransactionFile())) {

            String str = "100 27.14 " +
                        "300 0 "      +
                        "900 82.17 "  +
                        "400 66.56 "  +
                        "400 -6.56 "  +
                        "500 -30.12 " +
                        "400 0.00 "   +
                        "200 23.56 "  +
                        "200 -36.56 " +
                        "90 -36.56 " +
                        "-900 -36.56 " +
                        "700 -30.00";
            String[] arrayStrings = str.split(" ");
            for(int i = 0; i+1 < arrayStrings.length; i += 2) {
                getTransactionRecord2().getListOfTransactions2().add(new Transaction( (Integer.valueOf(arrayStrings[i])), (Double.valueOf(arrayStrings[i+1]))  ));
            }
            JAXB.marshal(getTransactionRecord2(), outputFileTransaction);
            System.out.println("created transactionFile.XML");

        } catch (IOException e) {
            System.err.println("problem file transaction XML");
            e.printStackTrace();
        }

    }

    /**
     * sorts the transaction file in ascending order
     * @param pathToTransactionFile
     * @return
     */
    public void segregateRecordsInTheTransactionTextFile(Path pathToTransactionFile) {

        getTransactionRecord2().getTransactionRecord(pathToTransactionFile, getTransactionRecord2() );
    }
}



