package com.college.sms.model;

public class User {
    private int id;
    private String email;
    private String passwordHash;
    private Role role;

    public enum Role {
        ADMIN, TEACHER, STAFF, STUDENT
    }

    public User(int id, String email, String passwordHash, Role role) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public User(String email, String passwordHash, Role role) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }
}
