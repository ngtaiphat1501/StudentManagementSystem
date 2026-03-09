/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.studentmanagement.services;

import com.studentmanagement.models.Student; // Nhớ import class Student của bạn bạn
import java.util.List;
import java.util.Map;

public interface ReportService {
    // 4.1: Thống kê theo lớp (Sĩ số, GPA, rèn luyện...)
    Map<String, Object> getClassStatistics(String classId);
    
    // 4.2: Thống kê theo GPA (Phân phối <2.0, 2.0-2.5...)
    Map<String, Integer> getGPAStatistics();
    
    // 4.3: Top 10 sinh viên (GPA 70% + RL 30%)
    List<Student> getTop10Students();
    
    // 4.4: Xếp loại sinh viên (Học lực, rèn luyện)
    Map<String, Integer> getClassificationCount();
    
    // 4.5: Báo cáo tổng hợp
    void generateSummaryReport();
    
    // Xuất báo cáo ra file
    void exportStatisticsReport(String filePath);
}
