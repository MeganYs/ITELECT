package com.example.demo.controller;


import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Borrower;
import com.example.demo.entity.Loan;
import com.example.demo.service.BorrowerService;
import com.example.demo.service.LoanService;

@Controller
@RequestMapping("/borrow")
public class BorrowConttroller {

    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    private LoanService loanService;

    // Show borrower creation form
    @GetMapping("/borrow")
    public String showCreateForm(Model model) {
        model.addAttribute("borrower", new Borrower());
        return "loan/borrow";  // Return the form for creating a borrower
    }

    @PostMapping
    public String saveBorrower(@ModelAttribute Borrower borrower) {
        Borrower savedBorrower = borrowerService.saveBorrower(borrower);

        // Create a new loan for the saved borrower
        Loan loan = new Loan();
        loan.setBorrower(savedBorrower);  // Set the borrower for the loan
        loan.setAmount(BigDecimal.valueOf(borrower.getAmount()));  // Set the loan amount (from the borrower)
        loan.setStatus("Pending");  // Default status for the loan
        loan.setDueDate(new Date());  // Set the current date as due date (you can modify this as per your logic)

        loanService.saveLoan(loan);  // Save the loan in the database

        return "redirect:/loans";  // Redirect to the loan list page after saving the borrower and loan
    }



}