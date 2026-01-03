package com.gymmanagement.controller;

import com.gymmanagement.model.Member;
import com.gymmanagement.model.Plan;
import com.gymmanagement.model.Payment;
import com.gymmanagement.service.MemberService;
import com.gymmanagement.service.PlanService;
import com.gymmanagement.service.PaymentService;
import com.gymmanagement.service.NotificationService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PlanService planService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private NotificationService notificationService;

    // ================= ADMIN SESSION CHECK =================
    private boolean admin(HttpSession session) {
        return session.getAttribute("admin") != null;
    }

    // ================= DASHBOARD =================
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        if (!admin(session)) {
            return "redirect:/login";
        }

        // ===== BASIC DATA =====
        model.addAttribute("members", memberService.getAllMembers());
        model.addAttribute("plans", planService.getAllPlans());
        model.addAttribute("payments", paymentService.getAllPayments());
        model.addAttribute("notifications", notificationService.latestActive());

        // ===== PLAN WISE REVENUE (CHART SAFE DATA) =====
        List<Payment> payments = paymentService.getAllPayments();

        Map<String, Double> revenueByPlan = new HashMap<>();

        for (Payment p : payments) {
            if (p.getPlan() != null) {
                String planName = p.getPlan().getPlanName();
                revenueByPlan.put(
                    planName,
                    revenueByPlan.getOrDefault(planName, 0.0) + p.getAmount()
                );
            }
        }

        // 🔥 Chart.js needs arrays, not map
        model.addAttribute("chartLabels", revenueByPlan.keySet());
        model.addAttribute("chartValues", revenueByPlan.values());

        return "admin-dashboard";
    }


    // ================= MEMBER EDIT =================
    @PostMapping("/editMember")
    @ResponseBody
    public String editMember(HttpSession session, @ModelAttribute Member member) {

        if (!admin(session)) return "unauthorized";

        memberService.saveMember(member);
        return "updated";
    }

    // ================= MEMBER DELETE =================
    @PostMapping("/deleteMember")
    @ResponseBody
    public String deleteMember(HttpSession session, @RequestParam int id) {

        if (!admin(session)) return "unauthorized";

        memberService.deleteMember(id);
        return "deleted";
    }

    // ================= PLAN ADD =================
    @PostMapping("/addPlan")
    @ResponseBody
    public String addPlan(HttpSession session, @ModelAttribute Plan plan) {

        if (!admin(session)) return "unauthorized";

        planService.savePlan(plan);

        notificationService.save(
                "New Plan Added : " + plan.getPlanName(),
                LocalDate.now().plusDays(2)
        );

        return "added";
    }

    // ================= PLAN DELETE =================
    @PostMapping("/deletePlan")
    @ResponseBody
    public String deletePlan(HttpSession session, @RequestParam int id) {

        if (!admin(session)) return "unauthorized";

        planService.deletePlan(id);
        return "deleted";
    }

    // ================= ASSIGN PLAN =================
    @PostMapping("/assignPlan")
    @ResponseBody
    public ResponseEntity<String> assignPlan(
            HttpSession session,
            @RequestParam int memberId,
            @RequestParam int planId) {

        // 1️⃣ Admin check
        if (!admin(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }

        // 2️⃣ Member fetch (FIXED)
        Member member = memberService.getById(memberId);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("member_not_found");
        }

        // 3️⃣ Plan fetch (FIXED)
        Plan plan = planService.getById(planId);
        if (plan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("plan_not_found");
        }

        // 4️⃣ Assign plan & expiry
        LocalDate expiry = LocalDate.now().plusMonths(plan.getDurationMonths());
        member.setPlan(plan);
        member.setExpiryDate(java.sql.Date.valueOf(expiry));

        // 5️⃣ Save member
        memberService.saveMember(member);

        // 6️⃣ Save payment
        paymentService.savePayment(member, plan);

        // 7️⃣ Notification
        notificationService.save(
                "Plan " + plan.getPlanName() + " assigned to " + member.getName(),
                LocalDate.now().plusDays(3)
        );

        return ResponseEntity.ok("assigned");
    }
}
