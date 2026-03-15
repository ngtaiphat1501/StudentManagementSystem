/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.studentmanagement.services;

import com.studentmanagement.models.Activity;
import java.util.Date;
import java.util.List;

/**
 *
 * @author TUF GAMING
 */
public interface ActivityService {
    
    
    public boolean addActivity(String studentId, String activityType, String activityName, String organization, Date startDate, Date endDate, int hourSpent);
    
    public double calculateTrainingScore(String studentId);
    
    public String classifyTrainingRanking(String studentId);
    
    public List<Activity> getActivityReport(String StudentId);
    
    public void exportTrainingReport();
    
    
}
