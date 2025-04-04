package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Borrower;
import com.example.demo.entity.Loan;
import com.example.demo.service.BorrowerService;
import com.example.demo.service.LoanService;

import java.util.List;

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
        model.addAttribute("loans", loans);
        return "loan/index";
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
            // Handle case when borrower is not found
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
            model.addAttribute("borrowers", borrowerService.getAllBorrowers());  // Add borrowers to the form
            return "loan/update";  // Return the update form
        }
        return "redirect:/loans";  // If loan is not found, redirect to loans page
    }

    // Handle the update loan request
    @PostMapping("/update/{id}")
    public String updateLoan(@PathVariable Long id, @RequestParam Long borrowerId, @ModelAttribute Loan loan) {
        Borrower borrower = borrowerService.getBorrowerById(borrowerId);
        if (borrower != null) {
            loan.setId(id);  // Ensure we update the correct loan
            loan.setBorrower(borrower);  // Set the borrower
            loanService.saveLoan(loan);  // Save the updated loan
        } else {
            return "redirect:/loans?error=BorrowerNotFound";
        }
        return "redirect:/loans";  // Redirect to the loan list after update
    }

    
    
    
}



