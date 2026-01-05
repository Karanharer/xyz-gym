package com.gymmanagement.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // ===============================
    // MEMBER
    // ===============================
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // ===============================
    // PLAN
    // ===============================
    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    // ===============================
    // PAYMENT DETAILS
    // ===============================
    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    private String mode;   // ONLINE / CASH / CARD

    private String status; // SUCCESS / FAILED / PENDING

    // ===============================
    // GETTERS & SETTERS
    // ===============================
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
