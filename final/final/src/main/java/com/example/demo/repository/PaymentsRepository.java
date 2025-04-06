package com.example.demo.repository;

import com.example.demo.entity.Payments;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
	List<Payments> findByBorrowerId(Long borrowerId);
    List<Payments> findByStatus(String status);

}
