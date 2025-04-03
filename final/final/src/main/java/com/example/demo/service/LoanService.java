package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Loan;
import com.example.demo.repository.LoanRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository repository;

    public List<Loan> getAllLoans() {
        return repository.findAll();
    }

    public Loan getLoanById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Loan saveLoan(Loan loan) {
        return repository.save(loan);
    }

    public void deleteLoan(Long id) {
        repository.deleteById(id);
    }
}

