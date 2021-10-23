package com.cezar.bankingapp.transaction.service;
import com.cezar.bankingapp.transaction.CustomerAccountReference.TransferDetailsDAO;
import com.cezar.bankingapp.transaction.TransactionDAO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {
    ResponseEntity<?>transferDetails(TransferDetailsDAO transferDetailsDAO,Long customerNumber);
    List<TransactionDAO> findTransactionByAccountNumber(Long accountNumber);
}
