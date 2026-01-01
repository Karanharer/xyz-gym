package com.gymmanagement.service;

import java.util.List;

import com.gymmanagement.model.Attendance;

public interface AttendanceService {
    double getAttendancePercent(int memberId);
    List<Attendance> getAllAttendances();
    void saveAttendance(Attendance attendance);

}
