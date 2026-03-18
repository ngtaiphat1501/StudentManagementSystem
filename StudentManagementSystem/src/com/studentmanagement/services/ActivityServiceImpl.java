/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.studentmanagement.services;

import com.studentmanagement.models.Activity;
import com.studentmanagement.models.Student;
import com.studentmanagement.models.TrainingScore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author TUF GAMING
 */
public class ActivityServiceImpl implements ActivityService {

    private List<Activity> activities;
    private List<TrainingScore> trainingScores;
    private StudentService studentService;
    private int nextActivityId = 1;
    private int nextScoreId = 1;

    public ActivityServiceImpl(StudentService studentService) {
        this.studentService = studentService;
        this.activities = new ArrayList<>();
        this.trainingScores = new ArrayList<>();
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<TrainingScore> getTrainingScores() {
        return trainingScores;
    }

    public void setTrainingScores(List<TrainingScore> trainingScores) {
        this.trainingScores = trainingScores;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public int getNextActivityId() {
        return nextActivityId;
    }

    public void setNextActivityId(int nextActivityId) {
        this.nextActivityId = nextActivityId;
    }

    public int getNextScoreId() {
        return nextScoreId;
    }

    public void setNextScoreId(int nextScoreId) {
        this.nextScoreId = nextScoreId;
    }

    // dữ liệu tạm thời
    public void initializeSampleData() {
        if (activities == null) {
            activities = new ArrayList<>();
        }
        if (trainingScores == null) {
            trainingScores = new ArrayList<>();
        }
        try {
            java.util.Date now = new java.util.Date();
            activities.add(new Activity(nextActivityId++, "CE200968", "The thao",
                    "Giải bóng đá sinh viên", "Đoàn trường", now, now));
            activities.add(new Activity(nextActivityId++, "CE200968", "Hoc thuat",
                    "Olympic Tin học", "Khoa CNTT", now, now));
            activities.add(new Activity(nextActivityId++, "CE200969", "Tinh Nguyen",
                    "Hiến máu nhân đạo", "Hội sinh viên", now, now));

            trainingScores.add(new TrainingScore(nextScoreId++, "CE200968", "2023-2024", 1));
            trainingScores.add(new TrainingScore(nextScoreId++, "CE200969", "2023-2024", 1));

            System.out.println("Đã tạo dữ liệu mẫu cho hoạt động");
        } catch (Exception e) {
            System.out.println("Lỗi tạo dữ liệu mẫu: " + e.getMessage());
        }
    }

    // thêm hoạt động
    @Override
    public boolean addActivity(String studentId, String activityType, String activityName, String organization, Date startDate, Date endDate, int hourSpent) {
        Student student = ((StudentServiceImpl) studentService).findStudentByStudentId(studentId); // ép kiểu tìm kiếm học sinh
        if (student == null) {    // Kiểm tra sinh viên tồn tại
            System.out.println("Không tìm thấy học sinh...");
            return false;
        }
        // Tạo hoạt động mới
        Activity activity = new Activity(nextActivityId, studentId, activityType, activityName, organization, startDate, endDate);

        activities.add(activity);
        activity.setHourSpent(hourSpent);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        System.out.print("Đã hoàn thành");

        // Thêm hoạt động vào sinh viên
        student.addActivity(activity);

        calculateTrainingScore(studentId);

        System.out.println("Thêm hoạt động thành công!");
        activity.displayActivityInfo();

        return true;
    }

    @Override
    public double calculateTrainingScore(String studentId) {
        Student student = ((StudentServiceImpl) studentService).findStudentByStudentId(studentId);
        if (student == null) {
            return 0.0;
        }

        // Tính tổng điểm từ các hoạt động
        double totalPoints = 0;
        for (Activity activity : student.getActivities()) {
            totalPoints += activity.getPointEarned();
        }

        // Cập nhật điểm rèn luyện cho sinh viên
        student.calculateTrainingScore();
        // Cập nhật hoặc tạo TrainingScore
        TrainingScore score = findTrainingScore(studentId, "2023-2024", 1);
        if (score == null) {
            score = new TrainingScore(nextScoreId++, studentId, "2023-2024", 1);
            trainingScores.add(score);
        }
        score.calculateScore(totalPoints);

        return student.getTrainingScore();
    }

    @Override
    public String classifyTrainingRanking(String studentId) {
        TrainingScore score = findTrainingScore(studentId, "2023-2024", 1);
        if (score == null) {
            return "Chưa xếp loại";
        }

        Student student = ((StudentServiceImpl) studentService).findStudentByStudentId(studentId);
        if (student != null) {
            return student.getRanking();
        }

        return score.getRanking();

    }

    @Override
    public List<Activity> getActivityReport(String StudentId) {
        List<Activity> studentActivities = new ArrayList<>();

        for (Activity activtiy : activities) {
            if (activtiy.getStudentId().equals(StudentId)) {
                studentActivities.add(activtiy);
            }
        }

        return studentActivities;

    }

    @Override
    public void exportTrainingReport() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║            BÁO CÁO RÈN LUYỆN                ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        System.out.println("📊 Tổng số hoạt động: " + activities.size());
        System.out.println("📊 Tổng số điểm rèn luyện: " + trainingScores.size());

        // Thống kê xếp loại
        int excellent = 0, good = 0, average = 0, weak = 0;
        for (TrainingScore score : trainingScores) {
            switch (score.getRanking()) {
                case "Xuất sắc":
                    excellent++;
                    break;
                case "Tốt":
                    good++;
                    break;
                case "Khá":
                    average++;
                    break;
                case "Trung bình":
                    average++;
                    break;
                case "Yếu":
                    weak++;
                    break;
            }
        }

        System.out.println("\n PHÂN LOẠI RÈN LUYỆN:");
        System.out.println("Xuất sắc: " + excellent);
        System.out.println("Tốt: " + good);
        System.out.println("Khá/Trung bình: " + average);
        System.out.println("Yếu: " + weak);
    }

    // tìm kiếm điểm rèn luyện
    public TrainingScore findTrainingScore(String studentId, String academicYear, int semester) {
        for (TrainingScore score : trainingScores) {
            if (score.getStudentId().equals(studentId)) {
                if (score.getAcademicYear().equals(academicYear)) {
                    if (score.getSemester() == semester) {
                        return score;
                    }
                }
            }
        }
        return null;
    }
}
