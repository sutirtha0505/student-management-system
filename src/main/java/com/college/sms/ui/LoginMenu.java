package com.college.sms.ui;

import com.college.sms.model.User;
import com.college.sms.service.AuthService;
import com.college.sms.util.InputUtil;
import java.sql.SQLException;

public class LoginMenu {
    private final AuthService authService;

    public LoginMenu() {
        this.authService = new AuthService();
    }

    public void start() {
        while (true) {
            System.out.println("\n=== STUDENT MANAGEMENT SYSTEM ===");
            System.out.println("1. Login");
            System.out.println("2. Admin Signup");
            System.out.println("3. Exit");
            int choice = InputUtil.getInt("Enter choice: ");

            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleSignup();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void handleLogin() {
        String email = InputUtil.getString("Enter Email: ");
        String password = InputUtil.getString("Enter Password: ");

        try {
            if (authService.login(email, password)) {
                User user = authService.getCurrentUser();
                System.out.println("Login successful! Welcome " + user.getRole());
                routeUser(user);
            } else {
                System.out.println("Invalid email or password.");
            }
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
    }

    private void handleSignup() {
        System.out.println("\n--- Admin Signup ---");
        String email = InputUtil.getString("Enter Email: ");
        String password = InputUtil.getString("Enter Password: ");

        try {
            authService.registerAdmin(email, password);
        } catch (SQLException e) {
            System.out.println("Signup error: " + e.getMessage());
        }
    }

    private void routeUser(User user) {
        switch (user.getRole()) {
            case ADMIN:
                new AdminMenu().show();
                break;
            case TEACHER:
                new TeacherMenu().show();
                break;
            case STUDENT:
                // For student, we assume the email is linked which might need extra logic to
                // find student ID
                // But for now, we just pass the email/user. ID lookup might be needed in
                // StudentMenu.
                new StudentMenu(user).show();
                break;
            case STAFF:
                // Staff logic (Student CRUD only) - can reuse methods from Admin or have
                // separate menu
                // For simplicity, let's map STAFF to AdminMenu but restricting some parts?
                // Or just give them Student CRUD.
                // The requirements say STAFF: Student CRUD.
                // Let's create a StaffMenu or handle in AdminMenu.
                // For this implementation, I will assume AdminMenu handles all for now or
                // create a simple method.
                // Re-reading requirements: Admin: Full, Staff: Student CRUD.
                // I'll make logic in AdminMenu to hide things or separate menu if wanted.
                // Let's create a clear separation.
                // But Prompt says: "AdminMenu: Manage users, Manage students".
                // I will add Staff logic to AdminMenu or separate it.
                // Let's stick to strict roles. I will make a simple menu for Staff if needed,
                // but
                // AdminMenu seems to cover Student CRUD.
                // I'll use AdminMenu for now and maybe conditionally show items, OR just create
                // a specific menu.
                // Given the prompt lists "AdminMenu", "TeacherMenu", "StudentMenu", but missed
                // "StaffMenu" explicit file command?
                // Wait, the prompt structure list:
                // src/main/java/com/college/sms/ui/
                // LoginMenu, AdminMenu, TeacherMenu, StudentMenu.
                // So STAFF must be handled somewhere. I will handle STAFF in AdminMenu or
                // create a small internal handler.
                // Actually, let's just let STAFF access Student Management in AdminMenu.
                new AdminMenu(user.getRole()).show();
                break;
            default:
                System.out.println("Role not implemented yet.");
        }
        authService.logout();
    }
}
