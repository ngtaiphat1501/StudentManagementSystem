package com.studentmanagement.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity implements Serializable {
    private int activityId;
    private String studentId;
    private String activityType; // "TalkShow", "Hoc thuat", "Tinh Nguyen", "The thao"
    private String activityName;
    private String organization;
    private Date startDate;
    private Date endDate;
    private int hourSpent;
    private double pointEarned;
    private String status; // "Hoàn thành", "Đang tham gia", "Hủy"

    public Activity(int activityId, String studentId, String activityType, String activityName,
                    String organization, Date startDate, Date endDate, int hourSpent, String status) {
        this.activityId = activityId;
        this.studentId = studentId;
        this.activityType = activityType;
        this.activityName = activityName;
        this.organization = organization;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hourSpent = hourSpent;
        this.status = status;
        calculateTrainingScore(); // tự tính điểm dựa trên giờ
    }

    public void calculateTrainingScore() {
        double rate;
        switch(activityType) {
            case "TalkShow": rate = 0.5; break;
            case "Hoc thuat": rate = 0.6; break;
            case "Tinh Nguyen": rate = 0.4; break;
            case "The thao": rate = 0.3; break;
            default: rate = 0;
        }
        pointEarned = Math.min(hourSpent * rate, 30); // tối đa 30 điểm cho mỗi hoạt động
    }

    public void displayActivityInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("┌─────────────────────────────────────────────────────┐");
        System.out.println("│                THÔNG TIN HOẠT ĐỘNG                  │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s: %-35s │\n", "Mã hoạt động", activityId);
        System.out.printf("│ %-15s: %-35s │\n", "Tên hoạt động", activityName);
        System.out.printf("│ %-15s: %-35s │\n", "Loại", activityType);
        System.out.printf("│ %-15s: %-35s │\n", "Đơn vị tổ chức", organization);
        System.out.printf("│ %-15s: %-35s │\n", "Ngày bắt đầu", sdf.format(startDate));
        System.out.printf("│ %-15s: %-35s │\n", "Ngày kết thúc", sdf.format(endDate));
        System.out.printf("│ %-15s: %-35d │\n", "Số giờ", hourSpent);
        System.out.printf("│ %-15s: %-35.1f │\n", "Điểm đạt được", pointEarned);
        System.out.printf("│ %-15s: %-35s │\n", "Trạng thái", status);
        System.out.println("└─────────────────────────────────────────────────────┘");
    }

    // Getter và Setter
    public int getActivityId() { return activityId; }
    public void setActivityId(int activityId) { this.activityId = activityId; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }
    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }
    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public int getHourSpent() { return hourSpent; }
    public void setHourSpent(int hourSpent) { this.hourSpent = hourSpent; }
    public double getPointEarned() { return pointEarned; }
    public void setPointEarned(double pointEarned) { this.pointEarned = pointEarned; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}