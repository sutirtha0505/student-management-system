package com.college.sms.service;

import com.college.sms.dao.UserDAO;
import com.college.sms.model.User;
import com.college.sms.util.PasswordUtil;
import java.sql.SQLException;

public class AuthService {
    private final UserDAO userDAO;
    private User currentUser;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public void registerAdmin(String email, String password) throws SQLException {
        if (userDAO.getUserByEmail(email) != null) {
            System.out.println("Error: Admin with this email already exists.");
            return; // Or throw exception
        }

        String passwordHash = PasswordUtil.hashPassword(password);
        User user = new User(email, passwordHash, User.Role.ADMIN);
        userDAO.createUser(user);
        System.out.println("Admin registered successfully.");
    }

    public boolean login(String email, String password) throws SQLException {
        User user = userDAO.getUserByEmail(email);
        if (user != null && PasswordUtil.checkPassword(password, user.getPasswordHash())) {
            this.currentUser = user;
            return true;
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        this.currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
