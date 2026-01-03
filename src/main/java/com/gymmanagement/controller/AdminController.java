package com.gymmanagement.controller;

import com.gymmanagement.model.*;
import com.gymmanagement.service.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired MemberService memberService;
    @Autowired PlanService planService;
    @Autowired PaymentService paymentService;
    @Autowired NotificationService notificationService;

    private boolean admin(HttpSession s){
        return s.getAttribute("admin") != null;
    }

    // ================= DASHBOARD =================
    @GetMapping("/dashboard")
    public String dashboard(HttpSession s, Model m){
        if(!admin(s)) return "redirect:/login";

        m.addAttribute("members", memberService.getAllMembers());
        m.addAttribute("plans", planService.getAllPlans());
        m.addAttribute("payments", paymentService.getAllPayments());
        m.addAttribute("notifications", notificationService.latestActive());
        m.addAttribute("revenue", paymentService.planWiseRevenue());


        return "admin-dashboard";
    }

    // ================= MEMBER EDIT =================
    @PostMapping("/editMember")
    @ResponseBody
    public String editMember(HttpSession s, Member member){
        if(!admin(s)) return "unauthorized";
        memberService.saveMember(member);
        return "updated";
    }

    // ================= MEMBER DELETE =================
    @PostMapping("/deleteMember")
    @ResponseBody
    public String deleteMember(HttpSession s, int id){
        if(!admin(s)) return "unauthorized";
        memberService.deleteMember(id);
        return "deleted";
    }

    // ================= PLAN =================
    @PostMapping("/addPlan")
    @ResponseBody
    public String addPlan(HttpSession s, Plan p){
        if(!admin(s)) return "unauthorized";
        planService.savePlan(p);
        notificationService.save(
            "New Plan Added : "+p.getPlanName(),
            LocalDate.now().plusDays(2)
        );
        return "added";
    }

    @PostMapping("/deletePlan")
    @ResponseBody
    public String deletePlan(HttpSession s, int id){
        if(!admin(s)) return "unauthorized";
        planService.deletePlan(id);
        return "deleted";
    }

        // ================= ASSIGN PLAN =================
    @PostMapping("/assignPlan")
    @ResponseBody
    public ResponseEntity<String> assignPlan(HttpSession session,
                                            @RequestParam int memberId,
                                            @RequestParam int planId) {
        // 1️⃣ Authorization check
        if (!admin(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }

        // 2️⃣ Fetch Member
        Optional<Member> optMember = memberService.findById(memberId);
        if (optMember.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("member_not_found");
        }
        Member member = optMember.get();

        // 3️⃣ Fetch Plan
        Optional<Plan> optPlan = planService.findById(planId);
        if (optPlan.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("plan_not_found");
        }
        Plan plan = optPlan.get();

        // 4️⃣ Set plan and expiry date
        LocalDate expiry = LocalDate.now().plusMonths(plan.getDurationMonths());
        member.setPlan(plan);
        member.setExpiryDate(java.sql.Date.valueOf(expiry));

        // 5️⃣ Save updated member
        memberService.saveMember(member);

        // 6️⃣ Save payment
        paymentService.savePayment(member, plan);

        // 7️⃣ Save notification for admin/member
        LocalDate notifyDate = LocalDate.now().plusDays(3); // Notify 3 days later
        notificationService.save(
                "Plan " + plan.getPlanName() + " assigned to " + member.getName(),
                notifyDate
        );

        // 8️⃣ Return success response
        return ResponseEntity.ok("assigned");
    }

}
