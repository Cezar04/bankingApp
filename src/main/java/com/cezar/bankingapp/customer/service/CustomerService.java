package com.cezar.bankingapp.customer.service;

import com.cezar.bankingapp.customer.CustomerDAO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDAO>findAll();
    ResponseEntity<?> addCustomer(CustomerDAO customerDAO);
    CustomerDAO findByCustomerId(UUID customerId);
    ResponseEntity<?> updateCustomerTest(CustomerDAO customerDAO,UUID customerID);
    ResponseEntity<?> deleteCustomer(UUID customerId);

}
