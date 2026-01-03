package com.gymmanagement.service;

import com.gymmanagement.model.Payment;
import com.gymmanagement.model.Member;
import com.gymmanagement.model.Plan;

import java.util.List;
import java.util.Map;

public interface PaymentService {

    List<Payment> getAllPayments();

    void savePayment(Payment payment);

    double getTotalPayments(int memberId);

    // Member आणि Plan पासून Payment तयार करून save करेल
    void savePayment(Member m, Plan p);

    // प्रत्येक plan चा revenue return करेल: Map<PlanName, TotalRevenue>
    Map<String, Double> planWiseRevenue();
}
