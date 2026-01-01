package com.gymmanagement.controller;

import com.gymmanagement.model.Member;
import com.gymmanagement.model.Plan;
import com.gymmanagement.service.MemberService;
import com.gymmanagement.service.PlanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PlanService planService;

    // ================= Helper: Check Admin Session =================
    private boolean checkAdminSession(HttpSession session) {
        return session.getAttribute("admin") != null;
    }

    // ================= ADMIN DASHBOARD =================
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!checkAdminSession(session)) {
            return "redirect:/login";
        }

        model.addAttribute("members", memberService.getAllMembers());
        model.addAttribute("plans", planService.getAllPlans());
        return "admin-dashboard";
    }

    // ================= ADD MEMBER =================
    @GetMapping("/addMember")
    public String addMemberForm(HttpSession session, Model model) {
        if (!checkAdminSession(session)) {
            return "redirect:/login";
        }

        model.addAttribute("member", new Member());
        model.addAttribute("plans", planService.getAllPlans());
        return "addMember";
    }

    @PostMapping("/addMember")
    public String saveMember(HttpSession session, @ModelAttribute Member member) {
        if (!checkAdminSession(session)) {
            return "redirect:/login";
        }

        memberService.saveMember(member);
        return "redirect:/admin/dashboard";
    }

    // ================= ADD PLAN =================
    @GetMapping("/addPlan")
    public String addPlanForm(HttpSession session, Model model) {
        if (!checkAdminSession(session)) {
            return "redirect:/login";
        }

        model.addAttribute("plan", new Plan());
        return "addPlan";
    }

    @PostMapping("/addPlan")
    public String savePlan(HttpSession session, @ModelAttribute Plan plan) {
        if (!checkAdminSession(session)) {
            return "redirect:/login";
        }

        planService.savePlan(plan);
        return "redirect:/admin/dashboard";
    }

    // ================= ASSIGN PLAN TO MEMBER =================
    @PostMapping("/assignPlan")
    public String assignPlan(
            HttpSession session,
            @RequestParam int memberId,
            @RequestParam int planId) {

        if (!checkAdminSession(session)) {
            return "redirect:/login";
        }

        Member member = memberService.getById(memberId);
        Plan plan = planService.getById(planId);

        if (member != null && plan != null) {
            member.setPlan(plan);

            // Calculate expiry date automatically
            LocalDate expiry = LocalDate.now().plusMonths(plan.getDurationMonths());
            member.setExpiryDate(java.sql.Date.valueOf(expiry)); // ✅


            memberService.saveMember(member);
        }

        return "redirect:/admin/dashboard";
    }

    // ================= DELETE PLAN (Optional) =================
    @GetMapping("/deletePlan/{id}")
    public String deletePlan(HttpSession session, @PathVariable int id) {
        if (!checkAdminSession(session)) {
            return "redirect:/login";
        }

        planService.deletePlan(id);
        return "redirect:/admin/dashboard";
    }

    // ================= DELETE MEMBER (Optional) =================
    @GetMapping("/deleteMember/{id}")
    public String deleteMember(HttpSession session, @PathVariable int id) {
        if (!checkAdminSession(session)) {
            return "redirect:/login";
        }

        memberService.deleteMember(id);
        return "redirect:/admin/dashboard";
    }
}
