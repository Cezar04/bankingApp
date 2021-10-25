package com.cezar.bankingapp.transaction.service;
import com.cezar.bankingapp.transaction.OperationOnAccountDAO;
import com.cezar.bankingapp.transaction.TransferDetailsDAO;
import com.cezar.bankingapp.transaction.TransactionDAO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    ResponseEntity<?>transferDetails(TransferDetailsDAO transferDetailsDAO, UUID customerId);
    List<TransactionDAO> findTransactionByAccountNumber(Long accountNumber);
    ResponseEntity<?>deposit(OperationOnAccountDAO operationOnAccountDAO, UUID customerId);
    ResponseEntity<?>withdraw(OperationOnAccountDAO operationOnAccountDAO, UUID customerId);
}
