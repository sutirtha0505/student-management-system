package com.college.sms.model;

public class Mark {
    private int id;
    private String studentId;
    private String subject;
    private double marksObtained;

    public Mark(int id, String studentId, String subject, double marksObtained) {
        this.id = id;
        this.studentId = studentId;
        this.subject = subject;
        this.marksObtained = marksObtained;
    }

    public Mark(String studentId, String subject, double marksObtained) {
        this.studentId = studentId;
        this.subject = subject;
        this.marksObtained = marksObtained;
    }

    public int getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSubject() {
        return subject;
    }

    public double getMarksObtained() {
        return marksObtained;
    }
}
