package com.shubham.bankingapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToOne(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonManagedReference
    private AccountOpening accountOpening;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountOpening getAccountOpening() {
        return accountOpening;
    }

    public void setAccountOpening(AccountOpening accountOpening) {
        this.accountOpening = accountOpening;
        if (accountOpening != null) {
            accountOpening.setCustomer(this);
        }
    }
}
