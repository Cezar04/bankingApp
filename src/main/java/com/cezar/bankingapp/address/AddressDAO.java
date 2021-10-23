package com.cezar.bankingapp.address;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressDAO {


    private String address;
    private String city;
    private String country;
}
