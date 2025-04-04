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
}



