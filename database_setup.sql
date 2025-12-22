-- Database Setup Script for Student Management System
-- Usage: SOURCE /path/to/database_setup.sql; in MySQL terminal

-- 1. Create Database (if not exists, but we want fresh start so we drop)
DROP DATABASE IF EXISTS student_management;
CREATE DATABASE student_management;
USE student_management;

-- 2. Drop Tables if they exist (Reverse dependency order)
DROP TABLE IF EXISTS marks;
DROP TABLE IF EXISTS attendance;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS users;

-- 3. Create Users Table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL, -- SHA-256 hash
    role ENUM('ADMIN', 'TEACHER', 'STAFF', 'STUDENT') NOT NULL
);

-- 4. Create Students Table
CREATE TABLE students (
    id VARCHAR(36) PRIMARY KEY, -- UUID
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT
);

-- 5. Create Attendance Table
CREATE TABLE attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(36) NOT NULL,
    date DATE NOT NULL, -- Format YYYY-MM-DD
    status VARCHAR(20) NOT NULL, -- Present/Absent
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- 6. Create Marks Table
CREATE TABLE marks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(36) NOT NULL,
    subject VARCHAR(100) NOT NULL,
    marks_obtained DOUBLE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- Optional: Initial Data (Uncomment if needed)
-- INSERT INTO users (email, password_hash, role) VALUES ('admin@college.com', 'hashed_secret', 'ADMIN');
