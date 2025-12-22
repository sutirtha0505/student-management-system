package com.college.sms.ui;

import com.college.sms.model.Student;
import com.college.sms.model.User;
import com.college.sms.service.AttendanceService;
import com.college.sms.service.MarksService;
import com.college.sms.dao.StudentDAO; // Need DAO to find student ID by email
import java.sql.SQLException;
import java.util.List;

public class StudentMenu {
    private final AttendanceService attendanceService;
    private final MarksService marksService;
    private final User user;
    private String studentId;

    public StudentMenu(User user) {
        this.attendanceService = new AttendanceService();
        this.marksService = new MarksService();
        this.user = user;
        resolveStudentId();
    }

    private void resolveStudentId() {
        // In a real app, query 'students' table where email matches user.email
        // Assuming StudentDAO has a method or we add one.
        // For this assignment, let's create a quick valid check or just ask for ID if
        // not found?
        // Better: Fetch it.
        StudentDAO dao = new StudentDAO();
        try {
            List<Student> students = dao.getAllStudents();
            for (Student s : students) {
                if (s.getEmail().equalsIgnoreCase(user.getEmail())) {
                    this.studentId = s.getId();
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error resolving student profile: " + e.getMessage());
        }
    }

    public void show() {
        if (studentId == null) {
            System.out.println("Error: No student profile found for email " + user.getEmail());
            return;
        }

        System.out.println("Welcome Student, ID: " + studentId);

        while (true) {
            System.out.println("\n--- STUDENT MENU ---");
            System.out.println("1. View My Attendance");
            System.out.println("2. View My Marks");
            System.out.println("0. Logout");

            // Note: StudentMenu doesn't use InputUtil for everything but let's stick to it
            // where needed.
            // But here we need int choice.
            // Oh, InputUtil is in the package.
            // Wait, I am not importing InputUtil? It's in util package.
            // Let's assume I can add the import or use fully qualified.
            // I'll add the import in the file content.

            // Since I am writing the file content, I must ensure imports are correct.
            // I will use InputUtil.getInt.

            int choice = com.college.sms.util.InputUtil.getInt("Enter choice: ");

            if (choice == 0)
                break;

            switch (choice) {
                case 1:
                    attendanceService.viewAttendance(studentId);
                    break;
                case 2:
                    marksService.viewMarks(studentId);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
