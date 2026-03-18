package com.studentmanagement.services;

import com.studentmanagement.models.*;
import java.util.List;

public interface AcademicService {
    boolean registerCourse(String studentId, String courseId, String semester, String academicYear);
    boolean enterGrade(String studentId, String courseId, double attendance, 
                      double midterm, double finalScore);
    double calculateGPA(String studentId);
    List<Grade> getTranscript(String studentId);
    List<Student> checkAcademicWarning();
    void generateAcademicReport();
}