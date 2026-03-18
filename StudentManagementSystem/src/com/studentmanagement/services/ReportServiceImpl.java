// Nhan
package com.studentmanagement.services;

import com.studentmanagement.models.Student;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ReportServiceImpl implements ReportService {

    private List<Student> studentList;

    public ReportServiceImpl(List<Student> studentList) {
        this.studentList = studentList != null ? studentList : new ArrayList<>();
    }

    @Override
    public Map<String, Object> getClassStatistics(String classId) {
        int count = 0;
        double sumGpa = 0;
        double sumTrainingScore = 0;

        for (Student s : studentList) {
            if (s.getClassId() != null && s.getClassId().equalsIgnoreCase(classId)) {
                count++;
                sumGpa += s.getGpa();
                sumTrainingScore += s.getTrainingScore();
            }
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("Total students", count);
        stats.put("Average GPA", count > 0 ? (sumGpa / count) : 0.0);
        stats.put("Average Training Score", count > 0 ? (sumTrainingScore / count) : 0.0);

        return stats;
    }

    @Override
    public Map<String, Integer> getGPAStatistics() {
        Map<String, Integer> gpaStats = new LinkedHashMap<>();
        gpaStats.put("< 2.0", 0);
        gpaStats.put("2.0 - 2.5", 0);
        gpaStats.put("2.5 - 3.0", 0);
        gpaStats.put("> 3.0", 0);

        for (Student s : studentList) {
            double gpa = s.getGpa();
            if (gpa < 2.0) {
                gpaStats.put("< 2.0", gpaStats.get("< 2.0") + 1);
            } else if (gpa <= 2.5) {
                gpaStats.put("2.0 - 2.5", gpaStats.get("2.0 - 2.5") + 1);
            } else if (gpa <= 3.0) {
                gpaStats.put("2.5 - 3.0", gpaStats.get("2.5 - 3.0") + 1);
            } else {
                gpaStats.put("> 3.0", gpaStats.get("> 3.0") + 1);
            }
        }
        return gpaStats;
    }

    @Override
    public List<Student> getTop10Students() {
        List<Student> eligibleStudents = new ArrayList<>();

        for (Student s : studentList) {
            boolean isDisciplined = s.getStatus() != null && s.getStatus().equalsIgnoreCase("Disciplined");
            if (s.getGpa() >= 3.2 && s.getTrainingScore() >= 80 && !isDisciplined) {
                eligibleStudents.add(s);
            }
        }

        eligibleStudents.sort(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                double score1 = ((s1.getGpa() / 4.0) * 100) * 0.7 + (s1.getTrainingScore() * 0.3);
                double score2 = ((s2.getGpa() / 4.0) * 100) * 0.7 + (s2.getTrainingScore() * 0.3);
                return Double.compare(score2, score1);
            }
        });

        return eligibleStudents.size() > 10 ? eligibleStudents.subList(0, 10) : eligibleStudents;
    }

    @Override
    public Map<String, Integer> getClassificationCount() {
        Map<String, Integer> stats = new LinkedHashMap<>();
        stats.put("Excellent", 0);
        stats.put("Good", 0);
        stats.put("Average", 0);
        stats.put("Below Average", 0);
        stats.put("Weak", 0);

        for (Student s : studentList) {
            double gpa = s.getGpa();
            if (gpa >= 3.6) {
                stats.put("Excellent", stats.get("Excellent") + 1);
            } else if (gpa >= 3.2) {
                stats.put("Good", stats.get("Good") + 1);
            } else if (gpa >= 2.5) {
                stats.put("Average", stats.get("Average") + 1);
            } else if (gpa >= 2.0) {
                stats.put("Below Average", stats.get("Below Average") + 1);
            } else {
                stats.put("Weak", stats.get("Weak") + 1);
            }
        }
        return stats;
    }

    @Override
    public void generateSummaryReport() {
        System.out.println("\n==============================================");
        System.out.println("          OVERALL SUMMARY REPORT              ");
        System.out.println("==============================================");
        System.out.println("Total students: " + studentList.size());

        Map<String, Integer> classification = getClassificationCount();
        System.out.println("\n--- Academic Classification ---");
        for (Map.Entry<String, Integer> entry : classification.entrySet()) {
            double percentage = studentList.isEmpty() ? 0 : (entry.getValue() * 100.0 / studentList.size());
            System.out.printf("- %-12s: %d students (%.1f%%)\n", entry.getKey(), entry.getValue(), percentage);
        }

        System.out.println("\n--- Top Outstanding Students ---");
        List<Student> top10 = getTop10Students();
        for (int i = 0; i < top10.size(); i++) {
            Student s = top10.get(i);
            System.out.printf("%d. %s - Class: %s - GPA: %.2f - Training: %.1f\n",
                    (i + 1), s.getFullName(), s.getClassId(), s.getGpa(), s.getTrainingScore());
        }
        System.out.println("==============================================\n");
    }

    @Override
    public void exportStatisticsReport(String filePath) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(filePath);
            java.io.PrintWriter printWriter = new java.io.PrintWriter(writer);

            printWriter.println("==============================================");
            printWriter.println("     STATISTICS REPORT");
            printWriter.println("==============================================");
            printWriter.println("Export Date: " + new java.util.Date());
            printWriter.println("Total students: " + studentList.size());
            printWriter.println();

            java.util.Map<String, Integer> classification = getClassificationCount();
            printWriter.println("--- ACADEMIC CLASSIFICATION ---");
            for (java.util.Map.Entry<String, Integer> entry : classification.entrySet()) {
                double percentage = studentList.isEmpty() ? 0
                        : (entry.getValue() * 100.0 / studentList.size());
                printWriter.printf("%s: %d students (%.1f%%)\n",
                        entry.getKey(), entry.getValue(), percentage);
            }
            printWriter.println();

            java.util.Map<String, Integer> gpaStats = getGPAStatistics();
            printWriter.println("--- GPA DISTRIBUTION ---");
            for (java.util.Map.Entry<String, Integer> entry : gpaStats.entrySet()) {
                double percentage = studentList.isEmpty() ? 0
                        : (entry.getValue() * 100.0 / studentList.size());
                printWriter.printf("%s: %d students (%.1f%%)\n",
                        entry.getKey(), entry.getValue(), percentage);
            }
            printWriter.println();

            printWriter.println("--- TOP 10 OUTSTANDING STUDENTS ---");
            java.util.List<Student> top10 = getTop10Students();
            for (int i = 0; i < top10.size(); i++) {
                Student s = top10.get(i);
                printWriter.printf("%d. %s - Class: %s - GPA: %.2f - Training: %.1f\n",
                        i + 1, s.getFullName(), s.getClassId(), s.getGpa(), s.getTrainingScore());
            }

            printWriter.println("==============================================");
            printWriter.close();

            System.out.println("✅ Report exported to file: " + filePath);

        } catch (java.io.IOException e) {
            System.out.println("❌ Error exporting file: " + e.getMessage());
        }
    }

    public void exportToCSV(String filePath) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(filePath);
            java.io.PrintWriter printWriter = new java.io.PrintWriter(writer);

            printWriter.println("Student ID,Full Name,Class,GPA,Training Score,Classification");

            for (Student s : studentList) {
                printWriter.printf("%s,%s,%s,%.2f,%.1f,%s\n",
                        s.getStudentId(),
                        s.getFullName(),
                        s.getClassId(),
                        s.getGpa(),
                        s.getTrainingScore(),
                        getStudentRanking(s.getGpa())
                );
            }

            printWriter.close();
            System.out.println("✅ CSV exported to file: " + filePath);

        } catch (java.io.IOException e) {
            System.out.println("❌ Error exporting CSV: " + e.getMessage());
        }
    }

    private String getStudentRanking(double gpa) {
        if (gpa >= 3.6) {
            return "Excellent";
        }
        if (gpa >= 3.2) {
            return "Good";
        }
        if (gpa >= 2.5) {
            return "Average";
        }
        if (gpa >= 2.0) {
            return "Below Average";
        }
        return "Weak";
    }
}