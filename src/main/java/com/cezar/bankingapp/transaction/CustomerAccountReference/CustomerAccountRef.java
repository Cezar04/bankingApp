package com.cezar.bankingapp.transaction.CustomerAccountReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerAccountRef {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Type(type = "pg-uuid")
    @Column(name="CUST_ACC_REF_ID")
    private UUID id;

    private Long accountNumber;
    private UUID customerNumber;
}
