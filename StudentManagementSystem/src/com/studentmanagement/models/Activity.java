package com.studentmanagement.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a student activity/event for training score calculation
 */
public class Activity implements Serializable {

    private int activityId;
    private String studentId;
    private String activityType; // "TalkShow", "Academic", "Volunteer", "Sports"
    private String activityName;
    private String organization;
    private Date startDate;
    private Date endDate;
    private int hourSpent;
    private double pointEarned;
    private String status; // "Completed", "Participating", "Cancelled"

    /**
     * Constructor for Activity
     * @param activityId Unique activity ID
     * @param studentId Student ID
     * @param activityType Type of activity
     * @param activityName Name of activity
     * @param organization Organizing body
     * @param startDate Start date
     * @param endDate End date
     * @param hourSpent Hours spent
     */
    public Activity(int activityId, String studentId, String activityType, String activityName,
            String organization, Date startDate, Date endDate, int hourSpent) {
        this.activityId = activityId;
        this.studentId = studentId;
        this.activityType = activityType;
        this.activityName = activityName;
        this.organization = organization;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hourSpent = hourSpent;
        this.status = "Completed";
        calculateTrainingScore();
    }

    /**
     * Calculates training score points based on activity type and hours
     */
    public void calculateTrainingScore() {
        double rate;
        switch (activityType.toLowerCase()) {
            case "talkshow":
                rate = 0.5;
                break;
            case "academic":
                rate = 0.6;
                break;
            case "volunteer":
                rate = 0.4;
                break;
            case "sports":
                rate = 0.3;
                break;
            default:
                rate = 0.2;
        }
        pointEarned = Math.min(hourSpent * rate, 30); // maximum 30 points per activity
    }

    /**
     * Displays activity information in a formatted box
     */
    public void displayActivityInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("┌─────────────────────────────────────────────────────┐");
        System.out.println("│                ACTIVITY INFORMATION                 │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s: %-35s │\n", "Activity ID", activityId);
        System.out.printf("│ %-15s: %-35s │\n", "Activity Name", activityName);
        System.out.printf("│ %-15s: %-35s │\n", "Type", activityType);
        System.out.printf("│ %-15s: %-35s │\n", "Organization", organization);
        System.out.printf("│ %-15s: %-35s │\n", "Start Date", sdf.format(startDate));
        System.out.printf("│ %-15s: %-35s │\n", "End Date", sdf.format(endDate));
        System.out.printf("│ %-15s: %-35d │\n", "Hours", hourSpent);
        System.out.printf("│ %-15s: %-35.1f │\n", "Points Earned", pointEarned);
        System.out.printf("│ %-15s: %-35s │\n", "Status", status);
        System.out.println("└─────────────────────────────────────────────────────┘");
    }

    // Getters and Setters
    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getHourSpent() {
        return hourSpent;
    }

    public void setHourSpent(int hourSpent) {
        this.hourSpent = hourSpent;
        calculateTrainingScore();
    }

    public double getPointEarned() {
        return pointEarned;
    }

    public void setPointEarned(double pointEarned) {
        this.pointEarned = pointEarned;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}