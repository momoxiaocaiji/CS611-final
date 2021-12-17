# CS611-final

## Name

---

Zheng Jiang

U88611204

Harsh Mutha

U84186562

Renxuan Cao

U39593434


coding my project on MacOS


## Files

---
1. HistoryTable - With this class, the page of the customer and manager can easily create a history table to check the transaction history.
2. LoanListPanel - With this class, the main frame can create a plane which can create several detail loans panels according to the length of LoanList and can change dynamically.
3. StockListPanel - With this class, the main frame can create a plane which can create several detail stocks panels according to the length of StockList and can change dynamically
4. Stock - a class to convert the backend stock into the fontend one.
5. Constant - maintain some final factor of this ATM
6. Core - The main page for the customer
7. CreateAccount - the page for customer to create the checking or saving account.
8. CreateSecuritiesAccount - the page for customer to create the Securities account.
9. LoanDetail - The page for customer to make a loan
10. Login - The login page.
11. Manager - The main page for the manager
12. Registry - The registry page.
13. StockDetail - The page for creating a stock
14. TransactionDetail - The page for customer to make a transaction


## Backend Classes

---
### Model
1. Person - model to denote a person in our application
2. Customer - customer using the application. 
3. Manager - manager for the bank
4. Bank - model for a bank
5. Stock - model for a stock
6. Transaction - model for transactions
7. login - login details for a person
8. Account - model for account
9. Checking Account - model for a checking account of a customer
10. Saving Account - model for a saving of a customer
11. Securities Account - model for a securities account
12. Loan - model for a loan
13. CustomerOwnedStock - stocks owned by customer


### Controller and Service
Each controller has a corresponding service class which consists of helper functions to implement the logic of the controller.
1. AccountController & AccountService - classes to implement functions related to accounts. 
2. BankManagerController & BankManagerService - functions and tasks carried out by the bank manager
3. DbController & DbService - connect to database
4. LoanController & LoanService - classes to implement functions related to loans of a customer
5. LoginController & LoginService - classes to implement signIn, signUp etc.
6. StockController & StockService - classes to implement stock and securities functionalities
7. TransactionController & TransactionService - classes to implement transaction features such as recording txs, making deposits and withdrawals, etc.



## Note

---
1. might bonus
   1. some extension
        1. customer can create several saving or checking account
   2. have a Constant class to maintain all final factor, which makes it easier to change the factor.
   3. Design Pattern
       1. I use MVC Pattern for the whole design