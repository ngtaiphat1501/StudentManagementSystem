package com.studentmanagement.menus;

import com.studentmanagement.managers.*;
import com.studentmanagement.models.*;
import com.studentmanagement.services.*;
import com.studentmanagement.utils.ConsoleUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Student menu with personal information, course registration, and grade viewing
 */
public class StudentMenu {

    private Scanner scanner;
    private UserManager userManager;
    private StudentManager studentManager;
    private CourseManager courseManager;
    private Student currentStudent;
    private AcademicService academicService;
    private ActivityService activityService;

    /**
     * Constructor - links user account to student record
     * @param scanner Scanner for user input
     * @param userManager User manager instance
     * @param studentManager Student manager instance
     * @param courseManager Course manager instance
     */
    public StudentMenu(Scanner scanner, UserManager userManager,
            StudentManager studentManager, CourseManager courseManager) {
        this.scanner = scanner;
        this.userManager = userManager;
        this.studentManager = studentManager;
        this.courseManager = courseManager;

        String username = userManager.getCurrentUser().getUsername();
        String email = userManager.getCurrentUser().getEmail();
        String fullName = userManager.getCurrentUser().getFullName();

        // 1. Find by username
        this.currentStudent = studentManager.findStudentByStudentId(username);
        
        // 2. Find by email
        if (this.currentStudent == null) {
            this.currentStudent = studentManager.findStudentByEmail(email);
        }
        
        // 3. Find by name
        if (this.currentStudent == null) {
            this.currentStudent = studentManager.findStudentByName(fullName);
        }
        
        // 4. Auto-create new student if not found
        if (this.currentStudent == null && userManager.getCurrentUser().getRole().equalsIgnoreCase("STUDENT")) {
            try {
                String newStudentId = "STU" + String.format("%03d", studentManager.getStudents().size() + 1);
                Student newStudent = new Student(
                        "S" + String.format("%03d", studentManager.getStudents().size() + 1),
                        newStudentId,
                        fullName,
                        new java.util.Date(),
                        "Unknown",
                        "",
                        email,
                        "",
                        "Unknown",
                        new java.util.Date(),
                        "Studying"
                );
                
                studentManager.getStudents().add(newStudent);
                this.currentStudent = newStudent;
                
                System.out.println(" Auto-created student profile for: " + fullName);
                System.out.println("   Student ID: " + newStudentId);
            } catch (Exception e) {
                System.out.println(" Error auto-creating student: " + e.getMessage());
            }
        }

        if (this.currentStudent != null) {
            System.out.println("✅ Linked with student: " + this.currentStudent.getStudentId());
        }

        this.academicService = new AcademicServiceImpl(
                courseManager.getCourses(),
                new ArrayList<>(),
                new ArrayList<>(),
                new StudentServiceImpl()
        );

        this.activityService = new ActivityServiceImpl(new StudentServiceImpl());
    }

