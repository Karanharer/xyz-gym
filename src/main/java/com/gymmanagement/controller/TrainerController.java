package com.gymmanagement.controller;

import com.gymmanagement.model.Member;
import com.gymmanagement.model.Trainer;
import com.gymmanagement.service.MemberService;
import com.gymmanagement.service.TrainerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TrainerService trainerService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Trainer trainer = (Trainer) session.getAttribute("trainer");
        if(trainer == null) {
            return "redirect:/login";
        }
        // Show members assigned to this trainer
        List<Member> members = memberService.getAllMembers()
                .stream()
                .filter(m -> m.getTrainer() != null && m.getTrainer().getId() == trainer.getId())
                .toList();

        model.addAttribute("members", members);
        model.addAttribute("trainerName", trainer.getName());
        return "trainerDashboard";
    }
}
