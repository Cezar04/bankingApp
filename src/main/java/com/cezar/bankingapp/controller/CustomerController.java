package com.cezar.bankingapp.controller;


import com.cezar.bankingapp.customer.CustomerDAO;
import com.cezar.bankingapp.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/all")
    public List<CustomerDAO> getAllCustomers(){
      return customerService.findAll();
    }

    @PostMapping("/add-customer")
    private ResponseEntity<?> addCustomer(@RequestBody CustomerDAO customer){
        return customerService.addCustomer(customer);
    }

    @GetMapping("/{customerId}")
    public CustomerDAO getCustomer(@PathVariable UUID customerId){
        return customerService.findByCustomerId(customerId);
    }


    @PutMapping("/edit/{customerId}")
    public ResponseEntity<?> updateCustomerTest(@RequestBody CustomerDAO customerDAO,@PathVariable UUID customerId){
        return customerService.updateCustomerTest(customerDAO,customerId);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable UUID customerId){
        return customerService.deleteCustomer(customerId);
    }




}
