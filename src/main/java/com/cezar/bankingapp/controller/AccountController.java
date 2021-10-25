package com.cezar.bankingapp.controller;

import com.cezar.bankingapp.account.AccountDAO;
import com.cezar.bankingapp.account.service.AccountService;
import com.cezar.bankingapp.transaction.OperationOnAccountDAO;
import com.cezar.bankingapp.transaction.TransferDetailsDAO;
import com.cezar.bankingapp.transaction.TransactionDAO;
import com.cezar.bankingapp.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PostMapping("/add-account/{customerId}")
    public ResponseEntity<?> addNewAccount(@RequestBody AccountDAO accountDAO, @PathVariable UUID customerId){
        return accountService.addNewAccount(accountDAO,customerId);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getByAccountNumber(@PathVariable Long accountNumber){
        return accountService.findByAccountNumber(accountNumber);
    }

    @PutMapping("/transfer/{customerId}")
    public ResponseEntity<?> transferDetails(@RequestBody TransferDetailsDAO transferDetailsDAO,@PathVariable UUID customerId){
        return transactionService.transferDetails(transferDetailsDAO,customerId);
    }

    @GetMapping(path = "/transactions/{accountNumber}")
    public List<TransactionDAO> getTransactionByAccountNumber(@PathVariable Long accountNumber) {

        return transactionService.findTransactionByAccountNumber(accountNumber);
    }

    @PostMapping("/deposit-to-account/{customerId}")
    public ResponseEntity<?> depositToAccount(@RequestBody OperationOnAccountDAO operationOnAccountDAO, @PathVariable UUID customerId){
        return  transactionService.deposit(operationOnAccountDAO,customerId);
    }

    @PostMapping("/withdraw-to-account/{customerId}")
    public ResponseEntity<?> withdrawToAccount(@RequestBody OperationOnAccountDAO operationOnAccountDAO, @PathVariable UUID customerId){
        return  transactionService.withdraw(operationOnAccountDAO,customerId);
    }
}
