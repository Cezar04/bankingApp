package com.cezar.bankingapp.transaction.service;

import com.cezar.bankingapp.account.Account;
import com.cezar.bankingapp.account.AccountRepository;
import com.cezar.bankingapp.customer.Customer;
import com.cezar.bankingapp.customer.CustomerRepository;
import com.cezar.bankingapp.helper.BankingServiceHelper;
import com.cezar.bankingapp.transaction.OperationOnAccountDAO;
import com.cezar.bankingapp.transaction.TransferDetailsDAO;
import com.cezar.bankingapp.transaction.Transaction;
import com.cezar.bankingapp.transaction.TransactionDAO;
import com.cezar.bankingapp.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;
    private BankingServiceHelper bankingServiceHelper;
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, BankingServiceHelper bankingServiceHelper, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.bankingServiceHelper = bankingServiceHelper;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public ResponseEntity<?> transferDetails(TransferDetailsDAO transferDetailsDAO, UUID customerId) {
        List<Account> accounts = new ArrayList<>();
        Account fromAccount;
        Account toAccount;

        Optional<Customer> customerEntityOptional = customerRepository.findById(customerId);

        if (customerEntityOptional.isPresent()){
            // get FROM ACCOUNT info
            Optional<Account> fromAccountEntityOptional = accountRepository.findByAccountNumber(transferDetailsDAO.getFromAccountNumber());
            if (fromAccountEntityOptional.isPresent()){
                fromAccount = fromAccountEntityOptional.get();
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("account number "+ transferDetailsDAO.getFromAccountNumber()+" not found.");
            }

            // get TO ACCOUNT info
            Optional<Account> toAccountEntityOptional = accountRepository.findByAccountNumber(transferDetailsDAO.getToAccountNumber());
            if (toAccountEntityOptional.isPresent()){
                toAccount = toAccountEntityOptional.get();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("account number "+ transferDetailsDAO.getToAccountNumber()+" not found.");
            }

            //insufficient funds
            if (fromAccount.getAccountBalance()<transferDetailsDAO.getTransferAmount()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds!");
            } else{
                synchronized (this){
                    fromAccount.setAccountBalance(fromAccount.getAccountBalance()-transferDetailsDAO.getTransferAmount());
                    fromAccount.setUpdateDateTime(new Date());
                    accounts.add(fromAccount);

                    toAccount.setAccountBalance(toAccount.getAccountBalance()+transferDetailsDAO.getTransferAmount());
                    toAccount.setUpdateDateTime(new Date());
                    accounts.add(toAccount);

                    accountRepository.saveAll(accounts);

                    Transaction fromTransaction = bankingServiceHelper.createTransaction(transferDetailsDAO,fromAccount.getAccountNumber(),"Debit");
                    transactionRepository.save(fromTransaction);

                    Transaction toTransaction = bankingServiceHelper.createTransaction(transferDetailsDAO,toAccount.getAccountNumber(),"credit");
                    transactionRepository.save(toTransaction);
                }
                return ResponseEntity.status(HttpStatus.OK).body("Amount transfer for Customer "+customerId);
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Number " + customerId + " not found.");
        }

    }

    @Override
    public List<TransactionDAO> findTransactionByAccountNumber(Long accountNumber) {
        List<TransactionDAO> transactionList = new ArrayList<>();
        Optional<Account> accountEntityOptional = accountRepository.findByAccountNumber(accountNumber);
        if (accountEntityOptional.isPresent()){
            Optional<List<Transaction>> optionalTransactions = transactionRepository.findByAccountNumber(accountNumber);
            if (optionalTransactions.isPresent()){
                optionalTransactions.get().forEach(transaction -> transactionList.add(bankingServiceHelper.convertToTransactionDAO(transaction)));
            }
        }
        return transactionList;
    }

    @Override
    public ResponseEntity<?> deposit(OperationOnAccountDAO operationOnAccountDAO, UUID customerId) {
        Account account;

        Optional<Customer> customerEntityOptional = customerRepository.findById(customerId);

        if (customerEntityOptional.isPresent()){
            Optional<Account> accountOptional = accountRepository.findByAccountNumber(operationOnAccountDAO.getAccountNumber());

            if (accountOptional.isPresent()){
                account= accountOptional.get();
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("From Account Number " + operationOnAccountDAO.getAccountNumber()+ " not found.");
            }

            account.setAccountBalance(account.getAccountBalance()+operationOnAccountDAO.getTransferAmount());
            account.setUpdateDateTime(new Date());

            accountRepository.save(account);

            Transaction transaction= bankingServiceHelper.updateAccount(operationOnAccountDAO,account.getAccountNumber(),"DEPOSIT");
            transactionRepository.save(transaction);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success Operation  for Customer Number " + customerId);
    }

    @Override
    public ResponseEntity<?> withdraw(OperationOnAccountDAO operationOnAccountDAO, UUID customerId) {
        Account account;

        Optional<Customer> customerEntityOptional = customerRepository.findById(customerId);

        if (customerEntityOptional.isPresent()){
            Optional<Account> accountOptional = accountRepository.findByAccountNumber(operationOnAccountDAO.getAccountNumber());

            if (accountOptional.isPresent()){
                account= accountOptional.get();
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("From Account Number " + operationOnAccountDAO.getAccountNumber()+ " not found.");
            }
            if (account.getAccountBalance()< operationOnAccountDAO.getTransferAmount()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient Funds.");
            }else{
                account.setAccountBalance(account.getAccountBalance() - operationOnAccountDAO.getTransferAmount());
                account.setUpdateDateTime(new Date());

                accountRepository.save(account);

                Transaction transaction= bankingServiceHelper.updateAccount(operationOnAccountDAO,account.getAccountNumber(),"DEPOSIT");
                transactionRepository.save(transaction);
            }
            return ResponseEntity.status(HttpStatus.OK).body("Success Operation  for Customer Number " + customerId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Number " + customerId + " not found.");
        }
    }


}
