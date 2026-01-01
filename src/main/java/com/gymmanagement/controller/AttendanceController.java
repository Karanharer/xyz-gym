package com.gymmanagement.controller;

import com.gymmanagement.model.Attendance;
import com.gymmanagement.service.AttendanceService;
import com.gymmanagement.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private MemberService memberService;

    // ================= LIST ATTENDANCE =================
    @GetMapping("/list")
    public String listAttendance(HttpSession session, Model model) {

        if (session.getAttribute("admin") == null &&
            session.getAttribute("trainer") == null) {
            return "redirect:/login";
        }

        model.addAttribute("attendances",
                attendanceService.getAllAttendances());

        return "attendance"; // attendance.jsp
    }

    // ================= ADD ATTENDANCE FORM =================
    @GetMapping("/add")
    public String addAttendanceForm(HttpSession session, Model model) {

        if (session.getAttribute("admin") == null &&
            session.getAttribute("trainer") == null) {
            return "redirect:/login";
        }

        model.addAttribute("attendance", new Attendance());
        model.addAttribute("members", memberService.getAllMembers());

        return "attendanceForm"; // attendanceForm.jsp
    }

    // ================= SAVE ATTENDANCE =================
    @PostMapping("/add")
    public String saveAttendance(
            HttpSession session,
            @ModelAttribute Attendance attendance) {

        if (session.getAttribute("admin") == null &&
            session.getAttribute("trainer") == null) {
            return "redirect:/login";
        }

        // Auto set today's date
        attendance.setAttendanceDate(new Date());

        attendanceService.saveAttendance(attendance);

        return "redirect:/attendance/list";
    }
}
