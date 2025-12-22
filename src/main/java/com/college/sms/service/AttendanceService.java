package com.college.sms.service;

import com.college.sms.dao.AttendanceDAO;
import com.college.sms.model.Attendance;
import java.sql.SQLException;
import java.util.List;

public class AttendanceService {
    private final AttendanceDAO attendanceDAO;

    public AttendanceService() {
        this.attendanceDAO = new AttendanceDAO();
    }

    public void markAttendance(String studentId, String date, String status) {
        try {
            if (attendanceDAO.checkAttendanceExists(studentId, date)) {
                System.out.println("Attendance already marked for this student on this date.");
                return;
            }
            Attendance attendance = new Attendance(studentId, date, status);
            attendanceDAO.markAttendance(attendance);
            System.out.println("Attendance marked successfully.");
        } catch (SQLException e) {
            System.out.println("Error marking attendance: " + e.getMessage());
        }
    }

    public void viewAttendance(String studentId) {
        try {
            List<Attendance> list = attendanceDAO.getAttendanceByStudentId(studentId);
            if (list.isEmpty()) {
                System.out.println("No attendance records found for this student.");
            } else {
                System.out.println("\n--- Attendance Records ---");
                for (Attendance a : list) {
                    System.out.println("Date: " + a.getDate() + " | Status: " + a.getStatus());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching attendance: " + e.getMessage());
        }
    }
}
