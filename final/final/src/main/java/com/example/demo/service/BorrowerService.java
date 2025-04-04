package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Borrower;
import com.example.demo.repository.BorrowerRepository;

import java.util.List;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository repository;

    public List<Borrower> getAllBorrowers() {
        return repository.findAll();
    }

    public Borrower saveBorrower(Borrower borrower) {
        return repository.save(borrower);
    }
    
    public Borrower getBorrowerById(Long id) {
        return repository.findById(id).orElse(null);
    }
}

