package com.example.demo.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "loans")
public class Loan {
    
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
	@SequenceGenerator(name = "seq_gen", sequenceName = "SEQ_NAME", allocationSize = 1)
	private Long id;

    
    private BigDecimal amount;
    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    @ManyToOne 
    @JoinColumn(name = "borrower_id")
    private Borrower borrower;
    
    @Column(name = "loan_month")
    private Integer month;


    public Loan() {}

    public Loan(Borrower borrower, BigDecimal amount, String status, Date dueDate, int month) {
        this.borrower = borrower;
        this.amount = amount;
        this.status = status;
        this.dueDate = dueDate;
        this.month = month;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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
    
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
    
    @Transient
    public BigDecimal getMonthlyInstallment() {
        if (this.amount == null || this.month == null || this.month == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal interestRate = switch (this.month) {
            case 3 -> BigDecimal.valueOf(0.10);
            case 6 -> BigDecimal.valueOf(0.20);
            case 12 -> BigDecimal.valueOf(0.30);
            default -> BigDecimal.ZERO;
        };

        BigDecimal totalAmountWithInterest = this.amount.add(this.amount.multiply(interestRate));
        return totalAmountWithInterest.divide(BigDecimal.valueOf(this.month), 2, BigDecimal.ROUND_HALF_UP);
    }

    
}
