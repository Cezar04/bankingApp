package com.cezar.bankingapp.controller;

import com.cezar.bankingapp.account.AccountDAO;
import com.cezar.bankingapp.account.service.AccountService;
import com.cezar.bankingapp.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private AccountService accountService;
    private TransactionService transactionService;

    @Autowired
    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping("/add-account/{customerNumber}")
    public ResponseEntity<?> addNewAccount(@RequestBody AccountDAO accountDAO, @PathVariable Long customerNumber){
        return accountService.addNewAccount(accountDAO,customerNumber);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getByAccountNumber(@PathVariable Long accountNumber){
        return accountService.findByAccountNumber(accountNumber);
    }
}
