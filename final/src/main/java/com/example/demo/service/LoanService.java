package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Loan;
import com.example.demo.repository.LoanRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    
 
    public BigDecimal calculateSimpleInterest(BigDecimal principal, BigDecimal annualRate, BigDecimal termInYears) {
        return principal.multiply(annualRate).multiply(termInYears);
    }

    
    public BigDecimal calculateCompoundInterest(BigDecimal principal, BigDecimal annualRate, int compoundingPerYear, BigDecimal termInYears) {
        BigDecimal n = BigDecimal.valueOf(compoundingPerYear);
        BigDecimal ratePerPeriod = annualRate.divide(n, 10, RoundingMode.HALF_UP);
        BigDecimal exponent = termInYears.multiply(n);
        BigDecimal base = BigDecimal.ONE.add(ratePerPeriod);

        
        double baseDouble = base.doubleValue();
        double expDouble = exponent.doubleValue();
        BigDecimal compoundFactor = BigDecimal.valueOf(Math.pow(baseDouble, expDouble));

        BigDecimal amount = principal.multiply(compoundFactor);
        return amount.subtract(principal); 
    }
    
    public BigDecimal getMonthlyInstallment(Long loanId) {
        Optional<Loan> loanOpt = repository.findById(loanId);
        if (loanOpt.isPresent()) {
            Loan loan = loanOpt.get();

            int term = loan.getMonth(); 
            if (term > 0) {
                return loan.getAmount().divide(BigDecimal.valueOf(term), 2, BigDecimal.ROUND_HALF_UP);
            }
        }
        return BigDecimal.ZERO;
    }
    
}


