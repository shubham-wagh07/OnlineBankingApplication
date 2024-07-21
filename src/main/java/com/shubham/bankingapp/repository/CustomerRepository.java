package com.shubham.bankingapp.repository;

import com.shubham.bankingapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);
    Customer findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.accountOpening WHERE c.email = :email")
    Customer findByEmailWithAccountOpening(String email);
}
