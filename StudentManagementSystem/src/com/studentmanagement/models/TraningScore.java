// Tai
package com.studentmanagement.models;

import java.io.Serializable;


public class TraningScore implements Serializable{
    private int scoreId;
    private String studentId;
    private String academicYear;
    private int semester;
    private double totalScore;
    private String ranking;

    public TraningScore(int scoreId, String studentId, String academicYear, int semester, double totalScore) {
        this.scoreId = scoreId;
        this.studentId = studentId;
        this.academicYear = academicYear;
        this.semester = semester;
        this.totalScore = totalScore;
        
    }
    // tinh diem ren luyen
    public void calculateScore(double activityPoint){
        double baseScore = 50;
        double activityScore = Math.min(activityPoint, 50);
        totalScore = baseScore +activityScore;
        
        
    }
    public void classifyRanking(){
        if(totalScore >=90){
            ranking =" Xuất Sắc ";
        }else if(totalScore >=80){
            ranking =" Tốt ";
        }else if(totalScore >= 65){
            ranking = " Kha ";
        }else if(totalScore >= 50 ){
            ranking = " Trung Binh ";
        }else{
            ranking = " Yếu ";
        }
        
    }
    // lay bao cao diem ren luyen
    public void getTrainingReport(){
        System.out.println("┌─────────────────────────────────────────────────────┐");
        System.out.println("│               BÁO CÁO ĐIỂM RÈN LUYỆN                                                 │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s: %-35s │\n", "Mã báo cáo", scoreId);
        System.out.printf("│ %-15s: %-35s │\n", "Mã sinh viên", studentId);
        System.out.printf("│ %-15s: %-35s │\n", "Năm học", academicYear);
        System.out.printf("│ %-15s: %-35d │\n", "Học kỳ", semester);
        System.out.printf("│ %-15s: %-35.1f │\n", "Tổng điểm", totalScore);
        System.out.printf("│ %-15s: %-35s │\n", "Xếp loại", ranking);
        System.out.println("└─────────────────────────────────────────────────────┘");
        
    }
    
    // get va set 

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }
    
    
}
