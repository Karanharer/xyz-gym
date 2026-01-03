package com.gymmanagement.controller;

import com.gymmanagement.model.*;
import com.gymmanagement.service.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String assignPlan(HttpSession s,int memberId,int planId){
        if(!admin(s)) return "unauthorized";

        Member m=memberService.getById(memberId);
        Plan p=planService.getById(planId);

        m.setPlan(p);
        m.setExpiryDate(
            java.sql.Date.valueOf(
                LocalDate.now().plusMonths(p.getDurationMonths())
            )
        );
        memberService.saveMember(m);

        paymentService.savePayment(m,p);
        notificationService.save(
            "Plan "+p.getPlanName()+" assigned to "+m.getName(),
            LocalDate.now().plusDays(3)
        );
        return "assigned";
    }
}
