package com.gymmanagement.service;

import com.gymmanagement.model.Payment;
import com.gymmanagement.model.Member;
import com.gymmanagement.model.Plan;
import com.gymmanagement.repository.MemberRepository;
import com.gymmanagement.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDate;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MemberRepository memberRepository;

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
        // 1️⃣ Member update
        m.setPlan(p);
        m.setExpiryDate(java.sql.Date.valueOf(LocalDate.now().plusMonths(p.getDurationMonths())));
        memberRepository.save(m);

        // 2️⃣ Payment save
        Payment payment = new Payment();
        payment.setMember(m);
        payment.setPlan(p);
        payment.setAmount(p.getPrice());
        payment.setPaymentDate(java.sql.Date.valueOf(LocalDate.now()));
        paymentRepository.save(payment);
    }


    @Override
    public Map<String, Double> planWiseRevenue() {
        List<Payment> payments = paymentRepository.findAll();
        Map<String, Double> revenueMap = new HashMap<>();

        for(Payment p : payments){
            String planName = p.getPlan().getPlanName();
            revenueMap.put(planName, revenueMap.getOrDefault(planName, 0.0) + p.getAmount());
        }

        return revenueMap;
    }
}
