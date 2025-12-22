package com.college.sms.service;

import com.college.sms.dao.MarksDAO;
import com.college.sms.model.Mark;
import java.sql.SQLException;
import java.util.List;

public class MarksService {
    private final MarksDAO marksDAO;

    public MarksService() {
        this.marksDAO = new MarksDAO();
    }

    public void addMark(String studentId, String subject, double marksObtained) {
        try {
            Mark mark = new Mark(studentId, subject, marksObtained);
            marksDAO.addMark(mark);
            System.out.println("Marks added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding marks: " + e.getMessage());
        }
    }

    public void viewMarks(String studentId) {
        try {
            List<Mark> list = marksDAO.getMarksByStudentId(studentId);
            if (list.isEmpty()) {
                System.out.println("No marks found for this student.");
            } else {
                System.out.println("\n--- Marks ---");
                for (Mark m : list) {
                    System.out.println("Subject: " + m.getSubject() + " | Marks: " + m.getMarksObtained());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching marks: " + e.getMessage());
        }
    }
}
