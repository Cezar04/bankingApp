package com.cezar.bankingapp.transaction.CustomerAccountReference;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OperationOnAccountDAO {


    private Long AccountNumber;
    private Double transferAmount;
}
