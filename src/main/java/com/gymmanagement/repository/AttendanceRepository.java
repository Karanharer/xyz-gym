package com.gymmanagement.repository;

import com.gymmanagement.model.Attendance;
import com.gymmanagement.model.Attendance.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    // Total attendance entries for a member
    long countByMember_Id(int memberId);

    // Total present entries for a member
    long countByMember_IdAndStatus(int memberId, Status status);
}
