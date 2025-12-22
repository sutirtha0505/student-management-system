package com.college.sms.dao;

import com.college.sms.db.DBConnection;
import com.college.sms.model.Attendance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    public void markAttendance(Attendance attendance) throws SQLException {
        String sql = "INSERT INTO attendance (student_id, date, status) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, attendance.getStudentId());
            stmt.setString(2, attendance.getDate());
            stmt.setString(3, attendance.getStatus());

            stmt.executeUpdate();
        }
    }

    public boolean checkAttendanceExists(String studentId, String date) throws SQLException {
        String sql = "SELECT COUNT(*) FROM attendance WHERE student_id = ? AND date = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            stmt.setString(2, date);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public List<Attendance> getAttendanceByStudentId(String studentId) throws SQLException {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE student_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Attendance(
                            rs.getInt("id"),
                            rs.getString("student_id"),
                            rs.getString("date"),
                            rs.getString("status")));
                }
            }
        }
        return list;
    }
}
