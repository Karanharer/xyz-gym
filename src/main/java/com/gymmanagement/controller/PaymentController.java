package com.gymmanagement.controller;

import com.gymmanagement.model.Member;
import com.gymmanagement.model.Payment;
import com.gymmanagement.service.MemberService;
import com.gymmanagement.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private MemberService memberService;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    // =======================
    // SHOW PAYMENT PAGE
    // =======================
    @GetMapping("/pay/{memberId}")
    public String paymentPage(
            @PathVariable int memberId,
            Model model,
            HttpSession session
    ) {
        Member member = memberService.getMemberById(memberId);
        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("plan", member.getPlan());
        model.addAttribute("razorpayKeyId", razorpayKeyId);

        session.setAttribute("member", member);

        return "payment";
    }

    // =======================
    // PAYMENT SUCCESS
    // =======================
    @PostMapping("/paymentSuccess")
    @ResponseBody
    public String paymentSuccess(
            @RequestParam("razorpayPaymentId") String paymentId,
            HttpSession session
    ) {
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            return "FAILED";
        }

        Payment payment = new Payment();
        payment.setMember(member);
        payment.setAmount(member.getPlan().getPrice());
        payment.setPaymentMode("ONLINE");
        payment.setTransactionId(paymentId);
        payment.setPaymentDate(new Date());
        payment.setStatus("SUCCESS");

        paymentService.savePayment(payment);

        return "OK";
    }
}
