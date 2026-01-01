package com.gymmanagement.service;

import java.util.List;

import com.gymmanagement.model.Attendance;
import com.gymmanagement.repository.AttendanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public double getAttendancePercent(int memberId) {

        long totalDays = attendanceRepository.countByMember_Id(memberId);

        if (totalDays == 0) {
            return 0;
        }

        long presentDays =
                attendanceRepository.countByMember_IdAndStatus(
                        memberId,
                        Attendance.Status.PRESENT
                );

        return (presentDays * 100.0) / totalDays;
    }

    @Override
    public List<Attendance> getAllAttendances() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveAttendance(Attendance attendance) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
