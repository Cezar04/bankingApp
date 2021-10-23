package com.cezar.bankingapp.CustomerAccountReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferDetailsDAO {

    private Long fromAccountNumber;
    private Long toAccountNumber;
    private Double transferAmount;
}
