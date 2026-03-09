//Khanh
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
        this.totalScore = totalScore;

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
        System.out.println("Nhập điểm thành công!");
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
        System.out.printf("│ %-15s: %-35.1f │\n", "Điểm chuyên cần", attendanceScore);
        System.out.printf("│ %-15s: %-35.1f │\n", "Điểm giữa kỳ", midtermScore);
        System.out.printf("│ %-15s: %-35.1f │\n", "Điểm cuối kỳ", finalScore);
        System.out.printf("│ %-15s: %-35.1f │\n", "Điểm tổng kết", totalScore);
        System.out.printf("│ %-15s: %-35s │\n", "Điểm chữ", letterGrade);
        System.out.printf("│ %-15s: %-35s │\n", "Xếp loại", getGradeText());
    }

    public String getGradeText() {
        switch (letterGrade) {
            case "A":
                return "Xuất sắc";
            case "B+":
                return "Giỏi";
            case "B":
                return "Khá";
            case "C+":
                return "Trung bình khá";
            case "C":
                return "Trung bình";
            case "D+":
                return "Trung bình yếu";
            case "D":
                return "Yếu";
            case "F":
                return "Kém";
            default:
                return "Chưa có điểm";
        }
    }

    public boolean hasAcademicWarning() {
        return totalScore < 4;
    }
}
