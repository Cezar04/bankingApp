package com.cezar.bankingapp.account;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountModel save(AccountModel accountModel);
    AccountModel getAccount(UUID id);
    List<AccountModel> getAllAccounts();

    AccountModel findAccountByFirstName(String firstName);
//    AccountModel updateAccount(AccountModel accountModel);


}
