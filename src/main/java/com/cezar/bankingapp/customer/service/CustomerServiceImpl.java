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
    public ResponseEntity<?> updateCustomer(CustomerDAO customerDAO, Long customerNumber) {
        Optional<Customer> managedCustomerEntityOptional = customerRepository.findByCustomerNumber(customerNumber);
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("customer "+ customerNumber+" not fount");
        }
    }

    @Override
    public ResponseEntity<?> deleteCustomer(Long customerNumber) {

        Optional<Customer> managedCustomerEntityOptional = customerRepository.findByCustomerNumber(customerNumber);

        if (managedCustomerEntityOptional.isPresent()){
            Customer managedCustomerEntity = managedCustomerEntityOptional.get();
            customerRepository.delete(managedCustomerEntity);
          return new ResponseEntity<>(managedCustomerEntity, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
