package com.gymmanagement.controller;

import com.gymmanagement.service.MemberService;
import com.gymmanagement.service.TrainerService;
import com.gymmanagement.service.PlanService;
import com.gymmanagement.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private PlanService planService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {

        // counts
        model.addAttribute("membersCount", memberService.getTotalMembers());
        model.addAttribute("trainersCount", trainerService.getTotalTrainers());
        model.addAttribute("plansCount", planService.getTotalPlans());

        // plans list
        model.addAttribute("plansList", planService.getAllPlans());

        // optional: top trainers
        model.addAttribute("topTrainers", trainerService.getTopTrainers());
        return "home"; // /WEB-INF/views/home.jsp
    }

    @GetMapping("/terms")
    public String termsPage() {
        return "home"; // home.jsp
    }

    @GetMapping("/refund")
    public String refundPage() {
        return "home"; // home.jsp
    }

}

