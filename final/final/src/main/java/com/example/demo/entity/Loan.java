package com.example.demo.entity;
import jakarta.persistence.*;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "loans")
public class Loan {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String borrowerName;
    private double amount;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
    
    public Loan() {}

    public Loan(String borrowerName, double amount, String status, Date dueDate) {
        this.borrowerName = borrowerName;
        this.amount = amount;
        this.status = status;
        this.dueDate = dueDate;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
    
    
}
