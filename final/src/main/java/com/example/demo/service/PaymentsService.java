package com.example.demo.service;

import com.example.demo.entity.Payments;
import com.example.demo.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    public List<Payments> getAllPayments() {
        return paymentsRepository.findAll();
    }

    public Optional<Payments> getPaymentById(Long id) {
        return paymentsRepository.findById(id);
    }

    public Payments savePayment(Payments payment) {
        return paymentsRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentsRepository.deleteById(id);
    }
    
    public void save(Payments payments) {
        paymentsRepository.save(payments);
    }
    
    public List<Payments> findByBorrowerId(Long borrowerId) {
        return paymentsRepository.findByBorrowerId(borrowerId);
    }

    
}
