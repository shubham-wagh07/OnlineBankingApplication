package com.shubham.bankingapp.repository;

import com.shubham.bankingapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountOpeningId(Long accountOpeningId);
}
