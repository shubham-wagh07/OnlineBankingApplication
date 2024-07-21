package com.shubham.bankingapp.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "account_opening")
public class AccountOpening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private Date dateOfBirth;
    private String address;

    @Column(unique = true, length = 10)
    private String phoneNumber;
    private Double balance;
    @Column(unique = true)
    private String email;

    private String accountType;
    private Double initialDeposit;

    @Column(name = "preferred_branch")
    private String preferredBranch;

    @Column(name = "nominee_details")
    private String nomineeDetails;

    @Column(unique = true, nullable = false)
    private String referenceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public AccountOpening() {
        this.referenceNumber = generateReferenceNumber();
    }

    
    public Double getBalance() {
        return balance;
    }

    
    public void setBalance(Double balance) {
        this.balance = balance;
    }
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(Double initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

    public String getPreferredBranch() {
        return preferredBranch;
    }

    public void setPreferredBranch(String preferredBranch) {
        this.preferredBranch = preferredBranch;
    }

    public String getNomineeDetails() {
        return nomineeDetails;
    }

    public void setNomineeDetails(String nomineeDetails) {
        this.nomineeDetails = nomineeDetails;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    private String generateReferenceNumber() {
        return UUID.randomUUID().toString();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
