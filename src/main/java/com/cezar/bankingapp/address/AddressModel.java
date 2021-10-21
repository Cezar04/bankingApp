package com.cezar.bankingapp.address;

import com.cezar.bankingapp.account.AccountModel;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class AddressModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    @Column(name = "address_id")
    private UUID id;

   @OneToOne(mappedBy = "address")
    AccountModel account;
}
