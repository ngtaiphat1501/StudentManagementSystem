package com.studentmanagement.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a course enrollment record for a student
 */
public class Enrollment implements Serializable {
    private int enrollmentId;
    private String studentId;
    private String courseId;
    private String semester;
    private String academicYear;
    private Date registrationDate;
    private String status; // "Registered", "Cancelled", "Completed"
    
    /**
     * Constructor for Enrollment
     * @param enrollmentId Unique enrollment ID
     * @param studentId Student ID
     * @param courseId Course ID
     * @param semester Semester
     * @param academicYear Academic year
     */
    public Enrollment(int enrollmentId, String studentId, String courseId, 
                     String semester, String academicYear) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.semester = semester;
        this.academicYear = academicYear;
        this.registrationDate = new Date();
        this.status = "Registered";
    }
    
    /**
     * Registers the enrollment
     */
    public void register() {
        status = "Registered";
        System.out.println("Course registration successful!");
    }
    
    /**
     * Drops/cancels the enrollment
     */
    public void dropCourse() {
        status = "Cancelled";
        System.out.println("Course registration cancelled!");
    }
    
    /**
     * Marks the enrollment as completed
     */
    public void markCompleted() {
        status = "Completed";
    }
    
    /**
     * Displays enrollment information
     */
    public void displayEnrollmentInfo() {
        System.out.println("┌─────────────────────────────────────────────────────┐");
        System.out.println("│                ENROLLMENT INFORMATION               │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s: %-35s │\n", "Enrollment ID", enrollmentId);
        System.out.printf("│ %-15s: %-35s │\n", "Student ID", studentId);
        System.out.printf("│ %-15s: %-35s │\n", "Course ID", courseId);
        System.out.printf("│ %-15s: %-35s │\n", "Semester", semester);
        System.out.printf("│ %-15s: %-35s │\n", "Academic Year", academicYear);
        System.out.printf("│ %-15s: %-35s │\n", "Status", status);
        System.out.printf("│ %-15s: %-35s │\n", "Registration Date", registrationDate);
        System.out.println("└─────────────────────────────────────────────────────┘");
    }
    
    // Getters and Setters
    public int getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }
    
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    
    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    
    public Date getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}