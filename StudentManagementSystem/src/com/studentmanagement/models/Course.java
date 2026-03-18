package com.studentmanagement.models;

import java.io.Serializable;

/**
 * Represents a course in the academic system
 */
public class Course implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String courseId;
    private String courseName;
    private int credits;
    private String departmentId;
    private int semester;
    private String academicYear;
    private String teacher;
    private Grade grade;

    /**
     * Default constructor
     */
    public Course() {
    }

    /**
     * Constructor with all fields
     * @param courseId System ID
     * @param courseName Course name with code in parentheses
     * @param credits Number of credits
     * @param departmentId Department ID
     * @param semester Semester number
     * @param academicYear Academic year
     * @param teacher Teacher name
     */
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
    
    /**
     * Processes course registration
     */
    public void register() {
        System.out.println("Processing course registration: " + this.courseName + " (" + this.courseId + ")");
    }

    /**
     * Displays course information
     */
    public void displayInfo() {
        System.out.println("--------------------------------------------------");
        System.out.println("Course ID    : " + this.courseId);
        System.out.println("Course Name  : " + this.courseName);
        System.out.println("Credits      : " + this.credits);
        System.out.println("Department   : " + this.departmentId);
        System.out.println("Semester     : " + this.semester + " | Academic Year: " + this.academicYear);
        System.out.println("Teacher      : " + this.teacher);
        System.out.println("--------------------------------------------------");
    }
}