package com.college.sms.ui;

import com.college.sms.service.AttendanceService;
import com.college.sms.service.MarksService;
import com.college.sms.service.StudentService;
import com.college.sms.util.InputUtil;

public class TeacherMenu {
    private final AttendanceService attendanceService;
    private final MarksService marksService;
    private final StudentService studentService;

    public TeacherMenu() {
        this.attendanceService = new AttendanceService();
        this.marksService = new MarksService();
        this.studentService = new StudentService();
    }

    public void show() {
        while (true) {
            System.out.println("\n--- TEACHER MENU ---");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Add Marks");
            System.out.println("4. View Marks");
            System.out.println("5. View All Students");
            System.out.println("0. Logout");
            int choice = InputUtil.getInt("Enter choice: ");

            if (choice == 0)
                break;

            switch (choice) {
                case 1:
                    String id = InputUtil.getString("Student ID: ");
                    String date = InputUtil.getString("Date (YYYY-MM-DD): ");
                    String status = InputUtil.getString("Status (Present/Absent): ");
                    attendanceService.markAttendance(id, date, status);
                    break;
                case 2:
                    String sid = InputUtil.getString("Student ID: ");
                    attendanceService.viewAttendance(sid);
                    break;
                case 3:
                    String mid = InputUtil.getString("Student ID: ");
                    String subject = InputUtil.getString("Subject: ");
                    double marks = InputUtil.getDouble("Marks Obtained: ");
                    marksService.addMark(mid, subject, marks);
                    break;
                case 4:
                    String vmid = InputUtil.getString("Student ID: ");
                    marksService.viewMarks(vmid);
                    break;
                case 5:
                    studentService.getAllStudents();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
