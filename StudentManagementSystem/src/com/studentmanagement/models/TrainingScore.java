package com.studentmanagement.models;

import java.io.Serializable;

/**
 * Represents a student's training score for a specific academic period
 */
public class TrainingScore implements Serializable {
    private int scoreId;
    private String studentId;
    private String academicYear;
    private int semester;
    private double totalScore;
    private String ranking;

    /**
     * Constructor for TrainingScore
     * @param scoreId Unique score ID
     * @param studentId Student ID
     * @param academicYear Academic year
     * @param semester Semester number
     */
    public TrainingScore(int scoreId, String studentId, String academicYear, int semester) {
        this.scoreId = scoreId;
        this.studentId = studentId;
        this.academicYear = academicYear;
        this.semester = semester;
        classifyRanking();
    }

    /**
     * Calculates total score based on activity points
     * @param activityPoint Points from activities (max 50)
     */
    public void calculateScore(double activityPoint) {
        double baseScore = 50;
        double activityScore = Math.min(activityPoint, 50);
        totalScore = baseScore + activityScore;
        classifyRanking();
    }

    /**
     * Classifies ranking based on total score
     */
    public void classifyRanking() {
        if (totalScore >= 90) ranking = "Excellent";
        else if (totalScore >= 80) ranking = "Good";
        else if (totalScore >= 65) ranking = "Average";
        else if (totalScore >= 50) ranking = "Below Average";
        else ranking = "Weak";
    }

    /**
     * Displays training score report
     */
    public void getTrainingReport() {
        System.out.println("┌─────────────────────────────────────────────────────┐");
        System.out.println("│               TRAINING SCORE REPORT                 │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s: %-35s │\n", "Report ID", scoreId);
        System.out.printf("│ %-15s: %-35s │\n", "Student ID", studentId);
        System.out.printf("│ %-15s: %-35s │\n", "Academic Year", academicYear);
        System.out.printf("│ %-15s: %-35d │\n", "Semester", semester);
        System.out.printf("│ %-15s: %-35.1f │\n", "Total Score", totalScore);
        System.out.printf("│ %-15s: %-35s │\n", "Ranking", ranking);
        System.out.println("└─────────────────────────────────────────────────────┘");
    }

    // Getters and Setters
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