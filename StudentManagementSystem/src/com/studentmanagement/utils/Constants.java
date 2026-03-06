package com.studentmanagement.utils;

public class Constants {
    
    // File paths
    public static final String STUDENT_FILE = "data/students.dat";
    public static final String USER_FILE = "data/users.dat";
    public static final String COURSE_FILE = "data/courses.dat";
    public static final String GRADE_FILE = "data/grades.dat";
    public static final String ACTIVITY_FILE = "data/activities.dat";
    
    // Academic constants
    public static final double GPA_EXCELLENT = 3.6;
    public static final double GPA_GOOD = 3.2;
    public static final double GPA_AVERAGE = 2.5;
    public static final double GPA_WEAK = 2.0;
    
    // Training score constants
    public static final double TRAINING_EXCELLENT = 90;
    public static final double TRAINING_GOOD = 80;
    public static final double TRAINING_AVERAGE = 65;
    public static final double TRAINING_WEAK = 50;
    
    // Grade weights
    public static final double ATTENDANCE_WEIGHT = 0.1;
    public static final double MIDTERM_WEIGHT = 0.3;
    public static final double FINAL_WEIGHT = 0.6;
    
    // System settings
    public static final int MAX_STUDENTS_PER_CLASS = 50;
    public static final int MAX_COURSES_PER_SEMESTER = 6;
    public static final int MAX_ACTIVITIES_PER_YEAR = 10;
    
    // Date formats
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    
    // User roles
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_TEACHER = "TEACHER";
    public static final String ROLE_STUDENT = "STUDENT";
}