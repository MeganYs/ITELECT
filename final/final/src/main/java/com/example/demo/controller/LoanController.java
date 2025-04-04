package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Loan;
import com.example.demo.service.LoanService;

import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService service;

    // Display all loans
    @GetMapping
    public String listLoans(Model model) {
        List<Loan> loans = service.getAllLoans();
        model.addAttribute("loans", loans);  // Ensure loans are passed to the view
        return "loan/index";  // Render the loan list
    }


    // Display the form to create a new loan
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("loan", new Loan());
        return "loan/create";  // This will render the create loan form
    }

    // Save a new loan
    @PostMapping
    public String saveLoan(@ModelAttribute Loan loan) {
        service.saveLoan(loan);
        return "redirect:/loans";  // Redirect to the loan list
    }

    // Delete a loan by its ID
    @GetMapping("/delete/{id}")
    public String deleteLoan(@PathVariable Long id) {
        service.deleteLoan(id);
        return "redirect:/loans";  // Redirect to the loan list after deletion
    }

    // Update the loan status (e.g., approve or reject)
 // Update the loan status (e.g., approve or reject)
    @GetMapping("/updateStatus/{id}")
    public String updateLoanStatus(@PathVariable Long id, @RequestParam String status) {
        Loan loan = service.getLoanById(id);  // Use the correct service bean
        if (loan != null) {
            loan.setStatus(status); // Update the loan status
            service.saveLoan(loan); // Save the updated loan
        }
        return "redirect:/loans"; // Redirect to the loan list after updating status
    }
}




