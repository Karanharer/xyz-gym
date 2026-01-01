package com.gymmanagement.service;

import com.gymmanagement.model.Member;
import com.gymmanagement.model.Plan;
import com.gymmanagement.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepository planRepository;

    // ===============================
    // GET ALL PLANS
    // ===============================
    @Override
    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    @Override
    public long getTotalPlans() {
        return planRepository.count();
    }


    // ===============================
    // SAVE PLAN
    // ===============================
    @Override
    public void savePlan(Plan plan) {
        planRepository.save(plan);
    }

    // ===============================
    // GET PLAN BY ID
    // ===============================
    @Override
    public Plan getById(int id) {
        return planRepository.findById(id).orElse(null);
    }

    // ===============================
    // CALCULATE PLAN PROGRESS (FIXED)
    // ===============================
    @Override
    public int getPlanProgress(Member member) {

        if (member == null ||
            member.getPlan() == null ||
            member.getJoinDate() == null ||
            member.getExpiryDate() == null) {
            return 0;
        }

        // 🔐 SAFE conversion (NO toInstant on sql.Date)
        LocalDate joinDate = convertToLocalDate(member.getJoinDate());
        LocalDate expiryDate = convertToLocalDate(member.getExpiryDate());
        LocalDate today = LocalDate.now();

        long totalDays = ChronoUnit.DAYS.between(joinDate, expiryDate);
        long usedDays = ChronoUnit.DAYS.between(joinDate, today);

        if (totalDays <= 0) return 100;

        int progress = (int) ((usedDays * 100) / totalDays);

        // clamp 0–100
        if (progress < 0) progress = 0;
        if (progress > 100) progress = 100;

        return progress;
    }

    // ===============================
    // DELETE PLAN
    // ===============================
    @Override
    public void deletePlan(int id) {
        planRepository.deleteById(id);
    }

    // ===============================
    // PRIVATE DATE CONVERTER (KEY FIX)
    // ===============================
    private LocalDate convertToLocalDate(Date date) {
        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate(); // ✅ SAFE
        }
        return new java.sql.Date(date.getTime()).toLocalDate(); // ✅ SAFE
    }
}
