package com.studentmanagement.menus;

import com.studentmanagement.managers.*;
import com.studentmanagement.utils.ConsoleUtils;
import java.util.Scanner;

public class MainMenu {
    private Scanner scanner;
    private UserManager userManager;
    private StudentManager studentManager;
    private CourseManager courseManager;
    private AdminMenu adminMenu;
    private TeacherMenu teacherMenu;
    private StudentMenu studentMenu;
    
    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.userManager = new UserManager();
        this.studentManager = new StudentManager();
        this.courseManager = new CourseManager();
    }
    
    public void start() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("HỆ THỐNG QUẢN LÝ SINH VIÊN");
            
            if (userManager.getCurrentUser() == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }
    
    private void showLoginMenu() {
        System.out.println("1. 🔐 Đăng nhập");
        System.out.println("2. ❌ Thoát");
        System.out.println("═══════════════════════════════════════════");
        System.out.print("Chọn: ");
        
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1":
                login();
                break;
            case "2":
                ConsoleUtils.showInfo("Cảm ơn bạn đã sử dụng hệ thống!");
                System.exit(0);
                break;
            default:
                ConsoleUtils.showError("Lựa chọn không hợp lệ!");
                ConsoleUtils.pressEnterToContinue(scanner);
        }
    }
    
    private void login() {
        ConsoleUtils.showHeader("ĐĂNG NHẬP HỆ THỐNG");
        
        System.out.print("Tên đăng nhập: ");
        String username = scanner.nextLine();
        
        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();
        
        if (userManager.login(username, password)) {
            ConsoleUtils.showSuccess("Đăng nhập thành công!");
            
            // Khởi tạo menu theo role
            String role = userManager.getCurrentUser().getRole();
            
            if (role.equalsIgnoreCase("ADMIN")) {
                adminMenu = new AdminMenu(scanner, userManager, studentManager, courseManager);
            } else if (role.equalsIgnoreCase("TEACHER")) {
                teacherMenu = new TeacherMenu(scanner, userManager, studentManager, courseManager);
            } else if (role.equalsIgnoreCase("STUDENT")) {
                studentMenu = new StudentMenu(scanner, userManager, studentManager, courseManager);
            }
            
            ConsoleUtils.pressEnterToContinue(scanner);
        } else {
            ConsoleUtils.showError("Đăng nhập thất bại!");
            ConsoleUtils.pressEnterToContinue(scanner);
        }
    }
    
    private void showMainMenu() {
        String role = userManager.getCurrentUser().getRole();
        
        if (role.equalsIgnoreCase("ADMIN") && adminMenu != null) {
            adminMenu.showMenu();
        } else if (role.equalsIgnoreCase("TEACHER") && teacherMenu != null) {
            teacherMenu.showMenu();
        } else if (role.equalsIgnoreCase("STUDENT") && studentMenu != null) {
            studentMenu.showMenu();
        } else {
            System.out.println("❌ Lỗi: Không xác định được role hoặc menu!");
            userManager.logout();
            ConsoleUtils.pressEnterToContinue(scanner);
        }
    }
    
    public static void main(String[] args) {
        new MainMenu().start();
    }
}