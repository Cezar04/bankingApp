package com.cezar.bankingapp.controller;


import com.cezar.bankingapp.customer.CustomerDAO;
import com.cezar.bankingapp.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{customerNumber}")
    public CustomerDAO getCustomer(@PathVariable Long customerNumber){
        return customerService.findByCustomerNumber(customerNumber);
    }

    @PutMapping("/edit/{customerNumber}")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDAO customerDAO,@PathVariable Long customerNumber){
        return customerService.updateCustomer(customerDAO,customerNumber);
    }

    @DeleteMapping("/delete/{customerNumber}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerNumber){
        return customerService.deleteCustomer(customerNumber);
    }




}
