package com.college.sms.dao;

import com.college.sms.db.DBConnection;
import com.college.sms.model.Mark;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarksDAO {

    public void addMark(Mark mark) throws SQLException {
        String sql = "INSERT INTO marks (student_id, subject, marks_obtained) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mark.getStudentId());
            stmt.setString(2, mark.getSubject());
            stmt.setDouble(3, mark.getMarksObtained());

            stmt.executeUpdate();
        }
    }

    public List<Mark> getMarksByStudentId(String studentId) throws SQLException {
        List<Mark> list = new ArrayList<>();
        String sql = "SELECT * FROM marks WHERE student_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Mark(
                            rs.getInt("id"),
                            rs.getString("student_id"),
                            rs.getString("subject"),
                            rs.getDouble("marks_obtained")));
                }
            }
        }
        return list;
    }
}
