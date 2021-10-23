package com.cezar.bankingapp.transaction.service;

import com.cezar.bankingapp.account.Account;
import com.cezar.bankingapp.account.AccountRepository;
import com.cezar.bankingapp.customer.Customer;
import com.cezar.bankingapp.customer.CustomerRepository;
import com.cezar.bankingapp.helper.BankingServiceHelper;
import com.cezar.bankingapp.transaction.CustomerAccountReference.TransferDetailsDAO;
import com.cezar.bankingapp.transaction.Transaction;
import com.cezar.bankingapp.transaction.TransactionDAO;
import com.cezar.bankingapp.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> transferDetails(TransferDetailsDAO transferDetailsDAO, Long customerNumber) {
        List<Account> accounts = new ArrayList<>();
        Account fromAccount = null;
        Account toAccount = null;

        Optional<Customer> customerEntityOptional = customerRepository.findByCustomerNumber(customerNumber);

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
                return ResponseEntity.status(HttpStatus.OK).body("Amount transfferd for Customer "+customerNumber);
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Number " + customerNumber + " not found.");
        }

    }

    @Override
    public List<TransactionDAO> findTransactionByAccountNumber(Long accountNumber) {
        return null;
    }
}
