package com.gymmanagement.controller;

import com.gymmanagement.model.Member;
import com.gymmanagement.model.Payment;
import com.gymmanagement.service.MemberService;
import com.gymmanagement.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/list")
    public String listPayments(HttpSession session, Model model) {
        if(session.getAttribute("admin") == null && session.getAttribute("trainer") == null) {
            return "redirect:/login";
        }
        model.addAttribute("payments", paymentService.getAllPayments());
        return "payments";
    }

    @GetMapping("/add")
    public String addPaymentForm(HttpSession session, Model model) {
        if(session.getAttribute("admin") == null && session.getAttribute("trainer") == null) {
            return "redirect:/login";
        }
        model.addAttribute("payment", new Payment());
        model.addAttribute("members", memberService.getAllMembers());
        return "paymentForm";
    }

    @PostMapping("/add")
    public String savePayment(HttpSession session, @ModelAttribute Payment payment) {
        if(session.getAttribute("admin") == null && session.getAttribute("trainer") == null) {
            return "redirect:/login";
        }
        payment.setPaymentDate(new Date());
        paymentService.savePayment(payment);
        return "redirect:/payment/list";
    }
}
