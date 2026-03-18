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
            ConsoleUtils.showHeader("MENU GIẢNG VIÊN (TEACHER)");
            
            System.out.println("👨‍🏫 Xin chào: " + userManager.getCurrentUser().getFullName());
            System.out.println("═══════════════════════════════════════════");
            System.out.println("1. 📚 XEM DANH SÁCH MÔN HỌC");
            System.out.println("2. 📝 NHẬP ĐIỂM CHO SINH VIÊN");
            System.out.println("3. 📊 XEM BẢNG ĐIỂM LỚP HỌC");
            System.out.println("4. 🔍 TÌM KIẾM SINH VIÊN");
            System.out.println("5. ⚠️ KIỂM TRA CẢNH BÁO HỌC VỤ");
            System.out.println("6. 🔐 ĐỔI MẬT KHẨU");
            System.out.println("7. 🚪 ĐĂNG XUẤT");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Chọn chức năng (1-7): ");
            
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
                    ConsoleUtils.showInfo("Đã đăng xuất!");
                    return;
                default:
                    ConsoleUtils.showError("Lựa chọn không hợp lệ!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }
    
    private void viewAllCourses() {
        courseManager.displayAllCourses();
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void enterGrade() {
        ConsoleUtils.showHeader("NHẬP ĐIỂM CHO SINH VIÊN");
        
        System.out.print("Mã sinh viên: ");
        String studentId = scanner.nextLine();
        
        Student student = studentManager.findStudentByStudentId(studentId);
        if (student == null) {
            ConsoleUtils.showError("Không tìm thấy sinh viên!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }
        
        student.displayInfo();
        
        System.out.print("\nMã môn học: ");
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
        
        System.out.println("\nMôn học: " + course.getCourseName());
        
        try {
            System.out.print("Điểm chuyên cần (0-10): ");
            double attendance = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Điểm giữa kỳ (0-10): ");
            double midterm = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Điểm cuối kỳ (0-10): ");
            double finalScore = Double.parseDouble(scanner.nextLine());
            
            boolean success = academicService.enterGrade(studentId, course.getCourseId(), 
                                                         attendance, midterm, finalScore);
            
            if (success) {
                ConsoleUtils.showSuccess("Nhập điểm thành công!");
            } else {
                ConsoleUtils.showError("Nhập điểm thất bại!");
            }
        } catch (NumberFormatException e) {
            ConsoleUtils.showError("Điểm số không hợp lệ!");
        }
        
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void viewClassGradeBoard() {
        System.out.print("Nhập mã lớp: ");
        String classId = scanner.nextLine();
        
        List<Student> classStudents = new java.util.ArrayList<>();
        for (Student s : studentManager.getStudents()) {
            if (s.getClassId() != null && s.getClassId().equalsIgnoreCase(classId)) {
                classStudents.add(s);
            }
        }
        
        if (classStudents.isEmpty()) {
            ConsoleUtils.showWarning("Không có sinh viên nào trong lớp " + classId);
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }
        
        ConsoleUtils.showHeader("BẢNG ĐIỂM LỚP " + classId);
        
        System.out.println("┌────┬──────────┬──────────────────┬──────────┬──────────┬──────────┐");
        System.out.println("│ STT│ MSSV     │ Họ và tên        │ Chuyên cần│ Giữa kỳ  │ Cuối kỳ  │");
        System.out.println("├────┼──────────┼──────────────────┼──────────┼──────────┼──────────┤");
        
        for (int i = 0; i < classStudents.size(); i++) {
            Student s = classStudents.get(i);
            
            // Lấy điểm môn học đầu tiên (tạm thời)
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
                i+1, s.getStudentId(), truncate(s.getFullName(), 16), 
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
            ConsoleUtils.showSuccess("Không có sinh viên nào bị cảnh báo học vụ!");
        } else {
            ConsoleUtils.showHeader("DANH SÁCH CẢNH BÁO HỌC VỤ");
            for (Student s : warningStudents) {
                System.out.printf("- %s (MSSV: %s) - Lớp: %s - GPA: %.2f\n", 
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
        if (str == null) return "";
        if (str.length() <= length) return str;
        return str.substring(0, length - 3) + "...";
    }
}