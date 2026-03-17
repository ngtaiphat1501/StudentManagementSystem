package com.studentmanagement.managers;

import com.studentmanagement.models.Course;
import com.studentmanagement.services.AcademicService;
import com.studentmanagement.services.FileService;
import com.studentmanagement.services.FileServiceImpl;
import com.studentmanagement.utils.ConsoleUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CourseManager {
    private List<Course> courses;
    private FileService fileService;
    private int nextCourseId = 1;
    
    public CourseManager() {
        this.courses = new ArrayList<>();
        this.fileService = new FileServiceImpl();
        loadCoursesFromFile();
        
        if (courses.isEmpty()) {
            initializeSampleData();
        }
    }
    
    private void loadCoursesFromFile() {
        Object data = fileService.loadData("courses.dat");
        if (data instanceof List) {
            courses = (List<Course>) data;
            for (Course course : courses) {
                String courseId = course.getCourseId();
                if (courseId != null && courseId.startsWith("C")) {
                    try {
                        int id = Integer.parseInt(courseId.substring(1));
                        if (id >= nextCourseId) {
                            nextCourseId = id + 1;
                        }
                    } catch (NumberFormatException e) {
                        // Bỏ qua
                    }
                }
            }
            System.out.println(" Đã tải " + courses.size() + " môn học từ file");
        }
    }
    
    private void saveCoursesToFile() {
        fileService.saveData("courses.dat", courses);
    }
    
    private void initializeSampleData() {
       
        courses.add(new Course(
            generateCourseId(),
            "Lập trình Java cơ bản (CT101)",
            3,
            "CNTT",
            1,
            "2023-2024",
            "TS. Nguyễn Văn A"
        ));
        
        courses.add(new Course(
            generateCourseId(),
            "Cấu trúc dữ liệu và giải thuật (CT102)",
            4,
            "CNTT",
            1,
            "2023-2024",
            "PGS.TS. Trần Thị B"
        ));
        
        courses.add(new Course(
            generateCourseId(),
            "Cơ sở dữ liệu (CT201)",
            3,
            "CNTT",
            2,
            "2023-2024",
            "ThS. Lê Văn C"
        ));
        
        courses.add(new Course(
            generateCourseId(),
            "Mạng máy tính (CT202)",
            3,
            "CNTT",
            2,
            "2023-2024",
            "TS. Phạm Thị D"
        ));
        
        saveCoursesToFile();
        System.out.println(" Đã tạo dữ liệu mẫu cho môn học");
    }
    
    private String generateCourseId() {
        return "C" + String.format("%03d", nextCourseId++);
    }
    
    /**
     * Lấy mã môn học từ tên (format: "Tên môn (MÃ)")
     */ 
    private String extractCourseCode(String courseName) {
        if (courseName == null) return "";
        int start = courseName.lastIndexOf("(");
        int end = courseName.lastIndexOf(")");
        if (start >= 0 && end > start) {
            return courseName.substring(start + 1, end);
        }
        return "";
    }
    
    /**
     * Tạo tên môn học kèm mã
     */
    private String formatCourseName(String courseName, String courseCode) {
        return courseName + " (" + courseCode + ")";
    }
    
    public void addCourse(Scanner scanner) {
        ConsoleUtils.showHeader("THÊM MÔN HỌC MỚI");
        
        System.out.print("Mã môn học (ví dụ: CT101): ");
        String courseCode = scanner.nextLine();
        
        // Kiểm tra mã môn học đã tồn tại chưa
        if (findCourseByCode(courseCode) != null) {
            System.out.println(" Mã môn học đã tồn tại!");
            return;
        }
        
        System.out.print("Tên môn học: ");
        String courseName = scanner.nextLine();
        
        System.out.print("Số tín chỉ: ");
        int credits = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Khoa: ");
        String department = scanner.nextLine();
        
        System.out.print("Học kỳ (1-9): ");
        int semester = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Năm học (ví dụ: 2023-2024): ");
        String academicYear = scanner.nextLine();
        
        System.out.print("Giảng viên phụ trách: ");
        String teacher = scanner.nextLine();
        
        // Tạo tên môn học kèm mã
        String fullCourseName = formatCourseName(courseName, courseCode);
        
        Course course = new Course(
            generateCourseId(),
            fullCourseName,
            credits,
            department,
            semester,
            academicYear,
            teacher
        );
        
        courses.add(course);
        saveCoursesToFile();
        
        System.out.println("\n Thêm môn học thành công!");
        displayCourseInfo(course);
    }
    
    private void displayCourseInfo(Course course) {
        String courseCode = extractCourseCode(course.getCourseName());
        String courseName = course.getCourseName().replace(" (" + courseCode + ")", "");
        
        System.out.println("┌─────────────────────────────────────────────────────┐");
        System.out.println("│                   THÔNG TIN MÔN HỌC                 │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s: %-35s │\n", "Mã hệ thống", course.getCourseId());
        System.out.printf("│ %-15s: %-35s │\n", "Mã môn học", courseCode);
        System.out.printf("│ %-15s: %-35s │\n", "Tên môn học", courseName);
        System.out.printf("│ %-15s: %-35d │\n", "Số tín chỉ", course.getCredits());
        System.out.printf("│ %-15s: %-35s │\n", "Khoa", course.getDepartmentId());
        System.out.printf("│ %-15s: %-35s │\n", "Học kỳ", "HK" + course.getSemester());
        System.out.printf("│ %-15s: %-35s │\n", "Năm học", course.getAcademicYear());
        System.out.printf("│ %-15s: %-35s │\n", "Giảng viên", course.getTeacher());
        System.out.println("└─────────────────────────────────────────────────────┘");
    }
    
    public void updateCourse(Scanner scanner) {
        ConsoleUtils.showHeader("SỬA THÔNG TIN MÔN HỌC");
        
        System.out.print("Nhập mã môn học cần sửa (mã hệ thống): ");
        String courseId = scanner.nextLine();
        
        Course course = findCourseById(courseId);
        if (course == null) {
            System.out.println(" Không tìm thấy môn học với mã: " + courseId);
            return;
        }
        
        System.out.println("\nThông tin hiện tại:");
        displayCourseInfo(course);
        
        String oldCourseCode = extractCourseCode(course.getCourseName());
        String oldCourseName = course.getCourseName().replace(" (" + oldCourseCode + ")", "");
        
        System.out.println("\nNhập thông tin mới (Enter để giữ nguyên):");
        
        System.out.print("Mã môn học [" + oldCourseCode + "]: ");
        String newCourseCode = scanner.nextLine();
        if (newCourseCode.isEmpty()) {
            newCourseCode = oldCourseCode;
        } else {
            // Kiểm tra mã môn mới đã tồn tại chưa
            Course existingCourse = findCourseByCode(newCourseCode);
            if (existingCourse != null && !existingCourse.getCourseId().equals(courseId)) {
                System.out.println("Mã môn học đã tồn tại!");
                return;
            }
        }
        
        System.out.print("Tên môn học [" + oldCourseName + "]: ");
        String newCourseName = scanner.nextLine();
        if (newCourseName.isEmpty()) {
            newCourseName = oldCourseName;
        }
        
        // Cập nhật tên đầy đủ
        course.setCourseName(formatCourseName(newCourseName, newCourseCode));
        
        System.out.print("Số tín chỉ [" + course.getCredits() + "]: ");
        String creditsStr = scanner.nextLine();
        if (!creditsStr.isEmpty()) {
            course.setCredits(Integer.parseInt(creditsStr));
        }
        
        System.out.print("Khoa [" + course.getDepartmentId() + "]: ");
        String department = scanner.nextLine();
        if (!department.isEmpty()) {
            course.setDepartmentId(department);
        }
        
        System.out.print("Học kỳ [HK" + course.getSemester() + "]: ");
        String semesterStr = scanner.nextLine();
        if (!semesterStr.isEmpty()) {
            course.setSemester(Integer.parseInt(semesterStr));
        }
        
        System.out.print("Năm học [" + course.getAcademicYear() + "]: ");
        String academicYear = scanner.nextLine();
        if (!academicYear.isEmpty()) {
            course.setAcademicYear(academicYear);
        }
        
        System.out.print("Giảng viên [" + course.getTeacher() + "]: ");
        String teacher = scanner.nextLine();
        if (!teacher.isEmpty()) {
            course.setTeacher(teacher);
        }
        
        saveCoursesToFile();
        System.out.println("\n Cập nhật thông tin môn học thành công!");
        displayCourseInfo(course);
    }
    
    public boolean deleteCourse(Scanner scanner) {
        ConsoleUtils.showHeader("XÓA MÔN HỌC");
        
        System.out.print("Nhập mã môn học cần xóa (mã hệ thống): ");
        String courseId = scanner.nextLine();
        
        Course course = findCourseById(courseId);
        if (course == null) {
            System.out.println(" Không tìm thấy môn học với mã: " + courseId);
            return false;
        }
        
        System.out.println("\nThông tin môn học sẽ xóa:");
        displayCourseInfo(course);
        
        System.out.print("\nBạn có chắc chắn muốn xóa? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            courses.remove(course);
            saveCoursesToFile();
            System.out.println(" Xóa môn học thành công!");
            return true;
        } else {
            System.out.println("Đã hủy thao tác xóa!");
            return false;
        }
    }
    
    public Course findCourseById(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }
    
    public Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            String code = extractCourseCode(course.getCourseName());
            if (code.equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }
    
    public List<Course> findCoursesByName(String keyword) {
        List<Course> results = new ArrayList<>();
        for (Course course : courses) {
            String courseName = course.getCourseName()
                .replace(" (" + extractCourseCode(course.getCourseName()) + ")", "");
            if (courseName.toLowerCase().contains(keyword.toLowerCase())) {
                results.add(course);
            }
        }
        return results;
    }
    
    //Lọc ra tất cả môn học thuộc một khoa 
    public List<Course> getCoursesByDepartment(String department) {
        List<Course> results = new ArrayList<>();
        for (Course course : courses) {
            if (course.getDepartmentId() != null && 
                course.getDepartmentId().equalsIgnoreCase(department)) {
                results.add(course);
            }
        }
        return results;
    }
    
    public List<Course> findCoursesByTeacher(String teacher) {
        List<Course> results = new ArrayList<>();
        for (Course course : courses) {
            if (course.getTeacher() != null && 
                course.getTeacher().toLowerCase().contains(teacher.toLowerCase())) {
                results.add(course);
            }
        }
        return results;
    }
    
    public void displayAllCourses() {
        if (courses.isEmpty()) {
            System.out.println(" Danh sách môn học trống!");
            return;
        }
        
        System.out.println("\n┌────┬──────────┬────────────────────────────────────┬──────────┬────────────┬──────────┬─────────────┐");
        System.out.println("│ STT│ Mã môn   │ Tên môn học                        │ Số TC    │ Khoa       │ Học kỳ   │ Giảng viên  │");
        System.out.println("├────┼──────────┼────────────────────────────────────┼──────────┼────────────┼──────────┼─────────────┤");
        
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            String courseCode = extractCourseCode(course.getCourseName());
            String courseName = course.getCourseName().replace(" (" + courseCode + ")", "");
            
            System.out.printf("│ %-3d│ %-8s │ %-34s │ %-6d │ %-10s │ HK%-5d │ %-11s │\n",
                i + 1,
                courseCode,
                truncateString(courseName, 34),
                course.getCredits(),
                truncateString(course.getDepartmentId(), 10),
                course.getSemester(),
                truncateString(course.getTeacher(), 11)
            );
        }
        
        System.out.println("└────┴──────────┴────────────────────────────────────┴──────────┴────────────┴──────────┴─────────────┘");
        System.out.println(" Tổng số môn học: " + courses.size());
    }
    
    //Cắt chuỗi nếu quá dài để hiển thị đẹp trong bảng
    private String truncateString(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
    
    public void showCourseStatistics() {
        if (courses.isEmpty()) {
            System.out.println(" Không có dữ liệu để thống kê!");
            return;
        }
        
        ConsoleUtils.showHeader("THỐNG KÊ MÔN HỌC");
        
        int totalCourses = courses.size();
        int totalCredits = 0;
        
        Map<String, Integer> departmentCount = new HashMap<>();
        Map<Integer, Integer> semesterCount = new HashMap<>();
        
        for (Course course : courses) {
            totalCredits += course.getCredits();
            
            String dept = course.getDepartmentId();
            departmentCount.put(dept, departmentCount.getOrDefault(dept, 0) + 1);
            
            int sem = course.getSemester();
            semesterCount.put(sem, semesterCount.getOrDefault(sem, 0) + 1);
        }
        
        double avgCredits = (double) totalCredits / totalCourses;
        
        System.out.println(" TỔNG QUAN:");
        System.out.println(" Tổng số môn học: " + totalCourses);
        System.out.printf(" Tổng số tín chỉ: %d\n", totalCredits);
        System.out.printf(" Trung bình tín chỉ/môn: %.1f\n", avgCredits);
        
        System.out.println("\n PHÂN BỐ THEO KHOA:");
        for (Map.Entry<String, Integer> entry : departmentCount.entrySet()) {
            System.out.printf(" %s: %d môn (%.1f%%)\n", 
                entry.getKey(), entry.getValue(),
                (entry.getValue() * 100.0 / totalCourses));
        }
        
        System.out.println("\n PHÂN BỐ THEO HỌC KỲ:");
        for (int i = 1; i <= 9; i++) {
            int count = semesterCount.getOrDefault(i, 0);
            if (count > 0) {
                System.out.printf(" Học kỳ %d: %d môn\n", i, count);
            }
        }
    }
    
    public List<Course> getCourses() {
        return courses;
    }
    
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    
    public int getCourseCount() {
        return courses.size();
    }
}
