// Tai 
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
    private List<Course> registeredCourses; // list update course
    private List<Activity> activities;

    public Student(String studentId, String classId, Date enrollmentDate, String status, double gpa, double trainingScore, String ranking, List<Course> registeredCourses, List<Activity> activities, String id, String fullName, Date birthDate, String gender, String phone, String email, String address) {
        super(id, fullName, birthDate, gender, phone, email, address);
        this.studentId = studentId;
        this.classId = classId;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
        this.gpa = 0.0;
        this.trainingScore = 0.0;
        this.ranking = ranking;
        // chu y khuc nay
        this.registeredCourses = new ArrayList<>();
        this.activities = new ArrayList<>();
    }
    
    
    // them khoa hoc
    public void  addRegisteredCourse(Course couse){
        registeredCourses.add(couse);
    }
    
    //them hoat dong
    public void addActivity(Activity activity){
       activities.add(activity);
    }
    // tinh gpa 
    public void calculateGPA(){
     if(registeredCourses.isEmpty()){
         gpa=0.0;
         return;
     }else{
        double totalWeightedScore =0; //tong diem * tin chi
        int totalCredits = 0; // tong so tin chi
         for (Course course : registeredCourses) {
            if (course.getGrade() != null) {
                totalWeightedScore += course.getGrade().getTotalScore() * course.getCredits();
                totalCredits += course.getCredits();
            }
        }
        
        if (totalCredits > 0) {
            gpa = totalWeightedScore / totalCredits;
        }
         
    }
   }
     //tinh diem ren luyenj
     public void calculateTranningScore(){
         double totalPoints = 0;
         for(Activity activity : activities){
             
             
         }
         
         
     }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public double getTrainingScore() {
        return trainingScore;
    }

    public void setTrainingScore(double trainingScore) {
        this.trainingScore = trainingScore;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(List<Course> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
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