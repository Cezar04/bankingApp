package com.cezar.bankingapp.transaction.CustomerAccountReference;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransferDetailsDAO {

    private Long fromAccountNumber;
    private Long toAccountNumber;
    private Double transferAmount;
}
