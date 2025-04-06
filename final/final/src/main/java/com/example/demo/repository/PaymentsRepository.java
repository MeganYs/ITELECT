package com.example.demo.repository;

import com.example.demo.entity.Payments;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
	@Query("SELECT p FROM Payments p WHERE p.borrower.id = :borrowerId")
	List<Payments> findByBorrowerId(@Param("borrowerId") Long borrowerId);

}
