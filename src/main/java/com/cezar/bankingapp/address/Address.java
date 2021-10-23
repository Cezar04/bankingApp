package com.cezar.bankingapp.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Type(type = "pg-uuid")
    @Column(name="ADDR_ID")
    private UUID id;

    private String address;
    private String city;
    private String country;


}
