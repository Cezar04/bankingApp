package com.cezar.bankingapp.helper;

import com.cezar.bankingapp.transaction.CustomerAccountReference.TransferDetailsDAO;
import com.cezar.bankingapp.account.Account;
import com.cezar.bankingapp.account.AccountDAO;
import com.cezar.bankingapp.address.Address;
import com.cezar.bankingapp.address.AddressDAO;
import com.cezar.bankingapp.customer.Customer;
import com.cezar.bankingapp.customer.CustomerDAO;
import com.cezar.bankingapp.transaction.Transaction;
import com.cezar.bankingapp.transaction.TransactionDAO;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BankingServiceHelper {
    public CustomerDAO convertToCustomerDAO(Customer customer){
        return CustomerDAO.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .customerNumber(customer.getCustomerNumber())
                .address(convertAddressToAddressDAO(customer.getCustomerAddress()))
                .build();
    }

    public Customer convertToCostumerEntity(CustomerDAO customerDAO){
        return Customer.builder()
                .firstName(customerDAO.getFirstName())
                .lastName(customerDAO.getLastName())
                .email(customerDAO.getEmail())
                .phoneNumber(customerDAO.getPhoneNumber())
                .customerNumber(customerDAO.getCustomerNumber())
                .customerAddress(convertAddressToEntity(customerDAO.getAddress()))
                .build();
    }

    public AddressDAO convertAddressToAddressDAO(Address address){
        return AddressDAO.builder()
                .address(address.getAddress())
                .city(address.getCity())
                .country(address.getCountry())
                .build();
    }

    public Address convertAddressToEntity(AddressDAO addressDAO){
        return  Address.builder()
                .city(addressDAO.getCity())
                .country(addressDAO.getCountry())
                .address(addressDAO.getAddress())
                .build();
    }

    public AccountDAO convertToAccountDAO(Account account){
        return AccountDAO.builder()
                .accountBalance(account.getAccountBalance())
                .accountNumber(account.getAccountNumber())
                .build();
    }

    public Account convertToAccountEntity(AccountDAO accountDAO){
        return  Account.builder()
                .accountBalance(accountDAO.getAccountBalance())
                .accountNumber(accountDAO.getAccountNumber())
                .build();

    }

    public TransactionDAO convertToTransactionDAO(Transaction transaction){
        return TransactionDAO.builder()
                .transactionAmount(transaction.getTransactionAmount())
                .transactionDateTime(transaction.getTransactionDateTime())
                .accountNumber(transaction.getAccountNumber())
                .transactionType(transaction.getTransactionType())
                .build();
    }

    public Transaction convertTransactionToEntity(TransactionDAO transactionDAO){
        return Transaction.builder()
                .accountNumber(transactionDAO.getAccountNumber())
                .transactionAmount(transactionDAO.getTransactionAmount())
                .transactionType(transactionDAO.getTransactionType())
                .build();
    }

    public Transaction createTransaction(TransferDetailsDAO transferDetailsDAO, Long accountNUmber, String transactionType){
        return Transaction.builder()
                .accountNumber(accountNUmber)
                .transactionAmount(transferDetailsDAO.getTransferAmount())
                .transactionType(transactionType)
                .transactionDateTime(new Date())
                .build();
    }


}
