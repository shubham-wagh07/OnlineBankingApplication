package com.shubham.bankingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;
import com.shubham.bankingapp.model.AccountOpening;
import com.shubham.bankingapp.model.Customer;
import com.shubham.bankingapp.model.Transaction;

import java.util.Optional;

@Repository
public interface AccountOpeningRepository extends JpaRepository<AccountOpening, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    AccountOpening findFirstByOrderByIdDesc();
    AccountOpening findByEmail(String email);
    Optional<AccountOpening> findByCustomer(Customer customer);
}
