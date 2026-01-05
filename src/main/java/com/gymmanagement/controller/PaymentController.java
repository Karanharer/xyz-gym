package com.gymmanagement.controller;

import com.gymmanagement.model.Member;
import com.gymmanagement.model.Payment;
import com.gymmanagement.model.Plan;
import com.gymmanagement.service.MemberService;
import com.gymmanagement.service.PaymentService;
import com.gymmanagement.service.PlanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/member")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PlanService planService;


    // ===============================
    // PAYMENT SUCCESS (Razorpay callback)
    @PostMapping("/paymentSuccess")
    @ResponseBody
    public String paymentSuccess(
            @RequestParam("razorpayPaymentId") String razorpayPaymentId,
            @RequestParam("planId") int planId,
            HttpSession session) {

        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "NOT_LOGGED_IN";
        }

        Plan plan = planService.getById(planId);

        Payment payment = new Payment();
        payment.setMember(member);
        payment.setPlan(plan);
        payment.setAmount(plan.getPrice());
        payment.setPaymentDate(new Date());
        payment.setMode("ONLINE");
        payment.setStatus("SUCCESS");
        payment.setRazorpayPaymentId(razorpayPaymentId);

        paymentService.savePayment(payment);

        // Assign plan
        memberService.assignPlanToMember(member.getId(), plan.getId());

        // 🔥 Refresh & update session
        Member updatedMember = memberService.getById(member.getId());
        session.setAttribute("member", updatedMember);

        return "OK";
    }

    // ===============================
    // PAYMENT FAILED (OPTIONAL)
    // ===============================
    @PostMapping("/paymentFailed")
    @ResponseBody
    public String paymentFailed(
            @RequestParam("planId") int planId,
            HttpSession session) {

        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "NOT_LOGGED_IN";
        }

        Plan plan = planService.getById(planId);

        Payment payment = new Payment();
        payment.setMember(member);
        payment.setPlan(plan);
        payment.setAmount(plan.getPrice());
        payment.setPaymentDate(new Date());
        payment.setMode("ONLINE");
        payment.setStatus("FAILED");

        paymentService.savePayment(payment);

        return "FAILED";
    }
}
