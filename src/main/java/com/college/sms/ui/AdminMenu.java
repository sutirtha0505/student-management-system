package com.college.sms.ui;

import com.college.sms.model.User.Role;
import com.college.sms.service.AttendanceService;
import com.college.sms.service.MarksService;
import com.college.sms.service.StudentService;
import com.college.sms.util.InputUtil;

public class AdminMenu {
    private final StudentService studentService;
    private final AttendanceService attendanceService;
    private final MarksService marksService;
    private final Role role; // specific role accessing this menu

    public AdminMenu() {
        this(Role.ADMIN);
    }

    public AdminMenu(Role role) {
        this.studentService = new StudentService();
        this.attendanceService = new AttendanceService();
        this.marksService = new MarksService();
        this.role = role;
    }

    public void show() {
        while (true) {
            System.out.println("\n--- " + role + " MENU ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");

            if (role == Role.ADMIN) {
                System.out.println("5. Mark Attendance (Admin override)");
                System.out.println("6. View Attendance");
                System.out.println("7. Add Marks");
                System.out.println("8. View Marks");
                // Add more admin stuff like User Management if needed
            } else if (role == Role.STAFF) {
                // Staff only has Student CRUD (1-4)
            }

            System.out.println("0. Logout");
            int choice = InputUtil.getInt("Enter choice: ");

            if (choice == 0)
                break;

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    studentService.getAllStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    if (role == Role.ADMIN)
                        markAttendance();
                    else
                        System.out.println("Access Denied");
                    break;
                case 6:
                    if (role == Role.ADMIN)
                        viewAttendance();
                    else
                        System.out.println("Access Denied");
                    break;
                case 7:
                    if (role == Role.ADMIN)
                        addMarks();
                    else
                        System.out.println("Access Denied");
                    break;
                case 8:
                    if (role == Role.ADMIN)
                        viewMarks();
                    else
                        System.out.println("Access Denied");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addStudent() {
        String name = InputUtil.getString("Name: ");
        String email = InputUtil.getString("Email: ");
        String phone = InputUtil.getString("Phone: ");
        String address = InputUtil.getString("Address: ");
        studentService.addStudent(name, email, phone, address);
    }

    private void updateStudent() {
        String id = InputUtil.getString("Enter Student ID to update: ");
        System.out.println("Press Enter to skip fields you don't want to change.");
        String name = InputUtil.getString("New Name: ");
        String email = InputUtil.getString("New Email: ");
        String phone = InputUtil.getString("New Phone: ");
        String address = InputUtil.getString("New Address: ");
        studentService.updateStudent(id, name, email, phone, address);
    }

    private void deleteStudent() {
        String id = InputUtil.getString("Enter Student ID to delete: ");
        studentService.deleteStudent(id);
    }

    // Admin helpers
    private void markAttendance() {
        String id = InputUtil.getString("Student ID: ");
        String date = InputUtil.getString("Date (YYYY-MM-DD): ");
        String status = InputUtil.getString("Status (Present/Absent): ");
        attendanceService.markAttendance(id, date, status);
    }

    private void viewAttendance() {
        String id = InputUtil.getString("Student ID: ");
        attendanceService.viewAttendance(id);
    }

    private void addMarks() {
        String id = InputUtil.getString("Student ID: ");
        String subject = InputUtil.getString("Subject: ");
        double marks = InputUtil.getDouble("Marks Obtained: ");
        marksService.addMark(id, subject, marks);
    }

    private void viewMarks() {
        String id = InputUtil.getString("Student ID: ");
        marksService.viewMarks(id);
    }
}
