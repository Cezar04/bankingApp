package com.cezar.bankingapp.account.service;


import com.cezar.bankingapp.transaction.CustomerAccountReference.CustomerAccountRef;
import com.cezar.bankingapp.transaction.CustomerAccountReference.CustomerAccountRefRepository;
import com.cezar.bankingapp.account.Account;
import com.cezar.bankingapp.account.AccountDAO;
import com.cezar.bankingapp.account.AccountRepository;
import com.cezar.bankingapp.customer.Customer;
import com.cezar.bankingapp.customer.CustomerRepository;
import com.cezar.bankingapp.helper.BankingServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

    private  AccountRepository accountRepository;
    private  BankingServiceHelper bankingServiceHelper;
    private  CustomerRepository customerRepository;
    private CustomerAccountRefRepository accountRefRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, BankingServiceHelper bankingServiceHelper, CustomerRepository customerRepository, CustomerAccountRefRepository accountRefRepository) {
        this.accountRepository = accountRepository;
        this.bankingServiceHelper = bankingServiceHelper;
        this.customerRepository = customerRepository;
        this.accountRefRepository = accountRefRepository;
    }

    @Override
    public ResponseEntity<?> addNewAccount(AccountDAO accountDAO, UUID customerId) {
        Optional<Customer> customerEntityOptional = customerRepository.findById(customerId);

        if (customerEntityOptional.isPresent()){
            accountRepository.save(bankingServiceHelper.convertToAccountEntity(accountDAO));

            accountRefRepository.save(CustomerAccountRef.builder()
                    .accountNumber(accountDAO.getAccountNumber())
                    .customerNumber(customerId)
                    .build());

        }

        return new ResponseEntity<>(accountDAO,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByAccountNumber(Long accountNumber) {

        Optional<Account> accountEntityOptional = accountRepository.findByAccountNumber(accountNumber);

        if (accountEntityOptional.isPresent()){
            return new ResponseEntity<>(bankingServiceHelper.convertToAccountDAO(accountEntityOptional.get()), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("account "+accountNumber+ " not found.");
        }
    }
}
