package com.tfg.account.infrastructure.outbound.persistence.address;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "addresses")
@Data
public class JpaAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
