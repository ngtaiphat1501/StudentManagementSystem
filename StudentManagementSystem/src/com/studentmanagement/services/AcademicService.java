package com.studentmanagement.services;

import com.studentmanagement.models.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface AcademicService {
    // 2.1. Đăng ký môn học
    boolean registerCourse(String studentId, String courseId, String semester, String academicYear);
    
    // 2.2. Nhập điểm
    boolean enterGrade(String studentId, String courseId, double attendance, 
                      double midterm, double finalScore);
    
    // 2.3. Tính GPA
    double calculateGPA(String studentId);
    
    // 2.4. Xem bảng điểm
    List<Grade> getTranscript(String studentId);
    
    // 2.5. Cảnh báo học vụ
    List<Student> checkAcademicWarning();
    
    // Tạo báo cáo học tập
    void generateAcademicReport();
}