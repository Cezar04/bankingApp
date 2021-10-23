package com.cezar.bankingapp.customer.service;

import com.cezar.bankingapp.customer.Customer;
import com.cezar.bankingapp.customer.CustomerDAO;
import com.cezar.bankingapp.customer.CustomerRepository;
import com.cezar.bankingapp.helper.BankingServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
    private CustomerRepository customerRepository;
    private BankingServiceHelper bankingServiceHelper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, BankingServiceHelper bankingServiceHelper) {
        this.customerRepository = customerRepository;
        this.bankingServiceHelper = bankingServiceHelper;
    }




    @Override
    public List<CustomerDAO> findAll() {
        List<CustomerDAO> allCustomers = new ArrayList<>();
        Iterable<Customer> customers = customerRepository.findAll();

        customers.forEach(customer -> {
            allCustomers.add(bankingServiceHelper.convertToCustomerDAO(customer));
        });

        return allCustomers;
    }

    @Override
    public ResponseEntity<?> addCustomer(CustomerDAO customerDAO) {
        Customer customer = bankingServiceHelper.convertToCostumerEntity(customerDAO);
        customer.setCreateDateTime(new Date());
        customerRepository.save(customer);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @Override
    public CustomerDAO findByCustomerNumber(Long customerNumber) {
        Optional<Customer> customerOptional = customerRepository.findByCustomerNumber(customerNumber);
        return customerOptional.map(customer -> bankingServiceHelper.convertToCustomerDAO(customer)).orElse(null);
    }

    @Override
    public CustomerDAO updateCustomer(CustomerDAO customerDAO, Long customerNumber) {
        return null;
    }

    @Override
    public CustomerDAO deleteCustomer(Long customerNumber) {
        return null;
    }
}
