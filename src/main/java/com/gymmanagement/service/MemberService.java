package com.gymmanagement.service;

import com.gymmanagement.model.Member;
import java.util.List;

public interface MemberService {

    void saveMember(Member member);

    List<Member> getAllMembers();

    Member login(String email, String password);

    Member findByEmail(String email);

    Member getById(int id);

    void deleteMember(int id);

    long getTotalMembers();
}
