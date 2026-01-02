package com.gymmanagement.controller;

import com.gymmanagement.model.Admin;
import com.gymmanagement.model.Member;
import com.gymmanagement.model.Trainer;
import com.gymmanagement.service.AdminService;
import com.gymmanagement.service.MemberService;
import com.gymmanagement.service.TrainerService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TrainerService trainerService;

    // ================== LOGIN PROCESS ==================

    @GetMapping("/login")
    public String loginPage() {
        return "home"; // login popup असलेला page
    }

        @PostMapping("/login")
        public String doLogin(
                @RequestParam String role,
                @RequestParam String email,
                @RequestParam String password,
                HttpSession session,
                Model model) {

            switch (role) {

                case "admin":
                    Admin admin = adminService.login(email, password);
                    if (admin != null) {
                        session.setAttribute("admin", admin);
                        return "redirect:/admin/dashboard";
                    }
                    break;

                case "member":
                    Member member = memberService.login(email, password);
                    if (member != null) {
                        session.setAttribute("member", member);
                        return "redirect:/member/dashboard";
                    }
                    break;

                case "trainer":
                    Trainer trainer = trainerService.login(email, password);
                    if (trainer != null) {
                        session.setAttribute("trainer", trainer);
                        return "redirect:/trainer/dashboard";
                    }
                    break;
            }

            model.addAttribute("error", "Invalid email or password");
            return "home";   // popup पुन्हा open होईल
        }

    // ================== LOGOUT ==================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
