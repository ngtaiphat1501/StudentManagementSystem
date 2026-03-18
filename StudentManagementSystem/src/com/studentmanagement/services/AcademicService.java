package com.studentmanagement.services;

import com.studentmanagement.models.*;
import java.util.List;

/**
 * Interface for academic operations including course registration and grade management
 */
public interface AcademicService {
    /**
     * Registers a student for a course
     * @param studentId Student ID
     * @param courseId Course ID
     * @param semester Semester
     * @param academicYear Academic year
     * @return true if registration successful, false otherwise
     */
    boolean registerCourse(String studentId, String courseId, String semester, String academicYear);
    
    /**
     * Enters grades for a student in a course
     * @param studentId Student ID
     * @param courseId Course ID
     * @param attendance Attendance score
     * @param midterm Midterm score
     * @param finalScore Final score
     * @return true if grade entry successful, false otherwise
     */
    boolean enterGrade(String studentId, String courseId, double attendance, 
                      double midterm, double finalScore);
    
    /**
     * Calculates GPA for a student
     * @param studentId Student ID
     * @return GPA value
     */
    double calculateGPA(String studentId);
    
    /**
     * Gets transcript for a student
     * @param studentId Student ID
     * @return List of grades
     */
    List<Grade> getTranscript(String studentId);
    
    /**
     * Checks for students with academic warnings
     * @return List of students with warnings
     */
    List<Student> checkAcademicWarning();
    
    /**
     * Generates academic report
     */
    void generateAcademicReport();
}