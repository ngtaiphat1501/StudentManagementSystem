//Nhan
package com.studentmanagement.services;

import com.studentmanagement.models.Student;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ReportServiceImpl implements ReportService {

    private List<Student> studentList;

    // Constructor tạm thời truyền thẳng List Sinh viên vào để test
    public ReportServiceImpl(List<Student> studentList) {
        this.studentList = studentList != null ? studentList : new ArrayList<>();
    }

    // --- 4.1: THỐNG KÊ THEO LỚP ---
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
        stats.put("Sĩ số", count);
        stats.put("GPA Trung bình", count > 0 ? (sumGpa / count) : 0.0);
        stats.put("Điểm RL Trung bình", count > 0 ? (sumTrainingScore / count) : 0.0);

        return stats;
    }

    // --- 4.2: THỐNG KÊ THEO GPA ---
    @Override
    public Map<String, Integer> getGPAStatistics() {
        // Dùng LinkedHashMap để giữ đúng thứ tự khi in ra
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

    // --- 4.3: TOP 10 SINH VIÊN XUẤT SẮC NHẤT ---
    @Override
    public List<Student> getTop10Students() {
        List<Student> eligibleStudents = new ArrayList<>();

        // Bước 1: Lọc điều kiện (GPA >= 3.2, RL >= 80, không bị kỷ luật)
        for (Student s : studentList) {
            boolean isDisciplined = s.getStatus() != null && s.getStatus().equalsIgnoreCase("Kỷ luật");
            if (s.getGpa() >= 3.2 && s.getTrainingScore() >= 80 && !isDisciplined) {
                eligibleStudents.add(s);
            }
        }

        // Bước 2: Sắp xếp theo công thức: (GPA quy đổi thang 100 * 70%) + (RL * 30%)
        eligibleStudents.sort(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                // Giả định GPA là hệ 4.0, ta quy đổi ra hệ 100: (GPA / 4.0) * 100
                double score1 = ((s1.getGpa() / 4.0) * 100) * 0.7 + (s1.getTrainingScore() * 0.3);
                double score2 = ((s2.getGpa() / 4.0) * 100) * 0.7 + (s2.getTrainingScore() * 0.3);
                
                // Sắp xếp giảm dần (từ cao xuống thấp)
                return Double.compare(score2, score1);
            }
        });

        // Bước 3: Chỉ lấy tối đa 10 bạn
        return eligibleStudents.size() > 10 ? eligibleStudents.subList(0, 10) : eligibleStudents;
    }

    // --- 4.4: XẾP LOẠI SINH VIÊN (HỌC LỰC) ---
    @Override
    public Map<String, Integer> getClassificationCount() {
        Map<String, Integer> stats = new LinkedHashMap<>();
        stats.put("Xuất sắc", 0);
        stats.put("Giỏi", 0);
        stats.put("Khá", 0);
        stats.put("Trung bình", 0);
        stats.put("Yếu", 0);

        for (Student s : studentList) {
            double gpa = s.getGpa();
            if (gpa >= 3.6) stats.put("Xuất sắc", stats.get("Xuất sắc") + 1);
            else if (gpa >= 3.2) stats.put("Giỏi", stats.get("Giỏi") + 1);
            else if (gpa >= 2.5) stats.put("Khá", stats.get("Khá") + 1);
            else if (gpa >= 2.0) stats.put("Trung bình", stats.get("Trung bình") + 1);
            else stats.put("Yếu", stats.get("Yếu") + 1);
        }
        return stats;
    }

    // --- 4.5: BÁO CÁO TỔNG HỢP ---
    @Override
    public void generateSummaryReport() {
        System.out.println("\n==============================================");
        System.out.println("          BÁO CÁO TỔNG QUAN TOÀN TRƯỜNG       ");
        System.out.println("==============================================");
        System.out.println("Tổng số sinh viên: " + studentList.size());
        
        Map<String, Integer> classification = getClassificationCount();
        System.out.println("\n--- Tỷ lệ xếp loại học tập ---");
        for (Map.Entry<String, Integer> entry : classification.entrySet()) {
            double percentage = studentList.isEmpty() ? 0 : (entry.getValue() * 100.0 / studentList.size());
            System.out.printf("- %-12s: %d SV (%.1f%%)\n", entry.getKey(), entry.getValue(), percentage);
        }

        System.out.println("\n--- Top Sinh Viên Xuất Sắc Nhất ---");
        List<Student> top10 = getTop10Students();
        for (int i = 0; i < top10.size(); i++) {
            Student s = top10.get(i);
            System.out.printf("%d. %s - Lớp: %s - GPA: %.2f - RL: %.1f\n", 
                    (i+1), s.getFullName(), s.getClassId(), s.getGpa(), s.getTrainingScore());
        }
        System.out.println("==============================================\n");
    }

    @Override
    public void exportStatisticsReport(String filePath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}