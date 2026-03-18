package com.studentmanagement.menus;

import com.studentmanagement.managers.*;
import com.studentmanagement.models.*;
import com.studentmanagement.services.*;
import com.studentmanagement.utils.ConsoleUtils;
import com.studentmanagement.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Admin menu providing access to all system management functions
 */
public class AdminMenu {

    private Scanner scanner;
    private UserManager userManager;
    private StudentManager studentManager;
    private CourseManager courseManager;
    private AcademicService academicService;
    private ActivityService activityService;
    private ReportService reportService;

    /**
     * Constructor - initializes all services
     * @param scanner Scanner for user input
     * @param userManager User manager instance
     * @param studentManager Student manager instance
     * @param courseManager Course manager instance
     */
    public AdminMenu(Scanner scanner, UserManager userManager,
            StudentManager studentManager, CourseManager courseManager) {
        this.scanner = scanner;
        this.userManager = userManager;
        this.studentManager = studentManager;
        this.courseManager = courseManager;

        FileService fileService = new FileServiceImpl();

        List<Enrollment> enrollments = (List<Enrollment>) fileService.loadData("enrollments.dat");
        if (enrollments == null) {
            enrollments = new ArrayList<>();
        }

        List<Grade> grades = (List<Grade>) fileService.loadData("grades.dat");
        if (grades == null) {
            grades = new ArrayList<>();
        }

        // Initialize services
        this.academicService = new AcademicServiceImpl(
                courseManager.getCourses(),
                enrollments,
                grades,
                new StudentServiceImpl()
        );

        this.activityService = new ActivityServiceImpl(new StudentServiceImpl());
        this.reportService = new ReportServiceImpl(studentManager.getStudents());
    }

    /**
     * Displays the main admin menu and handles user choices
     */
    public void showMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("ADMIN MENU");

            System.out.println(" Welcome: " + userManager.getCurrentUser().getFullName());
            System.out.println("═══════════════════════════════════════════");
            System.out.println("1.  COURSE MANAGEMENT");
            System.out.println("2.  STUDENT MANAGEMENT");
            System.out.println("3.  GRADE MANAGEMENT");
            System.out.println("4.  ACTIVITY MANAGEMENT");
            System.out.println("5.  REPORTS & STATISTICS");
            System.out.println("6.  USER MANAGEMENT");
            System.out.println("7.  BACKUP & RESTORE");
            System.out.println("8.  CHANGE PASSWORD");
            System.out.println("9.  LOGOUT");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Choose function (1-9): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    courseManagementMenu();
                    break;
                case "2":
                    studentManagementMenu();
                    break;
                case "3":
                    gradeManagementMenu();
                    break;
                case "4":
                    activityManagementMenu();
                    break;
                case "5":
                    reportMenu();
                    break;
                case "6":
                    userManagementMenu();
                    break;
                case "7":
                    backupRestoreMenu();
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
     * Course management submenu
     */
    private void courseManagementMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("COURSE MANAGEMENT");

