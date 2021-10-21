package com.cezar.bankingapp.account;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountModel save(AccountModel accountModel) {
        return accountRepository.save(accountModel);
    }

    @Override
    public AccountModel getAccount(UUID id) {
        return accountRepository.getById(id);
    }

    @Override
    public List<AccountModel> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public AccountModel findAccountByFirstName(String firstName) {
        return accountRepository.findAccountModelByFistName(firstName);
    }

}
