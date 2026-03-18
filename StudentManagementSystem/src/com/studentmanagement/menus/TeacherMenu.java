package com.studentmanagement.menus;

import com.studentmanagement.managers.*;
import com.studentmanagement.models.*;
import com.studentmanagement.services.*;
import com.studentmanagement.utils.ConsoleUtils;
import java.util.List;
import java.util.Scanner;

public class TeacherMenu {

    private Scanner scanner;
    private UserManager userManager;
    private StudentManager studentManager;
    private CourseManager courseManager;
    private AcademicService academicService;

    public TeacherMenu(Scanner scanner, UserManager userManager,
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
    }

    public void showMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("TEACHER MENU");

            System.out.println("👨‍🏫 Welcome: " + userManager.getCurrentUser().getFullName());
            System.out.println("═══════════════════════════════════════════");
            System.out.println("1. 📚 VIEW COURSE LIST");
            System.out.println("2. 📝 ENTER GRADES");
            System.out.println("3. 📊 VIEW CLASS GRADE BOARD");
            System.out.println("4. 🔍 SEARCH STUDENTS");
            System.out.println("5. ⚠️ CHECK ACADEMIC WARNING");
            System.out.println("6. 🔐 CHANGE PASSWORD");
            System.out.println("7. 🚪 LOGOUT");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Choose function (1-7): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewAllCourses();
                    break;
                case "2":
                    enterGrade();
                    break;
                case "3":
                    viewClassGradeBoard();
                    break;
                case "4":
                    searchStudent();
                    break;
                case "5":
                    checkAcademicWarning();
                    break;
                case "6":
                    changePassword();
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

    private void viewAllCourses() {
        courseManager.displayAllCourses();
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    private void enterGrade() {
        ConsoleUtils.showHeader("ENTER GRADES");

        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();

        Student student = studentManager.findStudentByStudentId(studentId);
        if (student == null) {
            ConsoleUtils.showError("Student not found!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        student.displayInfo();

        System.out.print("\nEnter course code (e.g., CT101): "); // FIXED: Thêm hướng dẫn
        String courseCode = scanner.nextLine();

        // FIXED: Tìm bằng mã môn học (CT101)
        Course course = courseManager.findCourseByCode(courseCode);
        if (course == null) {
            ConsoleUtils.showError("Course not found with code: " + courseCode);
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        System.out.println("\nCourse: " + course.getCourseName());

        try {
            System.out.print("Attendance score (0-10): ");
            double attendance = Double.parseDouble(scanner.nextLine());

            System.out.print("Midterm score (0-10): ");
            double midterm = Double.parseDouble(scanner.nextLine());

            System.out.print("Final score (0-10): ");
            double finalScore = Double.parseDouble(scanner.nextLine());

            boolean success = academicService.enterGrade(studentId, course.getCourseId(),
                    attendance, midterm, finalScore);

            if (success) {
                ConsoleUtils.showSuccess("Grade entry successful!");
            } else {
                ConsoleUtils.showError("Grade entry failed!");
            }
        } catch (NumberFormatException e) {
            ConsoleUtils.showError("Invalid score!");
        }

        ConsoleUtils.pressEnterToContinue(scanner);
    }

    private void viewClassGradeBoard() {
        System.out.print("Enter class ID: ");
        String classId = scanner.nextLine();

        List<Student> classStudents = new java.util.ArrayList<>();
        for (Student s : studentManager.getStudents()) {
            if (s.getClassId() != null && s.getClassId().equalsIgnoreCase(classId)) {
                classStudents.add(s);
            }
        }

        if (classStudents.isEmpty()) {
            ConsoleUtils.showWarning("No students in class " + classId);
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }

        ConsoleUtils.showHeader("CLASS GRADE BOARD - " + classId);

        System.out.println("┌────┬──────────┬──────────────────┬──────────┬──────────┬──────────┐");
        System.out.println("│ No │ Student  │ Full Name        │ Attend.  │ Midterm  │ Final    │");
        System.out.println("├────┼──────────┼──────────────────┼──────────┼──────────┼──────────┤");

        for (int i = 0; i < classStudents.size(); i++) {
            Student s = classStudents.get(i);

            String attendance = "-", midterm = "-", finalScore = "-";

            if (!s.getRegisteredCourses().isEmpty()) {
                Course c = s.getRegisteredCourses().get(0);
                if (c.getGrade() != null) {
                    attendance = String.format("%.1f", c.getGrade().getAttendanceScore());
                    midterm = String.format("%.1f", c.getGrade().getMidtermScore());
                    finalScore = String.format("%.1f", c.getGrade().getFinalScore());
                }
            }

            System.out.printf("│ %-2d │ %-8s │ %-16s │ %-8s │ %-8s │ %-8s │\n",
                    i + 1, s.getStudentId(), truncate(s.getFullName(), 16),
                    attendance, midterm, finalScore);
        }

        System.out.println("└────┴──────────┴──────────────────┴──────────┴──────────┴──────────┘");
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    private void searchStudent() {
        studentManager.searchStudent(scanner);
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    private void checkAcademicWarning() {
        List<Student> warningStudents = academicService.checkAcademicWarning();

        if (warningStudents.isEmpty()) {
            ConsoleUtils.showSuccess("No students with academic warning!");
        } else {
            ConsoleUtils.showHeader("ACADEMIC WARNING LIST");
            for (Student s : warningStudents) {
                System.out.printf("- %s (ID: %s) - Class: %s - GPA: %.2f\n",
                        s.getFullName(), s.getStudentId(), s.getClassId(), s.getGpa());
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }

    private void changePassword() {
        userManager.changePassword(scanner);
        ConsoleUtils.pressEnterToContinue(scanner);
    }

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
