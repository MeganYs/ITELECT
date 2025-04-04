package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Loan;
import com.example.demo.repository.LoanRepository;

import jakarta.persistence.Column;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository repository;

    // Retrieve all loans
    public List<Loan> getAllLoans() {
        return repository.findAll();
    }

    // Retrieve loan by ID
    public Loan getLoanById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Save a new or updated loan
    public Loan saveLoan(Loan loan) {
        return repository.save(loan);
    }

    // Delete loan by ID
    public void deleteLoan(Long id) {
        repository.deleteById(id);
    }
}


