package com.studentmanagement.menus;

import com.studentmanagement.managers.*;
import com.studentmanagement.models.*;
import com.studentmanagement.services.*;
import com.studentmanagement.utils.ConsoleUtils;
import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    private Scanner scanner;
    private UserManager userManager;
    private StudentManager studentManager;
    private CourseManager courseManager;
    private Student currentStudent;
    private AcademicService academicService;
    private ActivityService activityService;
    
    public StudentMenu(Scanner scanner, UserManager userManager, 
                       StudentManager studentManager, CourseManager courseManager) {
        this.scanner = scanner;
        this.userManager = userManager;
        this.studentManager = studentManager;
        this.courseManager = courseManager;
        
        // Tìm thông tin sinh viên từ username
        String username = userManager.getCurrentUser().getUsername();
        for (Student s : studentManager.getStudents()) {
            if (s.getStudentId().equalsIgnoreCase(username) || 
                s.getEmail().equalsIgnoreCase(userManager.getCurrentUser().getEmail())) {
                this.currentStudent = s;
                break;
            }
        }
        
        this.academicService = new AcademicServiceImpl(
            courseManager.getCourses(),
            new java.util.ArrayList<>(),
            new java.util.ArrayList<>(),
            new StudentServiceImpl()
        );
        
        this.activityService = new ActivityServiceImpl(new StudentServiceImpl());
    }
    
    public void showMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("MENU SINH VIÊN (STUDENT)");
            
            System.out.println("🧑‍🎓 Xin chào: " + userManager.getCurrentUser().getFullName());
            if (currentStudent != null) {
                System.out.println("📚 MSSV: " + currentStudent.getStudentId());
                System.out.println("🏫 Lớp: " + currentStudent.getClassId());
            }
            System.out.println("═══════════════════════════════════════════");
            System.out.println("1. 📋 XEM THÔNG TIN CÁ NHÂN");
            System.out.println("2. 📚 XEM DANH SÁCH MÔN HỌC");
            System.out.println("3. 📝 ĐĂNG KÝ MÔN HỌC");
            System.out.println("4. 📊 XEM BẢNG ĐIỂM");
            System.out.println("5. 📈 XEM GPA");
            System.out.println("6. 🏆 XEM ĐIỂM RÈN LUYỆN");
            System.out.println("7. 📋 XEM LỊCH SỬ HOẠT ĐỘNG");
            System.out.println("8. 🔐 ĐỔI MẬT KHẨU");
            System.out.println("9. 🚪 ĐĂNG XUẤT");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Chọn chức năng (1-9): ");
            
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
                    ConsoleUtils.showInfo("Đã đăng xuất!");
                    return;
                default:
                    ConsoleUtils.showError("Lựa chọn không hợp lệ!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }
    
    private void viewPersonalInfo() {
        if (currentStudent != null) {
            currentStudent.displayInfo();
        } else {
            ConsoleUtils.showHeader("THÔNG TIN TÀI KHOẢN");
            System.out.println("Username: " + userManager.getCurrentUser().getUsername());
            System.out.println("Họ tên: " + userManager.getCurrentUser().getFullName());
            System.out.println("Email: " + userManager.getCurrentUser().getEmail());
            System.out.println("Role: " + userManager.getCurrentUser().getRole());
            ConsoleUtils.showWarning("Liên kết với thông tin sinh viên không tồn tại!");
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void viewAllCourses() {
        courseManager.displayAllCourses();
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void registerCourse() {
        if (currentStudent == null) {
            ConsoleUtils.showError("Không tìm thấy thông tin sinh viên!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }
        
        ConsoleUtils.showHeader("ĐĂNG KÝ MÔN HỌC");
        
        courseManager.displayAllCourses();
        
        System.out.print("Nhập mã môn học cần đăng ký: ");
        String courseId = scanner.nextLine();
        
        Course course = courseManager.findCourseById(courseId);
        if (course == null) {
            course = courseManager.findCourseByCode(courseId);
            if (course == null) {
                ConsoleUtils.showError("Không tìm thấy môn học!");
                ConsoleUtils.pressEnterToContinue(scanner);
                return;
            }
        }
        
        System.out.print("Học kỳ (1-9): ");
        String semester = scanner.nextLine();
        
        System.out.print("Năm học (VD: 2023-2024): ");
        String academicYear = scanner.nextLine();
        
        boolean success = academicService.registerCourse(
            currentStudent.getStudentId(), course.getCourseId(), semester, academicYear);
        
        if (success) {
            ConsoleUtils.showSuccess("Đăng ký môn học thành công!");
        } else {
            ConsoleUtils.showError("Đăng ký thất bại!");
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void viewTranscript() {
        if (currentStudent == null) {
            ConsoleUtils.showError("Không tìm thấy thông tin sinh viên!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }
        
        List<Grade> transcript = academicService.getTranscript(currentStudent.getStudentId());
        
        if (transcript.isEmpty()) {
            ConsoleUtils.showWarning("Bạn chưa có điểm môn học nào!");
        } else {
            ConsoleUtils.showHeader("BẢNG ĐIỂM CÁ NHÂN");
            for (Grade g : transcript) {
                g.displayGradeInfo();
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void viewGPA() {
        if (currentStudent == null) {
            ConsoleUtils.showError("Không tìm thấy thông tin sinh viên!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }
        
        double gpa = academicService.calculateGPA(currentStudent.getStudentId());
        
        ConsoleUtils.showHeader("THÔNG TIN GPA");
        System.out.printf("GPA hiện tại: %.2f\n", gpa);
        
        String classification;
        if (gpa >= 3.6) classification = "Xuất sắc";
        else if (gpa >= 3.2) classification = "Giỏi";
        else if (gpa >= 2.5) classification = "Khá";
        else if (gpa >= 2.0) classification = "Trung bình";
        else classification = "Yếu";
        
        System.out.println("Xếp loại: " + classification);
        
        if (gpa < 2.0) {
            ConsoleUtils.showWarning("Bạn đang bị cảnh báo học vụ!");
        }
        
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void viewTrainingScore() {
        if (currentStudent == null) {
            ConsoleUtils.showError("Không tìm thấy thông tin sinh viên!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }
        
        double score = activityService.calculateTrainingScore(currentStudent.getStudentId());
        String ranking = activityService.classifyTrainingRanking(currentStudent.getStudentId());
        
        ConsoleUtils.showHeader("ĐIỂM RÈN LUYỆN");
        System.out.printf("Điểm rèn luyện: %.1f/100\n", score);
        System.out.println("Xếp loại: " + ranking);
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void viewActivityHistory() {
        if (currentStudent == null) {
            ConsoleUtils.showError("Không tìm thấy thông tin sinh viên!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }
        
        List<Activity> activities = activityService.getActivityReport(currentStudent.getStudentId());
        
        if (activities.isEmpty()) {
            ConsoleUtils.showWarning("Bạn chưa tham gia hoạt động nào!");
        } else {
            ConsoleUtils.showHeader("LỊCH SỬ HOẠT ĐỘNG");
            for (Activity a : activities) {
                a.displayActivityInfo();
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void changePassword() {
        userManager.changePassword(scanner);
        ConsoleUtils.pressEnterToContinue(scanner);
    }
}