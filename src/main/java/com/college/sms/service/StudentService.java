package com.college.sms.service;

import com.college.sms.dao.StudentDAO;
import com.college.sms.model.Student;
import com.college.sms.util.UUIDUtil;
import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    public void addStudent(String name, String email, String phone, String address) {
        try {
            String uuid = UUIDUtil.generateUUID();
            Student student = new Student(uuid, name, email, phone, address);
            studentDAO.addStudent(student);
            System.out.println("Student added successfully with ID: " + uuid);
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    public void getAllStudents() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            if (students.isEmpty()) {
                System.out.println("No students found.");
            } else {
                System.out.println("\n--- Student List ---");
                for (Student s : students) {
                    System.out.println("ID: " + s.getId() + " | Name: " + s.getName() + " | Email: " + s.getEmail());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
    }

    public void updateStudent(String id, String name, String email, String phone, String address) {
        try {
            Student student = studentDAO.getStudentById(id);
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }
            if (!name.isEmpty())
                student.setName(name);
            if (!email.isEmpty())
                student.setEmail(email);
            if (!phone.isEmpty())
                student.setPhone(phone);
            if (!address.isEmpty())
                student.setAddress(address);

            studentDAO.updateStudent(student);
            System.out.println("Student updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public void deleteStudent(String id) {
        try {
            studentDAO.deleteStudent(id);
            System.out.println("Student deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }
}
