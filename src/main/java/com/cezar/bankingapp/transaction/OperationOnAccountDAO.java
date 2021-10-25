package com.cezar.bankingapp.transaction;

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
