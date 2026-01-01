package com.gymmanagement.service;

import com.gymmanagement.model.Payment;
import java.util.List;

public interface PaymentService {

    List<Payment> getAllPayments();

    void savePayment(Payment payment);

    double getTotalPayments(int memberId);
}
