package com.studentmanagement.services;

import com.studentmanagement.models.Activity;
import java.util.Date;
import java.util.List;

public interface ActivityService {
    public boolean addActivity(String studentId, String activityType, String activityName, 
                              String organization, Date startDate, Date endDate, int hourSpent);
    public double calculateTrainingScore(String studentId);
    public String classifyTrainingRanking(String studentId);
    public List<Activity> getActivityReport(String StudentId);
    public void exportTrainingReport();
}