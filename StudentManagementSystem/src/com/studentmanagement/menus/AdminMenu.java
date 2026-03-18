package com.studentmanagement.menus;

import com.studentmanagement.managers.*;
import com.studentmanagement.models.*;
import com.studentmanagement.services.*;
import com.studentmanagement.utils.ConsoleUtils;
import com.studentmanagement.utils.Constants;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private Scanner scanner;
    private UserManager userManager;
    private StudentManager studentManager;
    private CourseManager courseManager;
    private AcademicService academicService;
    private ActivityService activityService;
    private ReportService reportService;
    
    public AdminMenu(Scanner scanner, UserManager userManager, 
                     StudentManager studentManager, CourseManager courseManager) {
        this.scanner = scanner;
        this.userManager = userManager;
        this.studentManager = studentManager;
        this.courseManager = courseManager;
        
        // Khởi tạo services
        this.academicService = new AcademicServiceImpl(
            courseManager.getCourses(),
            new java.util.ArrayList<>(),
            new java.util.ArrayList<>(),
            new StudentServiceImpl()
        );
        
        this.activityService = new ActivityServiceImpl(new StudentServiceImpl());
        this.reportService = new ReportServiceImpl(studentManager.getStudents());
    }
    
    public void showMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("MENU QUẢN TRỊ VIÊN (ADMIN)");
            
            System.out.println("👤 Xin chào: " + userManager.getCurrentUser().getFullName());
            System.out.println("═══════════════════════════════════════════");
            System.out.println("1. 📚 QUẢN LÝ MÔN HỌC");
            System.out.println("2. 🧑‍🎓 QUẢN LÝ SINH VIÊN");
            System.out.println("3. 📝 QUẢN LÝ ĐIỂM SỐ");
            System.out.println("4. 🏆 QUẢN LÝ HOẠT ĐỘNG");
            System.out.println("5. 📊 BÁO CÁO & THỐNG KÊ");
            System.out.println("6. 👥 QUẢN LÝ NGƯỜI DÙNG");
            System.out.println("7. 💾 SAO LƯU & PHỤC HỒI");
            System.out.println("8. 🔐 ĐỔI MẬT KHẨU");
            System.out.println("9. 🚪 ĐĂNG XUẤT");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Chọn chức năng (1-9): ");
            
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
                    ConsoleUtils.showInfo("Đã đăng xuất!");
                    return;
                default:
                    ConsoleUtils.showError("Lựa chọn không hợp lệ!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }
    
    // ==================== QUẢN LÝ MÔN HỌC ====================
    private void courseManagementMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("QUẢN LÝ MÔN HỌC");
            
            System.out.println("1. 📋 Xem danh sách môn học");
            System.out.println("2. ➕ Thêm môn học mới");
            System.out.println("3. ✏️ Sửa thông tin môn học");
            System.out.println("4. ❌ Xóa môn học");
            System.out.println("5. 🔍 Tìm kiếm môn học");
            System.out.println("6. 📊 Thống kê môn học");
            System.out.println("7. 🔙 Quay lại");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Chọn: ");
            
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
                    ConsoleUtils.showError("Lựa chọn không hợp lệ!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }
    
    private void searchCourseMenu() {
        ConsoleUtils.showHeader("TÌM KIẾM MÔN HỌC");
        
        System.out.println("1. 🔍 Tìm theo tên");
        System.out.println("2. 🏫 Tìm theo khoa");
        System.out.println("3. 👨‍🏫 Tìm theo giảng viên");
        System.out.println("4. 🔙 Quay lại");
        System.out.print("Chọn: ");
        
        String choice = scanner.nextLine();
        
        System.out.print("Nhập từ khóa: ");
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
            ConsoleUtils.showWarning("Không tìm thấy môn học nào!");
        } else {
            ConsoleUtils.showSuccess("Tìm thấy " + results.size() + " môn học:");
            for (Course c : results) {
                c.displayInfo();
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    // ==================== QUẢN LÝ SINH VIÊN ====================
    private void studentManagementMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("QUẢN LÝ SINH VIÊN");
            
            System.out.println("1. 📋 Xem danh sách sinh viên");
            System.out.println("2. ➕ Thêm sinh viên mới");
            System.out.println("3. ✏️ Sửa thông tin sinh viên");
            System.out.println("4. ❌ Xóa sinh viên");
            System.out.println("5. 🔍 Tìm kiếm sinh viên");
            System.out.println("6. 📊 Xem thống kê theo lớp");
            System.out.println("7. 🔙 Quay lại");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Chọn: ");
            
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
                    ConsoleUtils.showError("Lựa chọn không hợp lệ!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }
    
    private void viewClassStatistics() {
        System.out.print("Nhập mã lớp: ");
        String classId = scanner.nextLine();
        
          java.util.Map<String, Object> stats = reportService.getClassStatistics(classId);
        
        ConsoleUtils.showHeader("THỐNG KÊ LỚP " + classId);
        System.out.println("Sĩ số: " + stats.get("Sĩ số"));
        System.out.printf("GPA trung bình: %.2f\n", stats.get("GPA Trung bình"));
        System.out.printf("Điểm RL trung bình: %.1f\n", stats.get("Điểm RL Trung bình"));
        
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    // ==================== QUẢN LÝ ĐIỂM SỐ ====================
    private void gradeManagementMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("QUẢN LÝ ĐIỂM SỐ");
            
            System.out.println("1. 📝 Đăng ký môn học cho sinh viên");
            System.out.println("2. ✏️ Nhập điểm môn học");
            System.out.println("3. 📊 Xem bảng điểm sinh viên");
            System.out.println("4. 📈 Tính GPA sinh viên");
            System.out.println("5. ⚠️ Kiểm tra cảnh báo học vụ");
            System.out.println("6. 🔙 Quay lại");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Chọn: ");
            
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
                    ConsoleUtils.showError("Lựa chọn không hợp lệ!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }
    
    private void registerCourseForStudent() {
        System.out.print("Mã sinh viên: ");
        String studentId = scanner.nextLine();
        
        Student student = studentManager.findStudentByStudentId(studentId);
        if (student == null) {
            ConsoleUtils.showError("Không tìm thấy sinh viên!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }
        
        courseManager.displayAllCourses();
        
        System.out.print("Mã môn học cần đăng ký: ");
        String courseId = scanner.nextLine();
        
        System.out.print("Học kỳ (1-9): ");
        String semester = scanner.nextLine();
        
        System.out.print("Năm học (VD: 2023-2024): ");
        String academicYear = scanner.nextLine();
        
        boolean success = academicService.registerCourse(studentId, courseId, semester, academicYear);
        
        if (success) {
            ConsoleUtils.showSuccess("Đăng ký thành công!");
        } else {
            ConsoleUtils.showError("Đăng ký thất bại!");
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void enterGradeForStudent() {
        System.out.print("Mã sinh viên: ");
        String studentId = scanner.nextLine();
        
        System.out.print("Mã môn học: ");
        String courseId = scanner.nextLine();
        
        System.out.print("Điểm chuyên cần (0-10): ");
        double attendance = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Điểm giữa kỳ (0-10): ");
        double midterm = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Điểm cuối kỳ (0-10): ");
        double finalScore = Double.parseDouble(scanner.nextLine());
        
        boolean success = academicService.enterGrade(studentId, courseId, attendance, midterm, finalScore);
        
        if (success) {
            ConsoleUtils.showSuccess("Nhập điểm thành công!");
        } else {
            ConsoleUtils.showError("Nhập điểm thất bại!");
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void viewStudentTranscript() {
        System.out.print("Mã sinh viên: ");
        String studentId = scanner.nextLine();
        
        List<Grade> transcript = academicService.getTranscript(studentId);
        
        if (transcript.isEmpty()) {
            ConsoleUtils.showWarning("Sinh viên chưa có điểm!");
        } else {
            ConsoleUtils.showHeader("BẢNG ĐIỂM SINH VIÊN");
            for (Grade g : transcript) {
                g.displayGradeInfo();
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void calculateStudentGPA() {
        System.out.print("Mã sinh viên: ");
        String studentId = scanner.nextLine();
        
        double gpa = academicService.calculateGPA(studentId);
        ConsoleUtils.showSuccess("GPA của sinh viên: " + String.format("%.2f", gpa));
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void checkAcademicWarning() {
        List<Student> warningStudents = academicService.checkAcademicWarning();
        
        if (warningStudents.isEmpty()) {
            ConsoleUtils.showSuccess("Không có sinh viên nào bị cảnh báo học vụ!");
        } else {
            ConsoleUtils.showHeader("DANH SÁCH CẢNH BÁO HỌC VỤ");
            for (Student s : warningStudents) {
                System.out.printf("- %s (MSSV: %s) - GPA: %.2f\n", 
                    s.getFullName(), s.getStudentId(), s.getGpa());
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    // ==================== QUẢN LÝ HOẠT ĐỘNG ====================
    private void activityManagementMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("QUẢN LÝ HOẠT ĐỘNG");
            
            System.out.println("1. ➕ Thêm hoạt động cho sinh viên");
            System.out.println("2. 📊 Tính điểm rèn luyện");
            System.out.println("3. 🏆 Xem xếp loại rèn luyện");
            System.out.println("4. 📋 Báo cáo hoạt động");
            System.out.println("5. 🔙 Quay lại");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Chọn: ");
            
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
                    ConsoleUtils.showError("Lựa chọn không hợp lệ!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }
    
    private void addActivityForStudent() {
        System.out.print("Mã sinh viên: ");
        String studentId = scanner.nextLine();
        
        System.out.println("Loại hoạt động:");
        System.out.println("1. TalkShow");
        System.out.println("2. Học thuật");
        System.out.println("3. Tình nguyện");
        System.out.println("4. Thể thao");
        System.out.print("Chọn (1-4): ");
        
        String typeChoice = scanner.nextLine();
        String activityType = "";
        switch (typeChoice) {
            case "1": activityType = "TalkShow"; break;
            case "2": activityType = "Hoc thuat"; break;
            case "3": activityType = "Tinh Nguyen"; break;
            case "4": activityType = "The thao"; break;
            default: 
                ConsoleUtils.showError("Lựa chọn không hợp lệ!");
                return;
        }
        
        System.out.print("Tên hoạt động: ");
        String activityName = scanner.nextLine();
        
        System.out.print("Đơn vị tổ chức: ");
        String organization = scanner.nextLine();
        
        System.out.print("Số giờ tham gia: ");
        int hours = Integer.parseInt(scanner.nextLine());
        
        // Sử dụng ngày hiện tại cho đơn giản
        java.util.Date now = new java.util.Date();
        
        boolean success = activityService.addActivity(studentId, activityType, activityName, 
                                                      organization, now, now, hours);
        
        if (success) {
            ConsoleUtils.showSuccess("Thêm hoạt động thành công!");
        } else {
            ConsoleUtils.showError("Thêm hoạt động thất bại!");
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void calculateTrainingScore() {
        System.out.print("Mã sinh viên: ");
        String studentId = scanner.nextLine();
        
        double score = activityService.calculateTrainingScore(studentId);
        ConsoleUtils.showSuccess("Điểm rèn luyện: " + String.format("%.1f", score));
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void viewTrainingRanking() {
        System.out.print("Mã sinh viên: ");
        String studentId = scanner.nextLine();
        
        String ranking = activityService.classifyTrainingRanking(studentId);
        ConsoleUtils.showSuccess("Xếp loại rèn luyện: " + ranking);
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void viewActivityReport() {
        System.out.print("Mã sinh viên: ");
        String studentId = scanner.nextLine();
        
        List<Activity> activities = activityService.getActivityReport(studentId);
        
        if (activities.isEmpty()) {
            ConsoleUtils.showWarning("Sinh viên chưa tham gia hoạt động nào!");
        } else {
            ConsoleUtils.showHeader("DANH SÁCH HOẠT ĐỘNG");
            for (Activity a : activities) {
                a.displayActivityInfo();
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    // ==================== BÁO CÁO & THỐNG KÊ ====================
    private void reportMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("BÁO CÁO & THỐNG KÊ");
            
            System.out.println("1. 📊 Báo cáo tổng hợp");
            System.out.println("2. 📈 Thống kê phân phối GPA");
            System.out.println("3. 🏆 Top 10 sinh viên xuất sắc");
            System.out.println("4. 📋 Xếp loại học lực");
            System.out.println("5. 📊 Báo cáo học tập");
            System.out.println("6. 📊 Báo cáo rèn luyện");
            System.out.println("7. 🔙 Quay lại");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Chọn: ");
            
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
                    ConsoleUtils.showError("Lựa chọn không hợp lệ!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }
    
    private void viewGPAStatistics() {
        java.util.Map<String, Integer> gpaStats = reportService.getGPAStatistics();
        
        ConsoleUtils.showHeader("PHÂN PHỐI GPA");
        int total = studentManager.getStudents().size();
        
        for (java.util.Map.Entry<String, Integer> entry : gpaStats.entrySet()) {
            double percentage = total > 0 ? (entry.getValue() * 100.0 / total) : 0;
            System.out.printf("%-10s: %d sinh viên (%.1f%%)\n", 
                entry.getKey(), entry.getValue(), percentage);
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void viewTop10Students() {
        List<Student> top10 = reportService.getTop10Students();
        
        if (top10.isEmpty()) {
            ConsoleUtils.showWarning("Không có sinh viên đủ điều kiện!");
        } else {
            ConsoleUtils.showHeader("TOP 10 SINH VIÊN XUẤT SẮC");
            for (int i = 0; i < top10.size(); i++) {
                Student s = top10.get(i);
                double score = ((s.getGpa() / 4.0) * 100) * 0.7 + (s.getTrainingScore() * 0.3);
                System.out.printf("%d. %s (Lớp: %s) - Điểm: %.2f\n", 
                    i+1, s.getFullName(), s.getClassId(), score);
            }
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void viewClassification() {
        java.util.Map<String, Integer> classification = reportService.getClassificationCount();
        
        ConsoleUtils.showHeader("XẾP LOẠI HỌC LỰC");
        int total = studentManager.getStudents().size();
        
        for (java.util.Map.Entry<String, Integer> entry : classification.entrySet()) {
            double percentage = total > 0 ? (entry.getValue() * 100.0 / total) : 0;
            System.out.printf("%-12s: %d sinh viên (%.1f%%)\n", 
                entry.getKey(), entry.getValue(), percentage);
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    // ==================== QUẢN LÝ NGƯỜI DÙNG ====================
    private void userManagementMenu() {
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("QUẢN LÝ NGƯỜI DÙNG");
            
            System.out.println("1. 📋 Danh sách người dùng");
            System.out.println("2. ➕ Thêm người dùng mới");
            System.out.println("3. ✏️ Sửa thông tin người dùng");
            System.out.println("4. ❌ Xóa người dùng");
            System.out.println("5. 🔙 Quay lại");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Chọn: ");
            
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
                    return;
                default:
                    ConsoleUtils.showError("Lựa chọn không hợp lệ!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }
    
    private void displayAllUsers() {
        List<User> users = userManager.getUsers();
        
        ConsoleUtils.showHeader("DANH SÁCH NGƯỜI DÙNG");
        System.out.println("┌────┬────────────┬──────────────────┬───────────┬──────────────────────┐");
        System.out.println("│ STT│ Username   │ Họ và tên        │ Role      │ Email                │");
        System.out.println("├────┼────────────┼──────────────────┼───────────┼──────────────────────┤");
        
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            System.out.printf("│ %-2d │ %-10s │ %-16s │ %-9s │ %-20s │\n",
                i+1, u.getUsername(), truncate(u.getFullName(), 16), 
                u.getRole(), truncate(u.getEmail(), 20));
        }
        System.out.println("└────┴────────────┴──────────────────┴───────────┴──────────────────────┘");
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void addUser() {
        ConsoleUtils.showHeader("THÊM NGƯỜI DÙNG MỚI");
        
        System.out.print("Username: ");
        String username = scanner.nextLine();
        
        // Kiểm tra username tồn tại
        for (User u : userManager.getUsers()) {
            if (u.getUsername().equals(username)) {
                ConsoleUtils.showError("Username đã tồn tại!");
                ConsoleUtils.pressEnterToContinue(scanner);
                return;
            }
        }
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        System.out.print("Họ và tên: ");
        String fullName = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.println("Role (ADMIN/TEACHER/STUDENT): ");
        String role = scanner.nextLine().toUpperCase();
        
        if (!role.equals("ADMIN") && !role.equals("TEACHER") && !role.equals("STUDENT")) {
            ConsoleUtils.showError("Role không hợp lệ!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }
        
        User newUser = new User(username, password, role, fullName, email);
        userManager.getUsers().add(newUser);
        userManager.saveUserToFile();
        
        ConsoleUtils.showSuccess("Thêm người dùng thành công!");
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void updateUser() {
        System.out.print("Nhập username cần sửa: ");
        String username = scanner.nextLine();
        
        User userToUpdate = null;
        for (User u : userManager.getUsers()) {
            if (u.getUsername().equals(username)) {
                userToUpdate = u;
                break;
            }
        }
        
        if (userToUpdate == null) {
            ConsoleUtils.showError("Không tìm thấy người dùng!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }
        
        ConsoleUtils.showHeader("SỬA THÔNG TIN NGƯỜI DÙNG");
        System.out.println("Thông tin hiện tại:");
        System.out.println("Username: " + userToUpdate.getUsername());
        System.out.println("Họ tên: " + userToUpdate.getFullName());
        System.out.println("Email: " + userToUpdate.getEmail());
        System.out.println("Role: " + userToUpdate.getRole());
        
        System.out.println("\nNhập thông tin mới (Enter để giữ nguyên):");
        
        System.out.print("Họ tên [" + userToUpdate.getFullName() + "]: ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) userToUpdate.setFullName(newName);
        
        System.out.print("Email [" + userToUpdate.getEmail() + "]: ");
        String newEmail = scanner.nextLine();
        if (!newEmail.isEmpty()) userToUpdate.setEmail(newEmail);
        
        System.out.print("Role [" + userToUpdate.getRole() + "]: ");
        String newRole = scanner.nextLine().toUpperCase();
        if (!newRole.isEmpty()) {
            if (newRole.equals("ADMIN") || newRole.equals("TEACHER") || newRole.equals("STUDENT")) {
                userToUpdate.setRole(newRole);
            } else {
                ConsoleUtils.showError("Role không hợp lệ!");
            }
        }
        
        userManager.saveUserToFile();
        ConsoleUtils.showSuccess("Cập nhật thành công!");
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    private void deleteUser() {
        System.out.print("Nhập username cần xóa: ");
        String username = scanner.nextLine();
        
        // Không cho xóa chính mình
        if (userManager.getCurrentUser().getUsername().equals(username)) {
            ConsoleUtils.showError("Không thể xóa tài khoản đang đăng nhập!");
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
            ConsoleUtils.showError("Không tìm thấy người dùng!");
            ConsoleUtils.pressEnterToContinue(scanner);
            return;
        }
        
        System.out.print("Bạn có chắc chắn muốn xóa? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            userManager.getUsers().remove(userToDelete);
            userManager.saveUserToFile();
            ConsoleUtils.showSuccess("Xóa người dùng thành công!");
        }
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    // ==================== SAO LƯU & PHỤC HỒI ====================
    private void backupRestoreMenu() {
        FileService fileService = new FileServiceImpl();
        
        while (true) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.showHeader("SAO LƯU & PHỤC HỒI");
            
            System.out.println("1. 💾 Sao lưu dữ liệu");
            System.out.println("2. 📂 Danh sách bản sao lưu");
            System.out.println("3. 🔄 Khôi phục dữ liệu");
            System.out.println("4. 🔙 Quay lại");
            System.out.println("═══════════════════════════════════════════");
            System.out.print("Chọn: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    ConsoleUtils.showLoading("Đang sao lưu dữ liệu");
                    if (fileService.backupData()) {
                        ConsoleUtils.showSuccess("Sao lưu thành công!");
                    }
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "2":
                    ((FileServiceImpl) fileService).listBackup();
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "3":
                    System.out.print("Nhập tên file backup: ");
                    String backupFile = scanner.nextLine();
                    fileService.restoreData(backupFile);
                    ConsoleUtils.pressEnterToContinue(scanner);
                    break;
                case "4":
                    return;
                default:
                    ConsoleUtils.showError("Lựa chọn không hợp lệ!");
                    ConsoleUtils.pressEnterToContinue(scanner);
            }
        }
    }
    
    // ==================== ĐỔI MẬT KHẨU ====================
    private void changePassword() {
        userManager.changePassword(scanner);
        ConsoleUtils.pressEnterToContinue(scanner);
    }
    
    // Helper method
    private String truncate(String str, int length) {
        if (str == null) return "";
        if (str.length() <= length) return str;
        return str.substring(0, length - 3) + "...";
    }
}