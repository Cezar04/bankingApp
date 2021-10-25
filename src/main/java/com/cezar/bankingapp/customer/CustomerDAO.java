package com.cezar.bankingapp.customer;

import com.cezar.bankingapp.address.AddressDAO;
import lombok.*;

import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDAO {
    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String phoneNumber;

    private AddressDAO address;

}
