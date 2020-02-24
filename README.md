# ApplicationThatCreatesAndAdjustsXmlFiles

The program creates 2 files xml:
 - main.xml file
 - transactional.xml file


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
 
 
 
 
exemplary operation of the application:
******************

full file -> filesXml, there are xml files : main file and transaction file

******************

******************
Program for comparing xml files
******************
what you want to do, select the appropriate option:
1. Creates new 2 xml files by user
2. Creates new 2 xml files automatically
3. show current 2 files
4. use the current two files and show the result
5. exit 
> 2
*******************************

CREATE 2 NEW FILES AUTOMATICALLY: main.xml and transactional.xml
*******************************

created mainFile.XML
created transactionFile.XML
currently created 2 files automatically:
account number in the main file  name and surname     balance
100                              Jan Kowalski           24.98
200                              Anna Nowak           -345.67
800                              Jakub Sroka           224.62
400                              Ola Rudnik            -42.16
300                              Zofia Czekaj             0.0
500                              Jakub Sroka           224.62
700                              Artur Mistrz           800.0
500                              Jakub Sroka             32.0
1                                Jakub Sroka             32.0
1                                Jakub Sroka             32.0
300                              Zo Fa                    0.0



account number in the transaction file  transaction amount
100                                     27.14
300                                     0.00
900                                     82.17
400                                     66.56
400                                     -6.56
500                                     -30.12
400                                     0.00
200                                     23.56
200                                     -36.56
90                                      -36.56
-900                                    -36.56
700                                     -30.00




deletes the given duplicate account number -> 1 ...


deletes the given duplicate account number -> 300 ...


deletes the given duplicate account number -> 500 ...

displays 2 files after increasing segregation (account number): 
account number in the main file  name and surname     balance
1                                Jakub Sroka             32.0
100                              Jan Kowalski           24.98
200                              Anna Nowak           -345.67
300                              Zofia Czekaj             0.0
400                              Ola Rudnik            -42.16
500                              Jakub Sroka           224.62
700                              Artur Mistrz           800.0
800                              Jakub Sroka           224.62



account number in the transaction file  transaction amount
-900                                    -36.56
90                                      -36.56
100                                     27.14
200                                     23.56
200                                     -36.56
300                                     0.00
400                                     66.56
400                                     -6.56
400                                     0.00
500                                     -30.12
700                                     -30.00
900                                     82.17



displays 2 new files: result.txt and log.txt:
- result.txt -> list of customers with their transactions (shopping or payment)
- log.txt -> list of account numbers that are incorrect (from the transaction file)

*********************************
show xml result: 


1 accountNumber: 1 name: Jakub Sroka balance: 32.00
transactions:
account balance: 32.00, no transactions
 2 accountNumber: 100 name: Jan Kowalski balance: 24.98
transactions:
account balance: 52.12 -> shopping transaction
 3 accountNumber: 200 name: Anna Nowak balance: -345.67
transactions:
account balance: -322.11 -> shopping transaction
account balance: -358.67 -> payment transaction
 4 accountNumber: 300 name: Zofia Czekaj balance: 0.00
transactions:
account balance: 0.00 -> zero transaction
 5 accountNumber: 400 name: Ola Rudnik balance: -42.16
transactions:
account balance: 24.40 -> shopping transaction
account balance: 17.84 -> payment transaction
account balance: 17.84 -> zero transaction
 6 accountNumber: 500 name: Jakub Sroka balance: 224.62
transactions:
account balance: 194.50 -> payment transaction
 7 accountNumber: 700 name: Artur Mistrz balance: 800.00
transactions:
account balance: 770.00 -> payment transaction
 8 accountNumber: 800 name: Jakub Sroka balance: 224.62
transactions:
account balance: 224.62, no transactions
 
*********************************


*********************************
show file log: 


1. Invalid account number:
accountNumber: -900 transactionAmount: -36.56
2. Invalid account number:
accountNumber: 90 transactionAmount: -36.56
3. Invalid account number:
accountNumber: 900 transactionAmount: 82.17

*********************************


Process finished with exit code 0

