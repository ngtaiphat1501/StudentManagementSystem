package com.studentmanagement.menus;

import com.studentmanagement.managers.*;
import com.studentmanagement.models.*;
import com.studentmanagement.services.*;
import com.studentmanagement.utils.ConsoleUtils;
import java.util.List;
import java.util.Scanner;

/**
 * Staff menu with read-only access to courses, students, and reports
 */
public class StaffMenu {
    private Scanner scanner;
    private UserManager userManager;
    private StudentManager studentManager;
    private CourseManager courseManager;
    private AcademicService academicService;
    private ActivityService activityService;
    private ReportService reportService;
    
    /**
     * Constructor - initializes services
     * @param scanner Scanner for user input
     * @param userManager User manager instance
     * @param studentManager Student manager instance
     * @param courseManager Course manager instance
     */
    public StaffMenu(Scanner scanner, UserManager userManager, 
                     StudentManager studentManager, CourseManager courseManager) {
        this.scanner = scanner;
        this.userManager = userManager;
        this.studentManager = studentManager;
        this.courseManager = courseManager;
        
        this.academicService = new AcademicServiceImpl(
            courseManager.getCourses(),
            new java.util.ArrayList<>(),
            new java.util.ArrayList<>(),
            new StudentServiceImpl()
        );
        
        this.activityService = new ActivityServiceImpl(new StudentServiceImpl());
        this.reportService = new ReportServiceImpl(studentManager.getStudents());
    }
    
    /**
     * Displays the staff menu and handles user choices
     */
    public void showMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("STAFF MENU");
            
            System.out.println(" Welcome: " + userManager.getCurrentUser().getFullName());
            System.out.println("═══════════════════════════════════════════");
            System.out.println("1.  VIEW COURSE LIST");
            System.out.println("2.  VIEW STUDENT LIST");
            System.out.println("3.  SEARCH STUDENTS");
            System.out.println("4.  VIEW STATISTICS REPORTS");
            System.out.println("5.  VIEW STUDENT TRANSCRIPT");
            System.out.println("6.  CHANGE PASSWORD");
            System.out.println("7.  LOGOUT");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Choose function (1-7): ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    courseManager.displayAllCourses();
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "2":
                    studentManager.displayAllStudents();
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "3":
                    studentManager.searchStudent(scanner);
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "4":
                    reportService.generateSummaryReport();
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "5":
                    viewStudentTranscript();
                    break;
                case "6":
                    userManager.changePassword(scanner);
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "7":
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
     * Displays transcript for a specific student
     */
    private void viewStudentTranscript() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        
        List<Grade> transcript = academicService.getTranscript(studentId);
        
        if (transcript.isEmpty()) {
            ConsoleUtils.showWarning("Student has no grades!");
        } else {
            ConsoleUtils.showHeader("STUDENT TRANSCRIPT");
            for (Grade g : transcript) {
                g.displayGradeInfo();
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
}