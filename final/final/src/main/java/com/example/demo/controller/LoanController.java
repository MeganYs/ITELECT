package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Borrower;
import com.example.demo.entity.Loan;
import com.example.demo.service.BorrowerService;
import com.example.demo.service.LoanService;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BorrowerService borrowerService;

    // Display all loans
    @GetMapping
    public String listLoans(Model model) {
        List<Loan> loans = loanService.getAllLoans();

        // Create a map of loanId -> monthly installment
        Map<Long, BigDecimal> monthlyInstallments = new HashMap<>();

        for (Loan loan : loans) {
            BigDecimal installment = calculateMonthlyInstallment(loan.getAmount(), loan.getMonth());
            monthlyInstallments.put(loan.getId(), installment);
        }

        model.addAttribute("loans", loans);
        model.addAttribute("installments", monthlyInstallments);

        return "loan/index";
    }

    // Monthly installment calculation logic
    private BigDecimal calculateMonthlyInstallment(BigDecimal amount, Integer months) {
        if (amount == null || months == null || months == 0) return BigDecimal.ZERO;

        BigDecimal interestRate = switch (months) {
            case 3 -> BigDecimal.valueOf(0.10);
            case 6 -> BigDecimal.valueOf(0.20);
            case 12 -> BigDecimal.valueOf(0.30);
            default -> BigDecimal.ZERO;
        };

        BigDecimal totalWithInterest = amount.multiply(BigDecimal.ONE.add(interestRate));
        return totalWithInterest.divide(BigDecimal.valueOf(months), 2, BigDecimal.ROUND_HALF_UP);
    }

    // Display form to create new loan
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("loan", new Loan());
        model.addAttribute("borrowers", borrowerService.getAllBorrowers());
        return "loan/create";
    }

    @PostMapping
    public String saveLoan(@RequestParam Long borrowerId, @ModelAttribute Loan loan) {
        Borrower borrower = borrowerService.getBorrowerById(borrowerId);
        if (borrower != null) {
            loan.setBorrower(borrower);  // Link borrower to the loan
            loanService.saveLoan(loan);
        } else {
            return "redirect:/loans?error=BorrowerNotFound";
        }
        return "redirect:/loans";
    }

    // Delete loan
    @GetMapping("/delete/{id}")
    public String deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return "redirect:/loans";
    }

    // Show form to edit loan by ID
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Loan loan = loanService.getLoanById(id);
        if (loan != null) {
            model.addAttribute("loan", loan);
            model.addAttribute("borrowers", borrowerService.getAllBorrowers());
            return "loan/update";
        }
        return "redirect:/loans";
    }

    @PostMapping("/update/{id}")
    public String updateLoan(@PathVariable Long id, @RequestParam Long borrowerId, @ModelAttribute Loan loan) {
        Loan existingLoan = loanService.getLoanById(id);
        Borrower borrower = borrowerService.getBorrowerById(borrowerId);

        if (existingLoan == null || borrower == null) {
            return "redirect:/loans?error=NotFound";
        }

        BigDecimal interestRate = switch (loan.getMonth()) {
            case 3 -> BigDecimal.valueOf(0.10);
            case 6 -> BigDecimal.valueOf(0.20);
            case 12 -> BigDecimal.valueOf(0.30);
            default -> BigDecimal.ZERO;
        };

        BigDecimal newAmount = existingLoan.getAmount().multiply(BigDecimal.ONE.add(interestRate));

        existingLoan.setMonth(loan.getMonth());
        existingLoan.setAmount(newAmount);
        existingLoan.setBorrower(borrower);

        loanService.saveLoan(existingLoan);

        System.out.println("✅ Loan update complete");
        return "redirect:/loans";
    }
}
