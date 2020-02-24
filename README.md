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
 
 


options to choose from in the application:

******************
Program for comparing xml files
******************
what you want to do, select the appropriate option:
1. Creates new 2 xml files by user
2. Creates new 2 xml files automatically
3. show current 2 files
4. use the current two files and show the result
5. exit 
> 
*******************************


