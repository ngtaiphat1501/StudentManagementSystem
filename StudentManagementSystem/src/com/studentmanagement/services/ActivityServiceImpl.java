package com.studentmanagement.services;

import com.studentmanagement.models.Activity;
import com.studentmanagement.models.Student;
import com.studentmanagement.models.TrainingScore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation of ActivityService for activity and training score management
 */
public class ActivityServiceImpl implements ActivityService {

    private List<Activity> activities;
    private List<TrainingScore> trainingScores;
    private StudentService studentService;
    private int nextActivityId = 1;
    private int nextScoreId = 1;

    /**
     * Constructor for ActivityServiceImpl
     * @param studentService Student service instance
     */
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

    /**
     * Initializes sample activity data
     */
    public void initializeSampleData() {
        if (activities == null) {
            activities = new ArrayList<>();
        }
        if (trainingScores == null) {
            trainingScores = new ArrayList<>();
        }
        try {
            java.util.Date now = new java.util.Date();

            activities.add(new Activity(nextActivityId++, "CE200968", "Sports",
                    "Student Football Tournament", "Youth Union", now, now, 10));

            activities.add(new Activity(nextActivityId++, "CE200968", "Academic",
                    "IT Olympiad", "IT Faculty", now, now, 15));

            activities.add(new Activity(nextActivityId++, "CE200969", "Volunteer",
                    "Blood Donation", "Student Association", now, now, 8));

            trainingScores.add(new TrainingScore(nextScoreId++, "CE200968", "2023-2024", 1));
            trainingScores.add(new TrainingScore(nextScoreId++, "CE200969", "2023-2024", 1));

            System.out.println(" Sample activity data created");
        } catch (Exception e) {
            System.out.println(" Error creating sample data: " + e.getMessage());
        }
    }

    @Override
    public boolean addActivity(String studentId, String activityType, String activityName,
            String organization, Date startDate, Date endDate, int hourSpent) {
        Student student = ((StudentServiceImpl) studentService).findStudentByStudentId(studentId);
        if (student == null) {
            System.out.println(" Student not found...");
            return false;
        }

        // Create new activity
        Activity activity = new Activity(nextActivityId++, studentId, activityType,
                activityName, organization, startDate, endDate, hourSpent);
        activity.setHourSpent(hourSpent);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setStatus("Completed");

        // Calculate points based on hourSpent
        activity.calculateTrainingScore();

        activities.add(activity);
        student.addActivity(activity);

        calculateTrainingScore(studentId);

        System.out.println("✅ Activity added successfully!");
        activity.displayActivityInfo();

        return true;
    }

    @Override
    public double calculateTrainingScore(String studentId) {
        Student student = ((StudentServiceImpl) studentService).findStudentByStudentId(studentId);
        if (student == null) {
            return 0.0;
        }

        // Calculate total points from activities
        double totalPoints = 0;
        for (Activity activity : student.getActivities()) {
            totalPoints += activity.getPointEarned();
        }

        // Update training score for student
        student.calculateTrainingScore();
        
        // Update or create TrainingScore
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
            return "Not ranked";
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

        for (Activity activity : activities) {
            if (activity.getStudentId().equals(StudentId)) {
                studentActivities.add(activity);
            }
        }

        return studentActivities;
    }

    @Override
    public void exportTrainingReport() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║            TRAINING REPORT                   ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        System.out.println(" Total activities: " + activities.size());
        System.out.println(" Total training scores: " + trainingScores.size());

        // Classification statistics
        int excellent = 0, good = 0, average = 0, weak = 0;
        for (TrainingScore score : trainingScores) {
            switch (score.getRanking()) {
                case "Excellent":
                case "Xuất sắc":
                    excellent++;
                    break;
                case "Good":
                case "Tốt":
                    good++;
                    break;
                case "Average":
                case "Khá":
                case "Trung bình":
                    average++;
                    break;
                case "Weak":
                case "Yếu":
                    weak++;
                    break;
            }
        }

        System.out.println("\n TRAINING CLASSIFICATION:");
        System.out.println("Excellent: " + excellent);
        System.out.println("Good: " + good);
        System.out.println("Average: " + average);
        System.out.println("Weak: " + weak);
    }

    /**
     * Finds training score by student ID, academic year, and semester
     * @param studentId Student ID
     * @param academicYear Academic year
     * @param semester Semester
     * @return TrainingScore object if found, null otherwise
     */
    public TrainingScore findTrainingScore(String studentId, String academicYear, int semester) {
        for (TrainingScore score : trainingScores) {
            if (score.getStudentId().equals(studentId)
                    && score.getAcademicYear().equals(academicYear)
                    && score.getSemester() == semester) {
                return score;
            }
        }
        return null;
    }
}