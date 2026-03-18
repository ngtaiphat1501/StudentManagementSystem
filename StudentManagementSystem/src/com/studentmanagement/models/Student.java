package com.studentmanagement.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a student, extending Person with academic information
 */
public class Student extends Person {
    private String studentId;
    private String classId;
    private Date enrollmentDate;
    private String status;
    private double gpa;
    private double trainingScore;
    private String ranking;
    private List<Course> registeredCourses;
    private List<Activity> activities;

    /**
     * Constructor for Student
     * @param id System ID
     * @param studentId Student ID
     * @param fullName Full name
     * @param birthDate Birth date
     * @param gender Gender
     * @param phone Phone
     * @param email Email
     * @param address Address
     * @param classId Class ID
     * @param enrollmentDate Enrollment date
     * @param status Status (Studying/Suspended/Dropped)
     */
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
        this.ranking = "Not ranked";
        this.registeredCourses = new ArrayList<>();
        this.activities = new ArrayList<>();
    }

    /**
     * Adds a registered course and recalculates GPA
     * @param course Course to add
     */
    public void addRegisteredCourse(Course course) {
        registeredCourses.add(course);
        calculateGPA();
    }

    /**
     * Adds an activity and recalculates training score
     * @param activity Activity to add
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
        calculateTrainingScore();
    }

    /**
     * Calculates GPA based on registered courses with grades
     */
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

    /**
     * Calculates training score from activities
     */
    public void calculateTrainingScore() {
        double total = 0;
        for (Activity act : activities) {
            total += act.getPointEarned();
        }
        this.trainingScore = Math.min(total, 100);
        updateRanking();
    }

    /**
     * Updates ranking based on training score
     */
    private void updateRanking() {
        if (trainingScore >= 90) ranking = "Excellent";
        else if (trainingScore >= 80) ranking = "Good";
        else if (trainingScore >= 65) ranking = "Average";
        else if (trainingScore >= 50) ranking = "Below Average";
        else ranking = "Weak";
    }

    // Getters and Setters
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
        System.out.println("│                   STUDENT INFORMATION                       │");
        System.out.println("├─────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-20s: %-40s │\n", "Student ID", studentId);
        System.out.printf("│ %-20s: %-40s │\n", "Full Name", fullName);
        System.out.printf("│ %-20s: %-40s │\n", "Birth Date", sdf.format(birthDate));
        System.out.printf("│ %-20s: %-40s │\n", "Gender", gender);
        System.out.printf("│ %-20s: %-40s │\n", "Class", classId);
        System.out.printf("│ %-20s: %-40s │\n", "Enrollment Date", sdf.format(enrollmentDate));
        System.out.printf("│ %-20s: %-40s │\n", "Status", status);
        System.out.printf("│ %-20s: %-40.2f │\n", "GPA", gpa);
        System.out.printf("│ %-20s: %-40.1f │\n", "Training Score", trainingScore);
        System.out.printf("│ %-20s: %-40s │\n", "Ranking", ranking);
        System.out.printf("│ %-20s: %-40s │\n", "Email", email);
        System.out.printf("│ %-20s: %-40s │\n", "Phone", phone);
        System.out.printf("│ %-20s: %-40s │\n", "Address", address);
        System.out.println("└─────────────────────────────────────────────────────────────┘");
    }
}