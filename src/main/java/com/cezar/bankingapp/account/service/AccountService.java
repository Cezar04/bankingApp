package com.cezar.bankingapp.account.service;

import com.cezar.bankingapp.account.AccountDAO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    ResponseEntity<?> addNewAccount(AccountDAO accountDAO, Long customerNumber);
    ResponseEntity<?> findByAccountNumber(Long accountNumber);
}
