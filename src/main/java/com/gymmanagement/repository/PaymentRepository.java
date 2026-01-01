package com.gymmanagement.repository;

import com.gymmanagement.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.member.id = :memberId")
    Double getTotalPaymentsByMemberId(int memberId);
}
