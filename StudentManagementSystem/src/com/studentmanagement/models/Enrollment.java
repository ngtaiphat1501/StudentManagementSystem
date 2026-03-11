//Nhan
package com.studentmanagement.models;

import java.io.Serializable;
import java.util.Date;

public class Enrollment implements Serializable {
    private int enrollmentId;
    private String studentId;
    private String courseId;
    private String semester;
    private String academicYear;
    private Date registrationDate;
    private String status; // "Đã đăng ký", "Đã hủy", "Hoàn thành"
    
    public Enrollment(int enrollmentId, String studentId, String courseId, 
                     String semester, String academicYear) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.semester = semester;
        this.academicYear = academicYear;
        this.registrationDate = new Date();
        this.status = "Đã đăng ký";
    }
    
    // Đăng ký môn học
    public void register() {
        status = "Đã đăng ký";
        System.out.println("Đăng ký môn học thành công!");
    }
    
    // Hủy đăng ký
    public void dropCourse() {
        status = "Đã hủy";
        System.out.println("Đã hủy đăng ký môn học!");
    }
    
    // Đánh dấu hoàn thành
    public void markCompleted() {
        status = "Hoàn thành";
    }
    
    // Hiển thị thông tin đăng ký
    public void displayEnrollmentInfo() {
        System.out.println("┌─────────────────────────────────────────────────────┐");
        System.out.println("│                THÔNG TIN ĐĂNG KÝ                    │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s: %-35s │\n", "Mã đăng ký", enrollmentId);
        System.out.printf("│ %-15s: %-35s │\n", "Mã sinh viên", studentId);
        System.out.printf("│ %-15s: %-35s │\n", "Mã môn học", courseId);
        System.out.printf("│ %-15s: %-35s │\n", "Học kỳ", semester);
        System.out.printf("│ %-15s: %-35s │\n", "Năm học", academicYear);
        System.out.printf("│ %-15s: %-35s │\n", "Trạng thái", status);
        System.out.printf("│ %-15s: %-35s │\n", "Ngày đăng ký", registrationDate);
        System.out.println("└─────────────────────────────────────────────────────┘");
    }
    
    // Getter và Setter
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