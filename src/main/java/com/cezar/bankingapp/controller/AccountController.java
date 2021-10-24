package com.cezar.bankingapp.controller;

import com.cezar.bankingapp.account.AccountDAO;
import com.cezar.bankingapp.account.service.AccountService;
import com.cezar.bankingapp.transaction.CustomerAccountReference.OperationOnAccountDAO;
import com.cezar.bankingapp.transaction.CustomerAccountReference.TransferDetailsDAO;
import com.cezar.bankingapp.transaction.TransactionDAO;
import com.cezar.bankingapp.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/transfer/{customerNumber}")
    public ResponseEntity<?> transferDetails(@RequestBody TransferDetailsDAO transferDetailsDAO,@PathVariable Long customerNumber){
        return transactionService.transferDetails(transferDetailsDAO,customerNumber);
    }

    @GetMapping(path = "/transactions/{accountNumber}")
    public List<TransactionDAO> getTransactionByAccountNumber(@PathVariable Long accountNumber) {

        return transactionService.findTransactionByAccountNumber(accountNumber);
    }

    @PostMapping("/add-to-account/{customerNumber}")
    public ResponseEntity<?> depositToAccount(@RequestBody OperationOnAccountDAO operationOnAccountDAO, @PathVariable Long customerNumber){
        return  transactionService.addToAccount(operationOnAccountDAO,customerNumber);
    }
}
