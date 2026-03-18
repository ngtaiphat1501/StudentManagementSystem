package com.studentmanagement.models;

import java.io.Serializable;

public class Course implements Serializable {
    
    // Đánh dấu phiên bản để lưu file không bị lỗi
    private static final long serialVersionUID = 1L;

    // --- CÁC THUỘC TÍNH (Theo đúng sơ đồ) ---
    private String courseId;        // Mã môn học
    private String courseName;      // Tên môn học
    private int credits;            // Số tín chỉ
    private String departmentId;    // Mã khoa
    private int semester;           // Học kỳ (kiểu int theo sơ đồ)
    private String academicYear;    // Năm học
    private String teacher;         // Giảng viên
    private Grade grade;            // Điểm số của môn học (liên kết với class Grade)

    // --- CONSTRUCTORS ---
    
    // Constructor rỗng (Rất cần thiết khi đọc/ghi file hoặc dùng v��i các framework)
    public Course() {
    }

    // Constructor có tham số (Không bao gồm Grade vì điểm thường được cập nhật sau)
    public Course(String courseId, String courseName, int credits, String departmentId, 
                  int semester, String academicYear, String teacher) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.departmentId = departmentId;
        this.semester = semester;
        this.academicYear = academicYear;
        this.teacher = teacher;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public int getSemester() {
        return semester;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public String getTeacher() {
        return teacher;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    

    // --- CÁC PHƯƠNG THỨC (Theo đúng sơ đồ) ---

    // Hàm mô phỏng việc đăng ký môn học
    public void register() {
        System.out.println("Đang xử lý đăng ký môn học: " + this.courseName + " (" + this.courseId + ")");
    }

    // Hàm hiển thị thông tin môn học
    public void displayInfo() {
        System.out.println("--------------------------------------------------");
        System.out.println("Mã môn học : " + this.courseId);
        System.out.println("Tên môn học: " + this.courseName);
        System.out.println("Số tín chỉ : " + this.credits);
        System.out.println("Mã khoa    : " + this.departmentId);
        System.out.println("Học kỳ     : " + this.semester + " | Năm học: " + this.academicYear);
        System.out.println("Giảng viên : " + this.teacher);
        System.out.println("--------------------------------------------------");
    }
    
}