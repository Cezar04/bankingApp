package com.cezar.bankingapp.customer;

import com.cezar.bankingapp.address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name="CUST_ID")

    private UUID id;

    private String firstName;

    private String lastName;
//customer number poate fi inlocuit cu id
    private Long customerNumber;

    @Email
    private String email;

    private String phoneNumber;


    @ManyToOne(cascade=CascadeType.ALL)
    private Address customerAddress;

    @Temporal(TemporalType.TIME)
    private Date createDateTime;

    @Temporal(TemporalType.TIME)
    private Date updateDateTime;

}