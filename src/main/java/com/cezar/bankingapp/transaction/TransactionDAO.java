package com.cezar.bankingapp.transaction;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionDAO {

    private Long accountNumber;
    private Date transactionDateTime;
    private Double transactionAmount;
    private String transactionType;
}
