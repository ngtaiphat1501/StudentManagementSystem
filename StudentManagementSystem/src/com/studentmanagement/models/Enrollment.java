package com.studentmanagement.models;

import java.io.Serializable;
import java.util.Date; // Thêm thư viện Date để xử lý ngày tháng

public class Enrollment implements Serializable {
    
    // Đánh dấu phiên bản để lưu file không bị lỗi
    private static final long serialVersionUID = 1L;

    // --- CÁC THUỘC TÍNH (Theo đúng sơ đồ) ---
    private int enrollmentId;           // Mã đăng ký (kiểu int)
    private String studentId;           // Mã sinh viên
    private String courseId;            // Mã môn học
    private String semester;            // Học kỳ
    private String academicYear;        // Năm học
    private Date registrationDate;      // Ngày đăng ký
    private String status;              // Trạng thái (VD: "Đã đăng ký", "Đã hủy", "Đã hoàn thành")

    // --- CONSTRUCTORS ---
    
    // Constructor rỗng (Bắt buộc phải có khi dùng Serializable)
    public Enrollment() {
    }

    // Constructor đầy đủ tham số
    public Enrollment(int enrollmentId, String studentId, String courseId, String semester, 
                      String academicYear) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.semester = semester;
        this.academicYear = academicYear;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getStatus() {
        return status;
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

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // --- CÁC PHƯƠNG THỨC NGHIỆP VỤ (Theo đúng sơ đồ) ---

    // Đăng ký môn học
    public void register() {
        this.status = "Đã đăng ký";
        System.out.println("Sinh viên [" + this.studentId + "] đã ĐĂNG KÝ môn [" + this.courseId + "] thành công.");
    }

    // Hủy môn học
    public void dropCourse() {
        this.status = "Đã hủy";
        System.out.println("Sinh viên [" + this.studentId + "] đã HỦY môn [" + this.courseId + "].");
    }

    // Đánh dấu hoàn thành môn học
    public void markCompleted() {
        this.status = "Đã hoàn thành";
        System.out.println("Sinh viên [" + this.studentId + "] đã HOÀN THÀNH môn [" + this.courseId + "].");
    }
}