package com.example.demo.controller;

import com.example.demo.entity.Borrower;
import com.example.demo.entity.Loan;
import com.example.demo.entity.Payments;
import com.example.demo.service.BorrowerService;
import com.example.demo.service.LoanService;
import com.example.demo.service.PaymentsService;
import com.example.demo.repository.BorrowerRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.PaymentsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/payments")
public class PaymentsController {

    @Autowired
    private PaymentsRepository paymentrepository;

    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private LoanRepository loanRepository;

    // ✅ Only one @GetMapping("/") to avoid ambiguity
    @GetMapping
    public String showPayments(@RequestParam(value = "borrowerId", required = false) Long borrowerId, Model model) {
        List<Borrower> borrowers = borrowerService.getAllBorrowers();
        model.addAttribute("borrowers", borrowers);

        List<Payments> payments;
        if (borrowerId != null) {
            model.addAttribute("selectedBorrowerId", borrowerId);
            payments = paymentsService.findByBorrowerId(borrowerId);
        } else {
            payments = paymentsService.getAllPayments(); // or new ArrayList<>()
        }
        model.addAttribute("payments", payments);

        return "loan/payments";
    }


    @GetMapping("/{id}")
    public String viewPayment(@PathVariable Long id, Model model) {
        Optional<Payments> payment = paymentsService.getPaymentById(id);
        payment.ifPresent(p -> model.addAttribute("payment", p));
        return "payment_details";
    }

    @PostMapping("/add")
    public String addPayment(
        @RequestParam Long borrowerId,
        @RequestParam Long loanId,
        @RequestParam BigDecimal amount,
        @RequestParam String status) {
        
        Optional<Borrower> borrower = borrowerRepository.findById(borrowerId);
        Optional<Loan> loan = loanRepository.findById(loanId);

        if (borrower.isPresent() && loan.isPresent()) {
            Payments payment = new Payments(borrower.get(), loan.get(), amount, LocalDateTime.now(), status);
            paymentsService.savePayment(payment);
        }

        return "redirect:/payments";
    }

    @PostMapping("/save")
    public String savePayment(@RequestParam Long borrowerId,
                              @RequestParam Long loanId,
                              @RequestParam BigDecimal amount) {

        Optional<Loan> loanOpt = loanRepository.findById(loanId);
        Optional<Borrower> borrowerOpt = borrowerRepository.findById(borrowerId);

        if (loanOpt.isPresent() && borrowerOpt.isPresent()) {
            Loan loan = loanOpt.get();

            Payments payment = new Payments();
            payment.setLoan(loan);
            payment.setBorrower(borrowerOpt.get());
            payment.setAmount(amount);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setStatus("Paid");

            paymentsService.savePayment(payment);

            BigDecimal currentBalance = loan.getAmount() != null ? loan.getAmount() : BigDecimal.ZERO;
            BigDecimal newBalance = currentBalance.subtract(amount);

            loan.setAmount(newBalance.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : newBalance);
            loan.setStatus(newBalance.compareTo(BigDecimal.ZERO) <= 0 ? "Paid" : loan.getStatus());

            loanRepository.save(loan);
        }

        return "redirect:/payments";
    }

    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id) {
        paymentsService.deletePayment(id);
        return "redirect:/payments";
    }

    @GetMapping("/loans")
    @ResponseBody
    public List<Loan> getLoansByBorrower(@RequestParam("borrowerId") Long borrowerId) {
        return loanRepository.findByBorrowerId(borrowerId);
    }

    @GetMapping("/installment")
    @ResponseBody
    public Map<String, BigDecimal> getInstallment(@RequestParam Long loanId) {
        BigDecimal installment = loanService.getMonthlyInstallment(loanId); // Make sure this method exists
        return Map.of("installment", installment);
    }

    @GetMapping("/payments/by-borrower")
    @ResponseBody
    public List<Payments> getPaymentsByBorrower(@RequestParam Long borrowerId) {
        return paymentrepository.findByBorrowerId(borrowerId);
    }

    @GetMapping("/form")
    public String showPaymentsForm(Model model) {
        model.addAttribute("borrowers", borrowerRepository.findAll());
        model.addAttribute("loans", loanRepository.findAll());
        model.addAttribute("payment", new Payments());
        return "loan/payments";
    }
    
    @GetMapping("/paid")
    public String showPaidBorrowers(Model model) {
        List<Payments> paidList = paymentrepository.findByStatus("Paid");
        model.addAttribute("paidList", paidList);
        return "loan/paid_list";
    }

}
