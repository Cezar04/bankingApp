package com.cezar.bankingapp.account;

import com.cezar.bankingapp.address.AddressModel;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class AccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    @Column(name = "account_id")
    private UUID id;

    private String accountNumber;

    private String fistName;
    private String lastName;

    //------------ Mapped Column -----------//
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private AddressModel address;
    //-------------------------------------//







}
