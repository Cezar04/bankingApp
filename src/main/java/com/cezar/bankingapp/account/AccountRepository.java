package com.cezar.bankingapp.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, UUID> {
    AccountModel findAccountModelByAccountNumber(String accountNumber);
    AccountModel findAccountModelByFistName(String firstName);
}
