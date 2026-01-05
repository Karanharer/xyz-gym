package com.gymmanagement.service;

import com.gymmanagement.model.Member;
import com.gymmanagement.model.Plan;
import com.gymmanagement.repository.MemberRepository;
import com.gymmanagement.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlanRepository planRepository;

    @Override
    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member login(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Member getById(int id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteMember(int id) {
        memberRepository.deleteById(id);
    }

    @Override
    public long getTotalMembers() {
        return memberRepository.count();
    }
    @Override
    public void assignPlanToMember(int memberId, int planId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        // plan set
        member.setPlan(plan);

        // expiry date calculate
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, plan.getDurationMonths());

        member.setExpiryDate(cal.getTime());

        memberRepository.save(member);
    }

}
