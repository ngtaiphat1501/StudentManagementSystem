package com.studentmanagement.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Student extends Person {
    private String studentId;
    private String classId;
    private Date enrollmentDate;
    private String status; // "Đang học", "Đã tốt nghiệp", "Bảo lưu", "Thôi học"
    private double gpa;
    private double trainingScore;
    private String ranking; // Xếp loại rèn luyện
    private List<Course> registeredCourses;
    private List<Activity> activities;

    public Student(String id, String studentId, String fullName, Date birthDate,
                   String gender, String phone, String email, String address,
                   String classId, Date enrollmentDate, String status) {
        super(id, fullName, birthDate, gender, phone, email, address);
        this.studentId = studentId;
        this.classId = classId;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
        this.gpa = 0.0;
        this.trainingScore = 0.0;
        this.ranking = "Chưa xếp loại";
        this.registeredCourses = new ArrayList<>();
        this.activities = new ArrayList<>();
    }

    public void addRegisteredCourse(Course course) {
        registeredCourses.add(course);
        calculateGPA(); // tự động tính lại GPA khi thêm khóa học
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
        calculateTrainingScore(); // tự động tính lại điểm rèn luyện
    }

    public void calculateGPA() {
        if (registeredCourses.isEmpty()) {
            gpa = 0.0;
            return;
        }
        double totalWeightedScore = 0;
        int totalCredits = 0;
        for (Course course : registeredCourses) {
            Grade grade = course.getGrade();
            if (grade != null) {
                totalWeightedScore += grade.getTotalScore() * course.getCredits();
                totalCredits += course.getCredits();
            }
        }
        if (totalCredits > 0) {
            gpa = totalWeightedScore / totalCredits;
        } else {
            gpa = 0.0;
        }
    }

    public void calculateTrainingScore() {
        double total = 0;
        for (Activity act : activities) {
            total += act.getPointEarned();
        }
        this.trainingScore = Math.min(total, 100); // tối đa 100
        updateRanking();
    }

    private void updateRanking() {
        if (trainingScore >= 90) ranking = "Xuất sắc";
        else if (trainingScore >= 80) ranking = "Tốt";
        else if (trainingScore >= 65) ranking = "Khá";
        else if (trainingScore >= 50) ranking = "Trung bình";
        else ranking = "Yếu";
    }

    // Getter và Setter
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getClassId() { return classId; }
    public void setClassId(String classId) { this.classId = classId; }
    public Date getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(Date enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public double getTrainingScore() { return trainingScore; }
    public void setTrainingScore(double trainingScore) { this.trainingScore = trainingScore; }
    public String getRanking() { return ranking; }
    public void setRanking(String ranking) { this.ranking = ranking; }
    public List<Course> getRegisteredCourses() { return registeredCourses; }
    public void setRegisteredCourses(List<Course> registeredCourses) { this.registeredCourses = registeredCourses; }
    public List<Activity> getActivities() { return activities; }
    public void setActivities(List<Activity> activities) { this.activities = activities; }

    @Override
    public void displayInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│                   THÔNG TIN SINH VIÊN                       │");
        System.out.println("├─────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-20s: %-40s │\n", "Mã sinh viên", studentId);
        System.out.printf("│ %-20s: %-40s │\n", "Họ và tên", fullName);
        System.out.printf("│ %-20s: %-40s │\n", "Ngày sinh", sdf.format(birthDate));
        System.out.printf("│ %-20s: %-40s │\n", "Giới tính", gender);
        System.out.printf("│ %-20s: %-40s │\n", "Lớp", classId);
        System.out.printf("│ %-20s: %-40s │\n", "Ngày nhập học", sdf.format(enrollmentDate));
        System.out.printf("│ %-20s: %-40s │\n", "Trạng thái", status);
        System.out.printf("│ %-20s: %-40.2f │\n", "GPA", gpa);
        System.out.printf("│ %-20s: %-40.1f │\n", "Điểm rèn luyện", trainingScore);
        System.out.printf("│ %-20s: %-40s │\n", "Xếp loại", ranking);
        System.out.printf("│ %-20s: %-40s │\n", "Email", email);
        System.out.printf("│ %-20s: %-40s │\n", "SĐT", phone);
        System.out.printf("│ %-20s: %-40s │\n", "Địa chỉ", address);
        System.out.println("└─────────────────────────────────────────────────────────────┘");
    }
}