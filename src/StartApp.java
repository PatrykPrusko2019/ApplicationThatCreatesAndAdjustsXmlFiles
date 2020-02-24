import comparesFiles.FileMatch;
import createsOrDisplaysXmlFiles.CreateXmlFile;
import createsOrDisplaysXmlFiles.ReadXmlFile;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 *
 *
 The program creates 2 text files xml:
 - main file
 - transactional file


 Creating a new file with accounts, the user must provide account numbers different
 and greater than 0, otherwise the client data will be deleted by the program.

 Later sorts in ascending order by account number and
 removes duplicate account numbers (leaving the first, the rest removes)

 Later compares these two files, by customer account number:
 - if the account numbers in both files match, it checks what the transaction was.
 - if the numbers do not match, it either adds the wrong account number to the log
 file or the given record without changes.

 As a result, it shows the results on the console in a new xml file and log file

 displays 2 new files: result.xml and log.xml:
 - result.txt -> list of customers with their transactions (shopping or payment or zero);
 if the shopping transaction is a positive transaction amount,
 if the payment transaction is a negative transaction amount,
 if the zero transaction is a zero transaction amount.

 - log.xml -> list of account numbers that are incorrect (from the transaction file)


 Assumptions for transactions -> if they are positive (purchases) we add to the
 customer's balance -> 1000 + 25 = 1025 balance (purchases),
 if they are negative (payments) we also add to the customer's balance -> 1000 + (-25) = 975 balance (payment)
 */


public class StartApp {

    private CreateXmlFile createXmlFile;

    public static void main(String[] args) {

        new StartApp().start();
    }

    private void start() {

        Path pathToMainFile = Paths.get("src/filesText/mainFile.xml");
        Path pathToTransactionFile = Paths.get("src/filesText/transactionFile.xml");

        createXmlFile = new CreateXmlFile(pathToMainFile, pathToTransactionFile);

        createXmlFile.checksForAnyTxtFile(); //create 2 files text -> mainFile.txt , transactionFile.txt by users or automatically

        createsObjectsAndSegregatesRecordsInFiles(pathToMainFile, pathToTransactionFile);

    }


    private void createsObjectsAndSegregatesRecordsInFiles(Path pathToMainFile, Path pathToTransactionFile) {

        createXmlFile.segregateRecordsInTheMainTextFile(pathToMainFile);

        createXmlFile.segregateRecordsInTheTransactionTextFile(pathToTransactionFile);

        FileMatch fileMatch = new FileMatch(createXmlFile.getAccounts2(), createXmlFile.getTransactionRecord2(),pathToMainFile, pathToTransactionFile);

        fileMatch.matchTheGivenFiles(); // matching records

        System.out.println("displays 2 files after increasing segregation (account number): ");
        ReadXmlFile.ShowReadTextFromFile(pathToMainFile, pathToTransactionFile);
        System.out.println("\n\n");
        ReadXmlFile.showReadTextFromFilesResultAndLog ( fileMatch.getResultFile(), fileMatch.getLogFile() ); //displays the result after changes to 2 files:
                                                                                                              // the main file and the transaction file
    }

}