            System.out.println("1.  View all courses");
            System.out.println("2.  Add new course");
            System.out.println("3.  Update course information");
            System.out.println("4.  Delete course");
            System.out.println("5.  Search courses");
            System.out.println("6.  Course statistics");
            System.out.println("7.  Back");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    courseManager.displayAllCourses();
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "2":
                    courseManager.addCourse(scanner);
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "3":
                    courseManager.updateCourse(scanner);
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "4":
                    courseManager.deleteCourse(scanner);
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "5":
                    searchCourseMenu();
                    break;
                case "6":
                    courseManager.showCourseStatistics();
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "7":
                    return;
                default:
                    ConsoleUtils.showError("Invalid choice!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }

    /**
     * Course search submenu
     */
    private void searchCourseMenu() {
        ConsoleUtils.showHeader("SEARCH COURSES");

        System.out.println("1.  Search by name");
        System.out.println("2.  Search by department");
        System.out.println("3.  Search by teacher");
        System.out.println("4.  Back");
        System.out.print("Choose: ");

        String choice = scanner.nextLine();

        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();

        List<Course> results = null;

        switch (choice) {
            case "1":
                results = courseManager.findCoursesByName(keyword);
                break;
            case "2":
                results = courseManager.getCoursesByDepartment(keyword);
                break;
            case "3":
                results = courseManager.findCoursesByTeacher(keyword);
                break;
            default:
                return;
        }

        if (results == null || results.isEmpty()) {
            ConsoleUtils.showWarning("No courses found!");
        } else {
            ConsoleUtils.showSuccess("Found " + results.size() + " course(s):");
            for (Course c : results) {
                c.displayInfo();
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Student management submenu
     */
    private void studentManagementMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("STUDENT MANAGEMENT");

            System.out.println("1.  View all students");
            System.out.println("2.  Add new student");
            System.out.println("3.  Update student information");
            System.out.println("4.  Delete student");
            System.out.println("5.  Search students");
            System.out.println("6.  View class statistics");
            System.out.println("7.  Back");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    studentManager.displayAllStudents();
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "2":
                    studentManager.addStudent(scanner);
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "3":
                    studentManager.updateStudent(scanner);
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "4":
                    studentManager.deleteStudent(scanner);
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "5":
                    studentManager.searchStudent(scanner);
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "6":
                    viewClassStatistics();
                    break;
                case "7":
                    return;
                default:
                    ConsoleUtils.showError("Invalid choice!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }

    /**
     * Displays statistics for a specific class
     */
    private void viewClassStatistics() {
        System.out.print("Enter class ID: ");
        String classId = scanner.nextLine();

        java.util.Map<String, Object> stats = reportService.getClassStatistics(classId);

        ConsoleUtils.showHeader("STATISTICS FOR CLASS " + classId);
        System.out.println("Total students: " + stats.get("Total students"));
        System.out.printf("Average GPA: %.2f\n", stats.get("Average GPA"));
        System.out.printf("Average Training Score: %.1f\n", stats.get("Average Training Score"));

        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Grade management submenu
     */
    private void gradeManagementMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("GRADE MANAGEMENT");

            System.out.println("1.  Register course for student");
            System.out.println("2.  Enter grades");
            System.out.println("3.  View student transcript");
            System.out.println("4.  Calculate student GPA");
            System.out.println("5.  Check academic warning");
            System.out.println("6.  Back");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerCourseForStudent();
                    break;
                case "2":
                    enterGradeForStudent();
                    break;
                case "3":
                    viewStudentTranscript();
                    break;
                case "4":
                    calculateStudentGPA();
                    break;
                case "5":
                    checkAcademicWarning();
                    break;
                case "6":
                    return;
                default:
                    ConsoleUtils.showError("Invalid choice!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }

    /**
     * Registers a course for a student
     */
    private void registerCourseForStudent() {
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();

        Student student = studentManager.findStudentByStudentId(studentId);
        if (student == null) {
            ConsoleUtils.showError("Student not found!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        courseManager.displayAllCourses();

        System.out.print("Enter course code to register (e.g., CT101): ");
        String courseCode = scanner.nextLine();

        // Find course by course code (CT101) not system ID
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

        boolean success = academicService.registerCourse(studentId, course.getCourseId(), semester, academicYear);

        if (success) {
            ConsoleUtils.showSuccess("Registration successful!");
        } else {
            ConsoleUtils.showError("Registration failed!");
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Enters grades for a student in a course
     */
    private void enterGradeForStudent() {
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Course code: ");
        String courseId = scanner.nextLine();

        System.out.print("Attendance score (0-10): ");
        double attendance = Double.parseDouble(scanner.nextLine());

        System.out.print("Midterm score (0-10): ");
        double midterm = Double.parseDouble(scanner.nextLine());

        System.out.print("Final score (0-10): ");
        double finalScore = Double.parseDouble(scanner.nextLine());

        boolean success = academicService.enterGrade(studentId, courseId, attendance, midterm, finalScore);

        if (success) {
            ConsoleUtils.showSuccess("Grade entry successful!");
        } else {
            ConsoleUtils.showError("Grade entry failed!");
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Displays a student's transcript
     */
    private void viewStudentTranscript() {
        System.out.print("Student ID: ");
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

    /**
     * Calculates and displays a student's GPA
     */
    private void calculateStudentGPA() {
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();

        double gpa = academicService.calculateGPA(studentId);
        ConsoleUtils.showSuccess("Student GPA: " + String.format("%.2f", gpa));
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Checks and displays students with academic warnings
     */
    private void checkAcademicWarning() {
        List<Student> warningStudents = academicService.checkAcademicWarning();

        if (warningStudents.isEmpty()) {
            ConsoleUtils.showSuccess("No students with academic warning!");
        } else {
            ConsoleUtils.showHeader("ACADEMIC WARNING LIST");
            for (Student s : warningStudents) {
                System.out.printf("- %s (ID: %s) - GPA: %.2f\n",
                        s.getFullName(), s.getStudentId(), s.getGpa());
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Activity management submenu
     */
    private void activityManagementMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("ACTIVITY MANAGEMENT");

            System.out.println("1.  Add activity for student");
            System.out.println("2.  Calculate training score");
            System.out.println("3.  View training ranking");
            System.out.println("4.  Activity report");
            System.out.println("5.  Back");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addActivityForStudent();
                    break;
                case "2":
                    calculateTrainingScore();
                    break;
                case "3":
                    viewTrainingRanking();
                    break;
                case "4":
                    viewActivityReport();
                    break;
                case "5":
                    return;
                default:
                    ConsoleUtils.showError("Invalid choice!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }

    /**
     * Adds an activity for a student
     */
    private void addActivityForStudent() {
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();

        System.out.println("Activity type:");
        System.out.println("1. TalkShow");
        System.out.println("2. Academic");
        System.out.println("3. Volunteer");
        System.out.println("4. Sports");
        System.out.print("Choose (1-4): ");

        String typeChoice = scanner.nextLine();
        String activityType = "";
        switch (typeChoice) {
            case "1":
                activityType = "TalkShow";
                break;
            case "2":
                activityType = "Academic";
                break;
            case "3":
                activityType = "Volunteer";
                break;
            case "4":
                activityType = "Sports";
                break;
            default:
                ConsoleUtils.showError("Invalid choice!");
                return;
        }

        System.out.print("Activity name: ");
        String activityName = scanner.nextLine();

        System.out.print("Organization: ");
        String organization = scanner.nextLine();

        System.out.print("Hours participated: ");
        int hours = Integer.parseInt(scanner.nextLine());

        // Use current date for simplicity
        java.util.Date now = new java.util.Date();

        boolean success = activityService.addActivity(studentId, activityType, activityName,
                organization, now, now, hours);

        if (success) {
            ConsoleUtils.showSuccess("Activity added successfully!");
        } else {
            ConsoleUtils.showError("Failed to add activity!");
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Calculates and displays a student's training score
     */
    private void calculateTrainingScore() {
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();

        double score = activityService.calculateTrainingScore(studentId);
        ConsoleUtils.showSuccess("Training score: " + String.format("%.1f", score));
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Displays a student's training ranking
     */
    private void viewTrainingRanking() {
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();

        String ranking = activityService.classifyTrainingRanking(studentId);
        ConsoleUtils.showSuccess("Training ranking: " + ranking);
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Displays activity report for a student
     */
    private void viewActivityReport() {
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();

        List<Activity> activities = activityService.getActivityReport(studentId);

        if (activities.isEmpty()) {
            ConsoleUtils.showWarning("Student has no activities!");
        } else {
            ConsoleUtils.showHeader("ACTIVITY LIST");
            for (Activity a : activities) {
                a.displayActivityInfo();
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Reports and statistics submenu
     */
    private void reportMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("REPORTS & STATISTICS");

            System.out.println("1.  Summary report");
            System.out.println("2.  GPA distribution statistics");
            System.out.println("3.  Top 10 outstanding students");
            System.out.println("4.  Academic classification");
            System.out.println("5.  Academic report");
            System.out.println("6.  Training report");
            System.out.println("7.  Back");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    reportService.generateSummaryReport();
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "2":
                    viewGPAStatistics();
                    break;
                case "3":
                    viewTop10Students();
                    break;
                case "4":
                    viewClassification();
                    break;
                case "5":
                    academicService.generateAcademicReport();
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "6":
                    activityService.exportTrainingReport();
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "7":
                    return;
                default:
                    ConsoleUtils.showError("Invalid choice!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }

    /**
     * Displays GPA distribution statistics
     */
    private void viewGPAStatistics() {
        java.util.Map<String, Integer> gpaStats = reportService.getGPAStatistics();

        ConsoleUtils.showHeader("GPA DISTRIBUTION");
        int total = studentManager.getStudents().size();

        for (java.util.Map.Entry<String, Integer> entry : gpaStats.entrySet()) {
            double percentage = total > 0 ? (entry.getValue() * 100.0 / total) : 0;
            System.out.printf("%-10s: %d students (%.1f%%)\n",
                    entry.getKey(), entry.getValue(), percentage);
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Displays top 10 students based on GPA and training score
     */
    private void viewTop10Students() {
        List<Student> top10 = reportService.getTop10Students();

        if (top10.isEmpty()) {
            ConsoleUtils.showWarning("No eligible students!");
        } else {
            ConsoleUtils.showHeader("TOP 10 OUTSTANDING STUDENTS");
            for (int i = 0; i < top10.size(); i++) {
                Student s = top10.get(i);
                double score = ((s.getGpa() / 4.0) * 100) * 0.7 + (s.getTrainingScore() * 0.3);
                System.out.printf("%d. %s (Class: %s) - Score: %.2f\n",
                        i + 1, s.getFullName(), s.getClassId(), score);
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Displays academic classification statistics
     */
    private void viewClassification() {
        java.util.Map<String, Integer> classification = reportService.getClassificationCount();

        ConsoleUtils.showHeader("ACADEMIC CLASSIFICATION");
        int total = studentManager.getStudents().size();

        for (java.util.Map.Entry<String, Integer> entry : classification.entrySet()) {
            double percentage = total > 0 ? (entry.getValue() * 100.0 / total) : 0;
            System.out.printf("%-12s: %d students (%.1f%%)\n",
                    entry.getKey(), entry.getValue(), percentage);
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * User management submenu
     */
    private void userManagementMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("USER MANAGEMENT");

            System.out.println("1.  List users");
            System.out.println("2.  Add new user");
            System.out.println("3.  Update user information");
            System.out.println("4.  Delete user");
            System.out.println("5.  Link Student Account");
            System.out.println("6.  Back");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayAllUsers();
                    break;
                case "2":
                    addUser();
                    break;
                case "3":
                    updateUser();
                    break;
                case "4":
                    deleteUser();
                    break;
                case "5":
                    linkStudentAccount();
                    break;
                case "6":
                    return;
                default:
                    ConsoleUtils.showError("Invalid choice!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }

    /**
     * Links a user account to a student record
     */
    private void linkStudentAccount() {
        ConsoleUtils.showHeader("LINK STUDENT ACCOUNT");

        System.out.print("Enter username to link: ");
        String username = scanner.nextLine();

        User userToLink = null;
        for (User u : userManager.getUsers()) {
            if (u.getUsername().equals(username)) {
                userToLink = u;
                break;
            }
        }

        if (userToLink == null) {
            ConsoleUtils.showError("User not found!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        System.out.println("\nUser information:");
        System.out.println("Username: " + userToLink.getUsername());
        System.out.println("Full Name: " + userToLink.getFullName());
        System.out.println("Email: " + userToLink.getEmail());
        System.out.println("Role: " + userToLink.getRole());

        if (!userToLink.getRole().equalsIgnoreCase("STUDENT")) {
            ConsoleUtils.showError("This user is not a STUDENT!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        System.out.println("\n--- Available Students ---");
        List<Student> students = studentManager.getStudents();
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.printf("%d. %s - %s - %s\n",
                    i + 1, s.getStudentId(), s.getFullName(), s.getEmail());
        }

        System.out.print("\nEnter student number to link: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice < 1 || choice > students.size()) {
            ConsoleUtils.showError("Invalid choice!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        Student selectedStudent = students.get(choice - 1);
        selectedStudent.setEmail(userToLink.getEmail());

        ConsoleUtils.showSuccess("User " + username + " linked with student "
                + selectedStudent.getStudentId() + " successfully!");
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Displays all users in a formatted table
     */
    private void displayAllUsers() {
        List<User> users = userManager.getUsers();

        ConsoleUtils.showHeader("USER LIST");
        System.out.println("┌────┬────────────┬──────────────────┬───────────┬──────────────────────┐");
        System.out.println("│ No │ Username   │ Full Name        │ Role      │ Email                │");
        System.out.println("├────┼────────────┼──────────────────┼───────────┼──────────────────────┤");

        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            System.out.printf("│ %-2d │ %-10s │ %-16s │ %-9s │ %-20s │\n",
                    i + 1, u.getUsername(), truncate(u.getFullName(), 16),
                    u.getRole(), truncate(u.getEmail(), 20));
        }
        System.out.println("└────┴────────────┴──────────────────┴───────────┴──────────────────────┘");
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Adds a new user
     */
    private void addUser() {
        ConsoleUtils.showHeader("ADD NEW USER");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        // Check if username exists
        for (User u : userManager.getUsers()) {
            if (u.getUsername().equals(username)) {
                ConsoleUtils.showError("Username already exists!");
                ConsoleUtils.pressEnterToContinue(scanner);
                return;
            }
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Role (ADMIN/TEACHER/STUDENT/STAFF): ");
        String role = scanner.nextLine().toUpperCase();

        if (!role.equals("ADMIN") && !role.equals("TEACHER") && !role.equals("STUDENT") && !role.equals("STAFF")) {
            ConsoleUtils.showError("Invalid role!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        User newUser = new User(username, password, role, fullName, email);
        userManager.getUsers().add(newUser);
        userManager.saveUserToFile();

        ConsoleUtils.showSuccess("User added successfully!");
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Updates an existing user
     */
    private void updateUser() {
        System.out.print("Enter username to update: ");
        String username = scanner.nextLine();

        User userToUpdate = null;
        for (User u : userManager.getUsers()) {
            if (u.getUsername().equals(username)) {
                userToUpdate = u;
                break;
            }
        }

        if (userToUpdate == null) {
            ConsoleUtils.showError("User not found!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        ConsoleUtils.showHeader("UPDATE USER INFORMATION");
        System.out.println("Current information:");
        System.out.println("Username: " + userToUpdate.getUsername());
        System.out.println("Full Name: " + userToUpdate.getFullName());
        System.out.println("Email: " + userToUpdate.getEmail());
        System.out.println("Role: " + userToUpdate.getRole());

        System.out.println("\nEnter new information (Enter to keep current):");

        System.out.print("Full Name [" + userToUpdate.getFullName() + "]: ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            userToUpdate.setFullName(newName);
        }

        System.out.print("Email [" + userToUpdate.getEmail() + "]: ");
        String newEmail = scanner.nextLine();
        if (!newEmail.isEmpty()) {
            userToUpdate.setEmail(newEmail);
        }

        System.out.print("Role [" + userToUpdate.getRole() + "]: ");
        String newRole = scanner.nextLine().toUpperCase();
        if (!newRole.isEmpty()) {
            if (newRole.equals("ADMIN") || newRole.equals("TEACHER") || newRole.equals("STUDENT") || newRole.equals("STAFF")) {
                userToUpdate.setRole(newRole);
            } else {
                ConsoleUtils.showError("Invalid role!");
            }
        }

        userManager.saveUserToFile();
        ConsoleUtils.showSuccess("Update successful!");
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Deletes a user
     */
    private void deleteUser() {
        System.out.print("Enter username to delete: ");
        String username = scanner.nextLine();

        // Cannot delete yourself
        if (userManager.getCurrentUser().getUsername().equals(username)) {
            ConsoleUtils.showError("Cannot delete currently logged in account!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        User userToDelete = null;
        for (User u : userManager.getUsers()) {
            if (u.getUsername().equals(username)) {
                userToDelete = u;
                break;
            }
        }

        if (userToDelete == null) {
            ConsoleUtils.showError("User not found!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        System.out.print("Are you sure you want to delete? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            userManager.getUsers().remove(userToDelete);
            userManager.saveUserToFile();
            ConsoleUtils.showSuccess("User deleted successfully!");
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Backup and restore submenu
     */
    private void backupRestoreMenu() {
        FileService fileService = new FileServiceImpl();

        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("BACKUP & RESTORE");

            System.out.println("1.  Backup data");
            System.out.println("2.  List backups");
            System.out.println("3.  Restore data");
            System.out.println("4.  Back");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    ConsoleUtils.showLoading("Backing up data");
                    if (fileService.backupData()) {
                        ConsoleUtils.showSuccess("Backup successful!");
                    }
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "2":
                    ((FileServiceImpl) fileService).listBackup();
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "3":
                    System.out.print("Enter backup filename: ");
                    String backupFile = scanner.nextLine();
                    fileService.restoreData(backupFile);
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "4":
                    return;
                default:
                    ConsoleUtils.showError("Invalid choice!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }

    /**
     * Changes password for current user
     */
    private void changePassword() {
        userManager.changePassword(scanner);
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    /**
     * Helper method to truncate strings for display
     * @param str String to truncate
     * @param length Maximum length
     * @return Truncated string with ellipsis if needed
     */
    private String truncate(String str, int length) {
        if (str == null) {
            return "";
        }
        if (str.length() <= length) {
            return str;
        }
        return str.substring(0, length - 3) + "...";
    }
}