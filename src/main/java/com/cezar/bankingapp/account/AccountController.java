package com.cezar.bankingapp.account;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountServiceImpl accountService;

    @Autowired
    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account-byId/{id}")
    public AccountModel getAccount(@PathVariable UUID id){
        return accountService.getAccount(id);
    }

    @GetMapping("all-accounts")
    public List<AccountModel> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PostMapping
    public AccountModel addAccount(@RequestBody AccountModel accountModel){
        return accountService.save(accountModel);
    }
}
