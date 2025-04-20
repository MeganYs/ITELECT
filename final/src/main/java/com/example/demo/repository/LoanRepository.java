package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {
	
    List<Loan> findByBorrower_Name(String borrowerName);
    List<Loan> findByAmount(double amount);
    List<Loan> findByStatus(String status);
    List<Loan> findByDueDate(Date dueDate);
    List<Loan> findByBorrowerId(Long borrowerId);
    

}
