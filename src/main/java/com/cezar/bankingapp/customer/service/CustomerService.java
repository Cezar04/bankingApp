package com.cezar.bankingapp.customer.service;

import com.cezar.bankingapp.customer.CustomerDAO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    List<CustomerDAO>findAll();
   ResponseEntity<?> addCustomer(CustomerDAO customerDAO);
    CustomerDAO findByCustomerNumber(Long customerNumber);
    ResponseEntity<?> updateCustomer(CustomerDAO customerDAO,Long customerNumber);
//    aici posibil sa fie o eroare
    ResponseEntity<?> deleteCustomer(Long customerNumber);

}
