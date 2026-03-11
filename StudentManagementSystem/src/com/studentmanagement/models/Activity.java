//Tai 
package com.studentmanagement.models;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


 public class Activity implements Serializable {
    private int activityId;
    private String  studentId;
    private String activityType ;
    private String activityName;
    private String organization;
    private Date starDate;
    private Date endDate;
    private int hourSpent;
    private double pointEarned;
    private String status;

    public Activity(int activityId, String studentId, String activityType, String activityName, String organization, Date starDate, Date endDate) {
        this.activityId = activityId;
        this.studentId = studentId;
        this.activityType = activityType;
        this.activityName = activityName;
        this.organization = organization;
        this.starDate = starDate;
        this.endDate = endDate;
        this.hourSpent = hourSpent;
        this.pointEarned = pointEarned;
        this.status = status;
    }
    
    // them hoat dong
    public void addActivity(int hour , double point){
        
        this.hourSpent = hour;
        this.pointEarned = point;
        this.status = " Hoan thanh ";
        System.out.println("Da them hoat dong : "+activityName);
    }
    // tinh diem ren luyen
    public void  calculateTrainingScore(){
        double totalPoint = 0;
        switch(activityType){
            case "TalkShow":
                totalPoint = hourSpent * 0.5;
                break;
            case "Hoc thuat":
                totalPoint = hourSpent * 0.6;
                break;
            case "Tinh Nguyen":
                totalPoint = hourSpent * 0.4 ;
                break;
            case "The thao":
                totalPoint = hourSpent * 0.3 ;
                break;
        }
           pointEarned = Math.min(totalPoint,30); // toi da 30/hoat dong
    }
    public void displayActivityInfo(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("┌─────────────────────────────────────────────────────┐");
        System.out.println("│                THÔNG TIN HOẠT ĐỘNG                                                     │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s: %-35s │\n", "Mã hoạt động", activityId);
        System.out.printf("│ %-15s: %-35s │\n", "Tên hoạt động", activityName);
        System.out.printf("│ %-15s: %-35s │\n", "Loại", activityType);
        System.out.printf("│ %-15s: %-35s │\n", "Đơn vị tổ chức", organization);
        System.out.printf("│ %-15s: %-35s │\n", "Ngày bắt đầu", sdf.format(starDate));
        System.out.printf("│ %-15s: %-35s │\n", "Ngày kết thúc", sdf.format(endDate));
        System.out.printf("│ %-15s: %-35d │\n", "Số giờ", hourSpent);
        System.out.printf("│ %-15s: %-35.1f │\n", "Điểm đạt được", pointEarned);
        System.out.printf("│ %-15s: %-35s │\n", "Trạng thái", status);
        System.out.println("└─────────────────────────────────────────────────────┘");
    }
    // get and set 

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

    public Date getStarDate() {
        return starDate;
    }

    public void setStarDate(Date starDate) {
        this.starDate = starDate;
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
