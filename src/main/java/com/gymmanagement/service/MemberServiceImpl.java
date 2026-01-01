package com.gymmanagement.service;

import com.gymmanagement.model.Member;
import com.gymmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

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

}
