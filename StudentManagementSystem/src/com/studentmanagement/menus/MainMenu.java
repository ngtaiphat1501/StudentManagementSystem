package com.studentmanagement.menus;

import com.studentmanagement.managers.*;
import com.studentmanagement.utils.ConsoleUtils;
import java.util.Scanner;

/**
 * Main menu controller that handles login and role-based menu navigation
 */
public class MainMenu {

    private Scanner scanner;
    private UserManager userManager;
    private StudentManager studentManager;
    private CourseManager courseManager;
    private AdminMenu adminMenu;
    private TeacherMenu teacherMenu;
    private StudentMenu studentMenu;
    private StaffMenu staffMenu;

    /**
     * Constructor - initializes all managers
     */
    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.userManager = new UserManager();
        this.studentManager = new StudentManager();
        this.courseManager = new CourseManager();
    }

    /**
     * Starts the main application loop
     */
    public void start() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("STUDENT MANAGEMENT SYSTEM");

            if (userManager.getCurrentUser() == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    /**
     * Displays login menu when no user is logged in
     */
    private void showLoginMenu() {
        System.out.println("1.  Login");
        System.out.println("2.  Exit");
        System.out.println("═══════════════════════════════════════════");
        System.out.print("Choose: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                login();
                break;
            case "2":
                ConsoleUtils.showInfo("Thank you for using the system!");
                System.exit(0);
                break;
            default:
                ConsoleUtils.showError("Invalid choice!");
                ConsoleUtils.pressEnterToContinue(scanner);
        }
    }

    /**
     * Handles user login process
     */
    private void login() {
        ConsoleUtils.showHeader("LOGIN");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (userManager.login(username, password)) {
            ConsoleUtils.showSuccess("Login successful!");

            String role = userManager.getCurrentUser().getRole();

            if (role.equalsIgnoreCase("ADMIN")) {
                adminMenu = new AdminMenu(scanner, userManager, studentManager, courseManager);
            } else if (role.equalsIgnoreCase("TEACHER")) {
                teacherMenu = new TeacherMenu(scanner, userManager, studentManager, courseManager);
            } else if (role.equalsIgnoreCase("STUDENT")) {
                studentMenu = new StudentMenu(scanner, userManager, studentManager, courseManager);
            } else if (role.equalsIgnoreCase("STAFF")) {
                staffMenu = new StaffMenu(scanner, userManager, studentManager, courseManager);
            }

            ConsoleUtils.pressEnterToContinue(scanner);
        } else {
            ConsoleUtils.showError("Login failed!");
            ConsoleUtils.pressEnterToContinue(scanner);
        }
    }

    /**
     * Displays the appropriate menu based on user role
     */
    private void showMainMenu() {
        String role = userManager.getCurrentUser().getRole();

        if (role.equalsIgnoreCase("ADMIN")) {
            if (adminMenu == null) {
                adminMenu = new AdminMenu(scanner, userManager, studentManager, courseManager);
            }
            adminMenu.showMenu();
        } else if (role.equalsIgnoreCase("TEACHER")) {
            if (teacherMenu == null) {
                teacherMenu = new TeacherMenu(scanner, userManager, studentManager, courseManager);
            }
            teacherMenu.showMenu();
        } else if (role.equalsIgnoreCase("STUDENT")) {
            if (studentMenu == null) {
                studentMenu = new StudentMenu(scanner, userManager, studentManager, courseManager);
            }
            studentMenu.showMenu();
        } else if (role.equalsIgnoreCase("STAFF")) {
            if (staffMenu == null) {
                staffMenu = new StaffMenu(scanner, userManager, studentManager, courseManager);
            }
            staffMenu.showMenu();
        } else {
            System.out.println(" Error: Role or menu not determined!");
            userManager.logout();
            ConsoleUtils.pressEnterToContinue(scanner);
        }
    }

    /**
     * Main entry point for the application
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        new MainMenu().start();
    }
}