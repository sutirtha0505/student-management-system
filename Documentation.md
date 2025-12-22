# 📘 Student Management System - Technical Documentation

This document provides a comprehensive technical overview of the Terminal-based Student Management System. It details the architecture, file responsibilities, class relationships, and business logic flow.

---

## 🏗️ Architectural Overview

The application follows a strict **Layered Architecture** to separate concerns and maintain modularity.

```mermaid
graph TD
    User([User / Terminal]) <--> UI[UI Layer (Menus)]
    UI <--> Service[Service Layer (Business Logic)]
    Service <--> DAO[DAO Layer (Data Access)]
    DAO <--> DB[(MySQL Database)]
```

### 1. UI Layer (`com.college.sms.ui`)
*   **Responsibility**: Handles all user interaction. It displays menus, accepts input via the console, and displays messages/results.
*   **Interaction**: Calls methods in the **Service Layer**. It never speaks directly to the Database or DAOs.

### 2. Service Layer (`com.college.sms.service`)
*   **Responsibility**: Contains the business logic and rules of the application.
*   **interaction**: Receives data from the UI, validates it (e.g., checks if a student ID exists), and then delegates the data persistence to the **DAO Layer**.

### 3. DAO Layer (`com.college.sms.dao`)
*   **Responsibility**: Handles ALL raw database operations (SQL queries).
*   **Interaction**: Uses the `DBConnection` to execute `INSERT`, `UPDATE`, `DELETE`, and `SELECT` queries. Mapping `ResultSet` rows to Java Objects (`Model`) happens here.

### 4. Database Layer (MySQL)
*   **Responsibility**: Persistent storage of `Users`, `Students`, `Attendance`, and `Marks`.

---

## 📂 File & Class Breakdown

### 1. Root Configuration & Entry

#### `README.md`
*   **Purpose**: The landing page of the project. Contains commands for compiling and running the project, along with dependency (JAR) requirements.

#### `database_setup.sql`
*   **Purpose**: A Master SQL script for initialization.
*   **Action**: It drops the existing `student_management` database (if any) and recreates it with clean `users`, `students`, `attendance`, and `marks` tables. Run this for a system reset.

#### `resources/application.properties`
*   **Purpose**: External configuration file.
*   **Details**: Stores the JDBC Connection URL, username, and password. This allows changing database credentials without recompiling the Java code.

#### `src/main/java/com/college/sms/Main.java`
*   **Purpose**: The absolute entry point of the Java application (`public static void main`).
*   **Flow**: It instantiates the `LoginMenu` and calls its `start()` method to begin the user session.

---

### 2. Configuration & Connection (`com.college.sms.config` & `db`)

#### `DBConfig.java`
*   **Role**: Configuration Holder.
*   **Details**: Contains static constants (`URL`, `USER`, `PASSWORD`). Ideally, these should be loaded from `application.properties`, but for simplicity in this project phase, they might be defined here or read by a loader.

#### `DBConnection.java`
*   **Role**: Connection Factory.
*   **Method `getConnection()`**: Returns a `java.sql.Connection` object using the driver and credentials from `DBConfig`. It handles the low-level JDBC driver loading.

---

### 3. Data Models (`com.college.sms.model`)
Simple POJOs (Plain Old Java Objects) representing database rows.

*   **`User.java`**: Represents a record from the `users` table.
    *   *Fields*: `id`, `email`, `passwordHash` (SHA-256), `Role` (Enum: ADMIN, TEACHER, STAFF, STUDENT).
*   **`Student.java`**: Represents a record from the `students` table.
    *   *Fields*: `id` (UUID String), `name`, `email`, `phone`, `address`.
*   **`Attendance.java`**: Represents a daily attendance entry.
    *   *Fields*: `studentId`, `date` (YYYY-MM-DD), `status` (Present/Absent).
*   **`Mark.java`**: Represents academic performance.
    *   *Fields*: `studentId`, `subject`, `marksObtained` (Double).

