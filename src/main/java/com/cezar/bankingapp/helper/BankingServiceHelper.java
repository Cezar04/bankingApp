package com.cezar.bankingapp.helper;

import com.cezar.bankingapp.customer.Customer;
import com.cezar.bankingapp.customer.CustomerDetailsDAO;
import org.springframework.stereotype.Component;

@Component
public class BankingServiceHelper {
    public CustomerDetailsDAO convertToCustomerDAO(Customer customer){
        return CustomerDetailsDAO.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())


                .build();
    }
}
