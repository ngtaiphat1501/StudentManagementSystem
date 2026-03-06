/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.studentmanagement.models;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author TUF GAMING
 */
public class Enrollment {
    private int enrollmentId;           // Khóa chính (ID phiếu đăng ký)
    private String studentId;           // Khóa ngoại liên kết tới Student
    private String courseId;            // Khóa ngoại liên kết tới Course
    private String semester;            // Học kỳ (VD: "HK1", "HK2")
    private String academicYear;        // Năm học (VD: "2023-2024")
    private String status;              // Trạng thái: PENDING, APPROVED, CANCELLED, COMPLETED
    private Date registrationDate;      // Ngày đăng ký
    // Thuộc tính bổ sung để lưu điểm của môn học này (theo Class Diagram ban đầu)
    private Grade grade; 

    public Enrollment(int enrollmentId, String studentId, String courseId, String semester, String academicYear, String status, Date registrationDate, Grade grade) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.semester = semester;
        this.academicYear = academicYear;
        this.status = "PENDING";  // Mặc định khi mới tạo thì trạng thái là Chờ duyệt (PENDING) và lấy ngày giờ hiện tại
        this.registrationDate = registrationDate;
        this.grade = grade;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getSemester() {
        return semester;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public String getStatus() {
        return status;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }// 1. Đăng ký môn học (Khởi tạo lại trạng thái đăng ký)
    public void register() {
        this.status = "PENDING";
        this.registrationDate = new Date();
        System.out.println("Đã ghi nhận yêu cầu đăng ký môn " + courseId + " cho sinh viên " + studentId);
    }
    // 2. Hủy đăng ký môn học
    public void dropCourse() {
        if ("COMPLETED".equals(this.status)) {
            System.out.println("Lỗi: Không thể hủy môn học đã hoàn thành!");
        } else {
            this.status = "CANCELLED";
            System.out.println("Đã hủy đăng ký môn " + courseId + " thành công.");
        }
    }
    // 3. Duyệt đăng ký (Dành cho Giáo vụ / Admin duyệt)
    public void approveEnrollment() {
        if ("PENDING".equals(this.status)) {
            this.status = "APPROVED";
            System.out.println("Đã duyệt thành công môn " + courseId + " cho sinh viên " + studentId);
        } else {
            System.out.println("Lỗi: Chỉ có thể duyệt các môn đang ở trạng thái PENDING (Chờ duyệt).");
        }
    }
    // 5. In thông tin đăng ký ra màn hình (Thay cho getEnrollments)
    public void getEnrollmentInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        System.out.printf("│ %-5d │ %-10s │ %-10s │ %-6s │ %-10s │ %-10s │ %-16s │\n", 
                enrollmentId, studentId, courseId, semester, academicYear, status, sdf.format(registrationDate));
    }
}