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

    @GetMapping
    public String listLoans(Model model) {
        List<Loan> loans = service.getAllLoans();
        model.addAttribute("loans", loans);
        return "loan/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("loan", new Loan());
        return "loan/create";
    }

    @PostMapping
    public String saveLoan(@ModelAttribute Loan loan) {
        service.saveLoan(loan);
        return "redirect:/loans";
    }

    @GetMapping("/delete/{id}")
    public String deleteLoan(@PathVariable Long id) {
        service.deleteLoan(id);
        return "redirect:/loans";
    }
}

