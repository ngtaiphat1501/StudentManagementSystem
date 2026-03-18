package com.studentmanagement.managers;

import com.studentmanagement.models.Student;
import com.studentmanagement.models.ClassEntity;
import com.studentmanagement.services.*;
import com.studentmanagement.utils.ConsoleUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManager {

    private StudentService studentService;
    private FileService fileService;
    private List<ClassEntity> classes;

    // contructor
    public StudentManager() {
        this.studentService = new StudentServiceImpl();
        this.fileService = new FileServiceImpl();
        this.classes = new ArrayList<>();
        initializeSampleClasses();
    }

    public List<Student> getStudents() {
        return studentService.getAllStudents(); // ✅ Gọi từ StudentService
    }

    private void initializeSampleClasses() {
        List<Student> students = studentService.getAllStudents();
        classes.add(new ClassEntity("K17CNTTA", "Công nghệ thông tin A", "CNTT", "2025", "GV. Ngach", students.size(), students));
        classes.add(new ClassEntity("K17QTKD", "Quản trị kinh doanh", "QTKD", "2025", "GV. Tham", students.size(), students));
    }
    // goi studentservice 

    public void addStudent(Scanner scanner) {
        studentService.addStudent(scanner);
    }
    // goi studentservice 

    public void updateStudent(Scanner scanner) {
        System.out.print("Nhập mã sinh viên cần sửa: ");
        String studentId = scanner.nextLine();
        studentService.updateStudent(studentId, scanner);
    }
    // goi studentservice 

    public void deleteStudent(Scanner scanner) {
        System.out.print("Nhập mã sinh viên cần xóa: ");
        String studentId = scanner.nextLine();
        if (studentService.deleteStudent(studentId)) {
            System.out.println(" Xóa sinh viên thành công!");
        } else {
            System.out.println("Không tìm thấy sinh viên!");
        }
    }
    // goi studentservice 

    public void searchStudent(Scanner scanner) {
        System.out.println("Tìm kiếm theo: 1-ID, 2-Tên, 3-Lớp, 4-Email");
        System.out.print("Chọn: ");
        String choice = scanner.nextLine();
        String type;
        switch (choice) {
            case "1":
                type = "id";
                break;
            case "2":
                type = "name";
                break;
            case "3":
                type = "class";
                break;
            case "4":
                type = "email";
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                return;
        }
        System.out.print("Nhập từ khóa: ");
        String keyword = scanner.nextLine();
        List<Student> results = studentService.searchStudent(type, keyword);
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy sinh viên nào.");
        } else {
            System.out.println("\nKẾT QUẢ TÌM KIẾM:");
            for (Student s : results) {
                s.displayInfo();
            }
        }
    }

    public void displayAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("Chưa có sinh viên nào.");
        } else {
            for (Student s : students) {
                s.displayInfo();
            }
        }
    }

    public Student findStudentByStudentId(String studentId) {
        return ((StudentServiceImpl) studentService).findStudentByStudentId(studentId);
    }
}
