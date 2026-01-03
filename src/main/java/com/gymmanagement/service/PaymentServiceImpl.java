package com.gymmanagement.service;

import com.gymmanagement.model.Payment;
import com.gymmanagement.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.gymmanagement.model.Member;
import com.gymmanagement.model.Plan;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public double getTotalPayments(int memberId) {
        Double total = paymentRepository.getTotalPaymentsByMemberId(memberId);
        return total != null ? total : 0;
    }

    @Override
    public void savePayment(Member m, Plan p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object planWiseRevenue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
