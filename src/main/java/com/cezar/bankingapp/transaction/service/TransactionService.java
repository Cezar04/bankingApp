package com.cezar.bankingapp.transaction.service;
import com.cezar.bankingapp.account.AccountDAO;
import com.cezar.bankingapp.transaction.CustomerAccountReference.OperationOnAccountDAO;
import com.cezar.bankingapp.transaction.CustomerAccountReference.TransferDetailsDAO;
import com.cezar.bankingapp.transaction.TransactionDAO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
//    ResponseEntity<?>transferDetails(TransferDetailsDAO transferDetailsDAO,Long customerNumber);
    ResponseEntity<?>transferDetails(TransferDetailsDAO transferDetailsDAO, UUID customerId);
    List<TransactionDAO> findTransactionByAccountNumber(Long accountNumber);
//    ResponseEntity<?>deposit(OperationOnAccountDAO operationOnAccountDAO, Long customerNumber);
    ResponseEntity<?>deposit(OperationOnAccountDAO operationOnAccountDAO, UUID customerId);
    ResponseEntity<?>withdraw(OperationOnAccountDAO operationOnAccountDAO, UUID customerId);
}
