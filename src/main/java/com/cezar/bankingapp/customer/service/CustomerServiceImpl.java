package com.cezar.bankingapp.customer.service;

import com.cezar.bankingapp.address.Address;
import com.cezar.bankingapp.customer.Customer;
import com.cezar.bankingapp.customer.CustomerDAO;
import com.cezar.bankingapp.customer.CustomerRepository;
import com.cezar.bankingapp.helper.BankingServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final BankingServiceHelper bankingServiceHelper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, BankingServiceHelper bankingServiceHelper) {
        this.customerRepository = customerRepository;
        this.bankingServiceHelper = bankingServiceHelper;
    }




    @Override
    public List<CustomerDAO> findAll() {
        List<CustomerDAO> allCustomers = new ArrayList<>();
        Iterable<Customer> customers = customerRepository.findAll();

        customers.forEach(customer -> allCustomers.add(bankingServiceHelper.convertToCustomerDAO(customer)));

        return allCustomers;
    }

    @Override
    public ResponseEntity<?> addCustomer(CustomerDAO customerDAO) {
        Customer customer = bankingServiceHelper.convertToCostumerEntity(customerDAO);
        customer.setCustomerAddress(customer.getCustomerAddress());
        customer.setCreateDateTime(new Date());
        customerRepository.save(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @Override
    public CustomerDAO findByCustomerId(UUID customerId) {

        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        return customerOptional.map(bankingServiceHelper::convertToCustomerDAO).orElse(null);
    }

    @Override
    public ResponseEntity<?> updateCustomerTest(CustomerDAO customerDAO, UUID customerId) {
        Optional<Customer> managedCustomerEntityOptional = customerRepository.findById(customerId);
        Customer unmanagedCustomerEntity = bankingServiceHelper.convertToCostumerEntity(customerDAO);

        if (managedCustomerEntityOptional.isPresent()){
            Customer mangedCustomerEntity = managedCustomerEntityOptional.get();
            if (Optional.ofNullable(unmanagedCustomerEntity.getCustomerAddress()).isPresent()){
                Address managedAddress = mangedCustomerEntity.getCustomerAddress();
                if (managedAddress!= null){
                    managedAddress.setAddress(unmanagedCustomerEntity.getCustomerAddress().getAddress());
                    managedAddress.setCity(unmanagedCustomerEntity.getCustomerAddress().getCity());
                    managedAddress.setCountry(unmanagedCustomerEntity.getCustomerAddress().getCountry());
                } else {
                    mangedCustomerEntity.setCustomerAddress(unmanagedCustomerEntity.getCustomerAddress());
                }
        }
            mangedCustomerEntity.setUpdateDateTime(new Date());
            mangedCustomerEntity.setFirstName(unmanagedCustomerEntity.getFirstName());
            mangedCustomerEntity.setLastName(unmanagedCustomerEntity.getLastName());
            mangedCustomerEntity.setEmail(unmanagedCustomerEntity.getEmail());
            mangedCustomerEntity.setPhoneNumber(unmanagedCustomerEntity.getPhoneNumber());
            customerRepository.save(mangedCustomerEntity);


            return ResponseEntity.status(HttpStatus.OK).body("Customer updated");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("customer "+ customerId+" not fount");
        }
    }

    @Override
    public ResponseEntity<?> deleteCustomer(UUID customerId) {

        Optional<Customer> managedCustomerEntityOptional = customerRepository.findById(customerId);

        if (managedCustomerEntityOptional.isPresent()){
            Customer managedCustomerEntity = managedCustomerEntityOptional.get();
            customerRepository.delete(managedCustomerEntity);
          return new ResponseEntity<>(managedCustomerEntity, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
