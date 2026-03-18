package com.studentmanagement.services;

import com.studentmanagement.models.Activity;
import java.util.Date;
import java.util.List;

/**
 * Interface for activity and training score management
 */
public interface ActivityService {
    /**
     * Adds a new activity for a student
     * @param studentId Student ID
     * @param activityType Type of activity
     * @param activityName Name of activity
     * @param organization Organizing body
     * @param startDate Start date
     * @param endDate End date
     * @param hourSpent Hours spent
     * @return true if successful, false otherwise
     */
    public boolean addActivity(String studentId, String activityType, String activityName, 
                              String organization, Date startDate, Date endDate, int hourSpent);
    
    /**
     * Calculates training score for a student
     * @param studentId Student ID
     * @return Training score
     */
    public double calculateTrainingScore(String studentId);
    
    /**
     * Classifies training ranking for a student
     * @param studentId Student ID
     * @return Ranking string (Excellent/Good/Average/Below Average/Weak)
     */
    public String classifyTrainingRanking(String studentId);
    
    /**
     * Gets activity report for a student
     * @param StudentId Student ID
     * @return List of activities
     */
    public List<Activity> getActivityReport(String StudentId);
    
    /**
     * Exports training report
     */
    public void exportTrainingReport();
}