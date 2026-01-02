package com.gymmanagement.controller;

import com.gymmanagement.service.MemberService;
import com.gymmanagement.service.TrainerService;
import com.gymmanagement.service.PlanService;
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

    // ✅ ROOT HOME PAGE
    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("membersCount", memberService.getTotalMembers());
        model.addAttribute("trainersCount", trainerService.getTotalTrainers());
        model.addAttribute("plansCount", planService.getTotalPlans());

        model.addAttribute("plansList", planService.getAllPlans());
        model.addAttribute("topTrainers", trainerService.getTopTrainers());

        return "home"; // templates/home.html
    }

    // ✅ Terms popup page
    @GetMapping("/terms")
    public String termsPage() {
        return "terms"; // templates/terms.html
    }

    // ✅ Refund popup page
    @GetMapping("/refund")
    public String refundPage() {
        return "refund"; // templates/refund.html
    }
}
