package com.studentmanagement.services;

import com.studentmanagement.models.Student;
import java.util.List;
import java.util.Map;

public interface ReportService {
    Map<String, Object> getClassStatistics(String classId);
    Map<String, Integer> getGPAStatistics();
    List<Student> getTop10Students();
    Map<String, Integer> getClassificationCount();
    void generateSummaryReport();
    void exportStatisticsReport(String filePath);
}