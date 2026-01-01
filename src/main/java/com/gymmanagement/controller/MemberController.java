package com.gymmanagement.controller;

import com.gymmanagement.model.Member;
import com.gymmanagement.service.AttendanceService;
import com.gymmanagement.service.MemberService;
import com.gymmanagement.service.PaymentService;
import com.gymmanagement.service.PlanService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.gymmanagement.model.Plan;
import com.gymmanagement.model.Payment;

import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private PlanService planService;

    // ================= REGISTER =================
    @PostMapping("/register")
    public String registerMember(
            @RequestParam String role,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String phone,
            @RequestParam String gender,
            @RequestParam String addressline1,
            Model model
    ) {

        // Check if email already exists
        if (memberService.findByEmail(email) != null) {
            model.addAttribute("error", "Email already registered!");
            return "home";
        }

        // Only member registration allowed
        if (!role.equalsIgnoreCase("member")) {
            model.addAttribute("error", "Only member registration allowed!");
            return "home";
        }

        // Create new member
        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setPassword(password);
        member.setPhone(phone);
        member.setGender(gender);
        member.setAddressline1(addressline1);
        member.setRole("member");
        member.setJoinDate(new Date());

        memberService.saveMember(member);

        model.addAttribute("success", "Registration successful! Please login.");
        return "home";
    }

    // ================= MEMBER DASHBOARD =================
    @GetMapping("/dashboard")
    public String memberDashboard(Model model, HttpSession session) {

        // ✅ SAME KEY as LoginController
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member);

        // Total payments
        double paymentsTotal = paymentService.getTotalPayments(member.getId());
        model.addAttribute("paymentsTotal", paymentsTotal);

        // Attendance %
        double attendancePercent = attendanceService.getAttendancePercent(member.getId());
        model.addAttribute("attendancePercent", attendancePercent);

        // Plan progress %
        int planProgress = planService.getPlanProgress(member);
        model.addAttribute("planProgress", planProgress);

        // ✅ Add plans list for modal
        model.addAttribute("plansList", planService.getAllPlans());


        return "member-dashboard";
    }

    // ================= UPDATE PROFILE =================
    @PostMapping("/updateProfile")
    public String updateProfile(HttpSession session,
                                @RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String phone,
                                Model model) {

        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/login";
        }

        member.setName(name);
        member.setEmail(email);
        member.setPhone(phone);

        memberService.saveMember(member);

        // ✅ same key again
        session.setAttribute("member", member);

        model.addAttribute("success", "Profile updated successfully!");
        model.addAttribute("member", member);

        return "member/dashboard";
    }



    // ================= PLAN SELECTION =================
    @PostMapping("/selectPlan")
    public String selectPlan(HttpSession session,
                            @RequestParam int planId,
                            Model model) {

        Member member = (Member) session.getAttribute("member");
        if(member == null) {
            return "redirect:/login";
        }

        Plan plan = planService.getById(planId);
        if(plan == null) {
            model.addAttribute("error", "Invalid plan selected");
            return "member-dashboard";
        }

        // Save selected plan temporarily in session for payment
        session.setAttribute("selectedPlan", plan);

        // Redirect to payment page
        model.addAttribute("plan", plan);
        model.addAttribute("member", member);

        return "payments";  // payment.jsp
    }


    @PostMapping("/paymentSuccess")
    @ResponseBody
    public String paymentSuccess(HttpSession session,
                                @RequestParam String razorpayPaymentId) {

        Member member = (Member) session.getAttribute("member");
        Plan plan = (Plan) session.getAttribute("selectedPlan");

        if(member == null || plan == null){
            return "ERROR";
        }

        // 1️⃣ Create payment record
        Payment payment = new Payment();
        payment.setMember(member);
        payment.setAmount(plan.getPrice());
        payment.setPaymentDate(new Date());
        payment.setMode("Razorpay - Test");
        paymentService.savePayment(payment);

        // 2️⃣ Assign plan to member
        member.setPlan(plan);
        member.setJoinDate(new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, plan.getDurationMonths());
        member.setExpiryDate(cal.getTime());

        memberService.saveMember(member);

        // 3️⃣ Clear session temp
        session.removeAttribute("selectedPlan");

        return "OK";
    }

    // ================= LOGOUT =================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
