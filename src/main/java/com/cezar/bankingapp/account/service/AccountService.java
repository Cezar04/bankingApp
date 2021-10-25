package com.cezar.bankingapp.account.service;

import com.cezar.bankingapp.account.AccountDAO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AccountService {
    ResponseEntity<?> addNewAccount(AccountDAO accountDAO, UUID customerId);
    ResponseEntity<?> findByAccountNumber(Long accountNumber);
}