    /**
     * Removes accents from a string for accent-insensitive search
     * @param str Input string
     * @return String with accents removed
     */
    private String removeAccent(String str) {
        if (str == null) return "";
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("")
                   .replace('đ', 'd')
                   .replace('Đ', 'D');
        } catch (Exception e) {
            return str;
        }
    }

    /**
     * Displays the student menu and handles user choices
     */
    public void showMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("STUDENT MENU");

            System.out.println(" Welcome: " + userManager.getCurrentUser().getFullName());
            if (currentStudent != null) {
                System.out.println(" Student ID: " + currentStudent.getStudentId());
                System.out.println(" Class: " + currentStudent.getClassId());
            }
            System.out.println("═══════════════════════════════════════════");
            System.out.println("1.  VIEW PERSONAL INFORMATION");
            System.out.println("2.  VIEW COURSE LIST");
            System.out.println("3.  REGISTER COURSE");
            System.out.println("4.  VIEW TRANSCRIPT");
            System.out.println("5.  VIEW GPA");
            System.out.println("6.  VIEW TRAINING SCORE");
            System.out.println("7.  VIEW ACTIVITY HISTORY");
            System.out.println("8.  CHANGE PASSWORD");
            System.out.println("9.  LOGOUT");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Choose function (1-9): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewPersonalInfo();
                    break;
                case "2":
                    viewAllCourses();
                    break;
                case "3":
                    registerCourse();
                    break;
                case "4":
                    viewTranscript();
                    break;
                case "5":
                    viewGPA();
                    break;
                case "6":
                    viewTrainingScore();
                    break;
                case "7":
                    viewActivityHistory();
                    break;
                case "8":
                    changePassword();
                    break;
                case "9":
                    userManager.logout();
                    ConsoleUtils.showInfo("Logged out!");
                    return;
                default:
                    ConsoleUtils.showError("Invalid choice!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }

    /**
     * Displays personal information
     */
    private void viewPersonalInfo() {
        if (currentStudent != null) {
            currentStudent.displayInfo();
        } else {
            ConsoleUtils.showHeader("ACCOUNT INFORMATION");
            System.out.println("Username: " + userManager.getCurrentUser().getUsername());
            System.out.println("Full Name: " + userManager.getCurrentUser().getFullName());
            System.out.println("Email: " + userManager.getCurrentUser().getEmail());
            System.out.println("Role: " + userManager.getCurrentUser().getRole());
            ConsoleUtils.showWarning("No linked student information found!");
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Displays all available courses
     */
    private void viewAllCourses() {
        courseManager.displayAllCourses();
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Registers the current student for a course
     */
    private void registerCourse() {
        if (currentStudent == null) {
            ConsoleUtils.showError("Student information not found!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        ConsoleUtils.showHeader("COURSE REGISTRATION");

        courseManager.displayAllCourses();

        System.out.print("Enter course code to register (e.g., CT101): ");
        String courseCode = scanner.nextLine();

        Course course = courseManager.findCourseByCode(courseCode);
        if (course == null) {
            ConsoleUtils.showError("Course not found with code: " + courseCode);
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        System.out.print("Semester (1-9): ");
        String semester = scanner.nextLine();

        System.out.print("Academic year (e.g., 2023-2024): ");
        String academicYear = scanner.nextLine();

        boolean success = academicService.registerCourse(
                currentStudent.getStudentId(), course.getCourseId(), semester, academicYear);

        if (success) {
            ConsoleUtils.showSuccess("Course registration successful!");
        } else {
            ConsoleUtils.showError("Registration failed!");
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Displays the student's transcript
     */
    private void viewTranscript() {
        if (currentStudent == null) {
            ConsoleUtils.showError("Student information not found!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        List<Grade> transcript = academicService.getTranscript(currentStudent.getStudentId());

        if (transcript.isEmpty()) {
            ConsoleUtils.showWarning("You have no grades yet!");
        } else {
            ConsoleUtils.showHeader("YOUR TRANSCRIPT");
            for (Grade g : transcript) {
                g.displayGradeInfo();
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Calculates and displays the student's GPA
     */
    private void viewGPA() {
        if (currentStudent == null) {
            ConsoleUtils.showError("Student information not found!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        double gpa = academicService.calculateGPA(currentStudent.getStudentId());

        ConsoleUtils.showHeader("GPA INFORMATION");
        System.out.printf("Current GPA: %.2f\n", gpa);

        String classification;
        if (gpa >= 3.6) {
            classification = "Excellent";
        } else if (gpa >= 3.2) {
            classification = "Good";
        } else if (gpa >= 2.5) {
            classification = "Average";
        } else if (gpa >= 2.0) {
            classification = "Below Average";
        } else {
            classification = "Weak";
        }

        System.out.println("Classification: " + classification);

        if (gpa < 2.0) {
            ConsoleUtils.showWarning("You are on academic warning!");
        }

        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Displays the student's training score
     */
    private void viewTrainingScore() {
        if (currentStudent == null) {
            ConsoleUtils.showError("Student information not found!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        double score = activityService.calculateTrainingScore(currentStudent.getStudentId());
        String ranking = activityService.classifyTrainingRanking(currentStudent.getStudentId());

        ConsoleUtils.showHeader("TRAINING SCORE");
        System.out.printf("Training score: %.1f/100\n", score);
        System.out.println("Ranking: " + ranking);
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Displays the student's activity history
     */
    private void viewActivityHistory() {
        if (currentStudent == null) {
            ConsoleUtils.showError("Student information not found!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        List<Activity> activities = activityService.getActivityReport(currentStudent.getStudentId());

        if (activities.isEmpty()) {
            ConsoleUtils.showWarning("You have not participated in any activities!");
        } else {
            ConsoleUtils.showHeader("ACTIVITY HISTORY");
            for (Activity a : activities) {
                a.displayActivityInfo();
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Changes the user's password
     */
    private void changePassword() {
        userManager.changePassword(scanner);
        ConsoleUtils.pressEnterToContinue(scanner);
    }
}