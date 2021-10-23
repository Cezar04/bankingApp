package com.cezar.bankingapp.CustomerAccountReference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerAccountRefRepository extends JpaRepository<CustomerAccountRef, UUID> {
}
