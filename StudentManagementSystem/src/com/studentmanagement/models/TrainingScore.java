package com.studentmanagement.models;

import java.io.Serializable;

public class TrainingScore implements Serializable {
    private int scoreId;
    private String studentId;
    private String academicYear;
    private int semester;
    private double totalScore;
    private String ranking;

    public TrainingScore(int scoreId, String studentId, String academicYear, int semester, double totalScore) {
        this.scoreId = scoreId;
        this.studentId = studentId;
        this.academicYear = academicYear;
        this.semester = semester;
        this.totalScore = totalScore;
        classifyRanking();
    }

    public void calculateScore(double activityPoint) {
        double baseScore = 50;
        double activityScore = Math.min(activityPoint, 50);
        totalScore = baseScore + activityScore;
        classifyRanking();
    }

    public void classifyRanking() {
        if (totalScore >= 90) ranking = "Xuất sắc";
        else if (totalScore >= 80) ranking = "Tốt";
        else if (totalScore >= 65) ranking = "Khá";
        else if (totalScore >= 50) ranking = "Trung bình";
        else ranking = "Yếu";
    }

    public void getTrainingReport() {
        System.out.println("┌─────────────────────────────────────────────────────┐");
        System.out.println("│               BÁO CÁO ĐIỂM RÈN LUYỆN                │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s: %-35s │\n", "Mã báo cáo", scoreId);
        System.out.printf("│ %-15s: %-35s │\n", "Mã sinh viên", studentId);
        System.out.printf("│ %-15s: %-35s │\n", "Năm học", academicYear);
        System.out.printf("│ %-15s: %-35d │\n", "Học kỳ", semester);
        System.out.printf("│ %-15s: %-35.1f │\n", "Tổng điểm", totalScore);
        System.out.printf("│ %-15s: %-35s │\n", "Xếp loại", ranking);
        System.out.println("└─────────────────────────────────────────────────────┘");
    }

    // Getter và Setter
    public int getScoreId() { return scoreId; }
    public void setScoreId(int scoreId) { this.scoreId = scoreId; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }
    public double getTotalScore() { return totalScore; }
    public void setTotalScore(double totalScore) { this.totalScore = totalScore; }
    public String getRanking() { return ranking; }
    public void setRanking(String ranking) { this.ranking = ranking; }
}