---

### 4. Data Access Objects (`com.college.sms.dao`)
The only classes allowed to contain SQL code.

#### `UserDAO.java`
*   **`createUser(User)`**: used for **Admin Signup**. Inserts a new admin into the `users` table.
*   **`getUserByEmail(String)`**: Used for **Login**. Fetches a user's role and password hash to verify credentials.

#### `StudentDAO.java`
*   **`addStudent(Student)`**: Inserts a new student record.
*   **`getAllStudents()`**: Returns a `List<Student>` of all students for viewing.
*   **`updateStudent(Student)`**: Updates name, email, phone, or address for a given ID.
*   **`deleteStudent(String id)`**: Removes a student from the database.

#### `AttendanceDAO.java`
*   **`markAttendance(Attendance)`**: Inserts an attendance record.
*   **`checkAttendanceExists(id, date)`**: Returns `true` if attendance is already marked for that day, helping prevent duplicates.
*   **`getAttendanceByStudentId(id)`**: Fetches history for a specific student.

#### `MarksDAO.java`
*   **`addMark(Mark)`**: Inserts a subject mark.
*   **`getMarksByStudentId(id)`**: Fetches all marks for a student.

---

### 5. Services (`com.college.sms.service`)
The business brain of the application.

#### `AuthService.java`
*   **Authentication Logic**:
    *   `registerAdmin(email, password)`: Hashes the password using `PasswordUtil` before calling `UserDAO.createUser`.
    *   `login(email, password)`: Fetches the user, hashes the input password, and compares it with the stored hash.
    *   `getCurrentUser()`: Maintains the session state of the logged-in user.

#### `StudentService.java`
*   **Management Logic**:
    *   Generating unique UUIDs for new students via `UUIDUtil`.
    *   Validating inputs (e.g., ensuring fields aren't empty) before passing to DAO.
    *   Formatting output lists for the console.

#### `AttendanceService.java`
*   Checks if attendance already exists for the date before allowing a new entry.
*   Formats the attendance history view.

#### `MarksService.java`
*   Handles the adding and viewing of marks.

---

### 6. User Interface (`com.college.sms.ui`)
Console-based menus powered by `System.in` and `System.out`.

#### `LoginMenu.java`
*   The first screen users see.
*   **Features**: Login, Admin Signup, Exit.
*   **Routing**: Based on the `User.Role` retrieved from `AuthService`, it instantiates and shows the correct menu (Admin/Teacher/Student).

#### `AdminMenu.java`
*   **Access**: ADMIN and STAFF (Restricted).
*   **Capabilities**:
    *   Full Student CRUD (Add, View, Update, Delete).
    *   **Admin Override**: Can also add Marks and Attendance (features usually for Teachers).

#### `TeacherMenu.java`
*   **Access**: TEACHER.
*   **Capabilities**:
    *   Mark Attendance.
    *   Add Marks.
    *   View all Students (ReadOnly).

#### `StudentMenu.java`
*   **Access**: STUDENT.
*   **Capabilities**:
    *   Identify the student by their logged-in email.
    *   View **My Attendance** (Personal data only).
    *   View **My Marks** (Personal data only).

---

### 7. Utilities (`com.college.sms.util`)

#### `InputUtil.java`
*   **Problem Solved**: Java's `Scanner` often has issues when mixing `nextInt()` and `nextLine()`.
*   **Solution**: Provides static methods (`getInt`, `getString`, `getDouble`) that handle the scanner buffer correctly and catch `NumberFormatExceptions` so the app doesn't crash on invalid input.

#### `PasswordUtil.java`
*   **Security**: Uses **SHA-256** hashing.
*   **Why?**: Storing passwords in plain text is a security risk. We store the hash (gibberish string) and compare hashes during login.

#### `UUIDUtil.java`
*   **Identity**: Wraps `java.util.UUID` to generate random, unique 36-character strings for Student IDs.
