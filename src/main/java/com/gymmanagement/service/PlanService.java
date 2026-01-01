package com.gymmanagement.service;

import com.gymmanagement.model.Member;
import com.gymmanagement.model.Plan;

import java.util.List;

public interface PlanService {

    List<Plan> getAllPlans();

    void savePlan(Plan plan);

    Plan getById(int id);

    int getPlanProgress(Member member);

    void deletePlan(int id);

    long getTotalPlans();
}
