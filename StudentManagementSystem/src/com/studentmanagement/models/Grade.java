// Khanh
package com.studentmanagement.models;

import java.io.Serializable;

/**
 *
 * @author TUF GAMING
 */
public class Grade implements Serializable {

    private int gradeId;
    private int enrollmentId;
    private double attendanceScore;
    private double midtermScore;
    private double finalScore;
    private double totalScore;
    private String letterGrade;

    public Grade(int gradeId, int enrollmentId, double attendanceScore,
            double midtermScore, double finalScore) {
        this.gradeId = gradeId;
        this.enrollmentId = enrollmentId;
        this.attendanceScore = attendanceScore;
        this.midtermScore = midtermScore;
        this.finalScore = finalScore;
        calculateTotalScore();
        calculateLetterGrade();
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public double getAttendanceScore() {
        return attendanceScore;
    }

    public void setAttendanceScore(double attendanceScore) {
        this.attendanceScore = attendanceScore;
        calculateTotalScore();
        calculateLetterGrade();
    }

    public double getMidtermScore() {
        return midtermScore;
    }

    public void setMidtermScore(double midtermScore) {
        this.midtermScore = midtermScore;
        calculateTotalScore();
        calculateLetterGrade();
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
        calculateTotalScore();
        calculateLetterGrade();
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public void enterGrade(double attendanceScore, double midtermScore, double finalScore) {
        this.attendanceScore = attendanceScore;
        this.midtermScore = midtermScore;
        this.finalScore = finalScore;

        calculateTotalScore();
        calculateLetterGrade();
        System.out.println("Grade entry successful!");
    }

    public void calculateTotalScore() {
        totalScore = attendanceScore * 0.1 + midtermScore * 0.3 + finalScore * 0.6;
    }

    public void calculateLetterGrade() {
        if (totalScore >= 8.5) {
            letterGrade = "A";
        } else if (totalScore >= 8.0) {
            letterGrade = "B+";
        } else if (totalScore >= 7.0) {
            letterGrade = "B";
        } else if (totalScore >= 6.5) {
            letterGrade = "C+";
        } else if (totalScore >= 5.5) {
            letterGrade = "C";
        } else if (totalScore >= 5.0) {
            letterGrade = "D+";
        } else if (totalScore >= 4.0) {
            letterGrade = "D";
        } else {
            letterGrade = "F";
        }
    }

    public void displayGradeInfo() {
        System.out.printf("│ %-15s: %-35.1f │\n", "Attendance", attendanceScore);
        System.out.printf("│ %-15s: %-35.1f │\n", "Midterm", midtermScore);
        System.out.printf("│ %-15s: %-35.1f │\n", "Final", finalScore);
        System.out.printf("│ %-15s: %-35.1f │\n", "Total Score", totalScore);
        System.out.printf("│ %-15s: %-35s │\n", "Letter Grade", letterGrade);
        System.out.printf("│ %-15s: %-35s │\n", "Classification", getGradeText());
    }

    public String getGradeText() {
        switch (letterGrade) {
            case "A":
                return "Excellent";
            case "B+":
                return "Very Good";
            case "B":
                return "Good";
            case "C+":
                return "Above Average";
            case "C":
                return "Average";
            case "D+":
                return "Below Average";
            case "D":
                return "Poor";
            case "F":
                return "Fail";
            default:
                return "No Grade";
        }
    }

    public boolean hasAcademicWarning() {
        return totalScore < 4;
    }
}