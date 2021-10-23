package com.cezar.bankingapp.account;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountDAO {

    private Long accountNumber;
    private Double accountBalance;
    private Date accountCreated;
}
