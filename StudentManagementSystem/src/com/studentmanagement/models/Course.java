/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.studentmanagement.models;


import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author TUF GAMING
 */
public class Course {
   private String courseId;        // Format: CT101
    private String courseName;
    private int credits;            // 1-5 tín chỉ
    private String departmentId;    // Mã khoa phụ trách
    private int semester;           // Học kỳ (1, 2, 3...)
    private List<String> prerequisites; //Danh sách mã môn học tiên quyết
    private int maxStudents;        // Số sinh viên tối đa
    
    // Thuộc tính bổ sung để xử lý logic
    private int currentStudents;    // Số sinh viên đã đăng ký hiện tại
    private Grade grade;            // Lưu điểm của môn học này (để tương thích với class Student của bạn)

    // Constructor đầy đủ
    public Course(String courseId, String courseName, int credits, String departmentId, int semester, List<String> prerequisites, int maxStudents, int currentStudents, Grade grade) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.departmentId = departmentId;
        this.semester = semester;
        this.prerequisites = prerequisites;
        this.maxStudents = maxStudents;
        this.currentStudents = currentStudents;
        this.grade = grade;
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

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public int getCurrentStudents() {
        return currentStudents;
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

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void setMaxStudents(int maxStudents) {this.maxStudents = maxStudents;
    }

    public void setCurrentStudents(int currentStudents) {
        this.currentStudents = currentStudents;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    
     // Đăng ký môn học
    public boolean registerCourse() {
        if (getAvailableSeats() > 0) {
            currentStudents++;
            return true; // Đăng ký thành công
        }
        return false; // Hết chỗ
    }
    // Hủy đăng ký môn học (bổ sung thêm để logic hoàn thiện)
    public boolean cancelRegistration() {
        if (currentStudents > 0) {
            currentStudents--;
            return true;
        }
        return false;
    }
    //Kiểm tra xem còn chỗ trống không
    public int getAvailableSeats() {
        return maxStudents - currentStudents;
    }
      // Kiểm tra điều kiện tiên quyết (truyền vào danh sách các môn SV đã học qua)
    public boolean checkPrerequisites(List<String> completedCourseIds) {
        if (prerequisites == null || prerequisites.isEmpty()) {
            return true; // Không có môn tiên quyết -> đủ điều kiện
        }
        // Kiểm tra xem SV đã học tất cả các môn tiên quyết chưa
        return completedCourseIds.containsAll(prerequisites);
    }
     // Lấy thông tin môn học (In ra Console)
    public void getCourseInfo() {
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.printf("│ %-15s: %-42s │\n", "Mã môn học", courseId);
        System.out.printf("│ %-15s: %-42s │\n", "Tên môn học", courseName);
        System.out.printf("│ %-15s: %-42d │\n", "Số tín chỉ", credits);
        System.out.printf("│ %-15s: %-42s │\n", "Khoa phụ trách", departmentId);
        System.out.printf("│ %-15s: %-42d │\n", "Học kỳ", semester);
        System.out.printf("│ %-15s: %-42d │\n", "Sĩ số tối đa", maxStudents);
        System.out.printf("│ %-15s: %-42d │\n", "Chỗ còn trống", getAvailableSeats());   
        String preReqStr = prerequisites.isEmpty() ? "Không có" : String.join(", ", prerequisites);
        System.out.printf("│ %-15s: %-42s │\n", "Môn tiên quyết", preReqStr);
        System.out.println("└─────────────────────────────────────────────────────────────┘");
    }
     // Thêm môn tiên quyết
    public void addPrerequisite(String courseId) {
        if (!this.prerequisites.contains(courseId)) {
            this.prerequisites.add(courseId);
        }
    }
}