package com.studentmanagement.services;

import com.studentmanagement.models.Student;
import java.util.List;
import java.util.Map;

/**
 * Interface for report and statistics generation
 */
public interface ReportService {
    /**
     * Gets statistics for a specific class
     * @param classId Class ID
     * @return Map containing class statistics
     */
    Map<String, Object> getClassStatistics(String classId);
    
    /**
     * Gets GPA distribution statistics
     * @return Map of GPA ranges to student counts
     */
    Map<String, Integer> getGPAStatistics();
    
    /**
     * Gets top 10 outstanding students
     * @return List of top 10 students
     */
    List<Student> getTop10Students();
    
    /**
     * Gets academic classification counts
     * @return Map of classifications to student counts
     */
    Map<String, Integer> getClassificationCount();
    
    /**
     * Generates summary report
     */
    void generateSummaryReport();
    
    /**
     * Exports statistics report to a file
     * @param filePath Path to export file
     */
    void exportStatisticsReport(String filePath);
}