package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Borrower;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    List<Borrower> findByName(String name);
    List<Borrower> findByEmail(String email);
    List<Borrower> findByPhoneNumber(String phoneNumber);
	
}
