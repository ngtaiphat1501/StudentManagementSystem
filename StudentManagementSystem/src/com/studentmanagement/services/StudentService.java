package com.studentmanagement.services;

import com.studentmanagement.models.Student;
import java.util.List;
import java.util.Scanner;

public interface StudentService {
    // 1.1. Thêm sinh viên
    void addStudent(Scanner scanner);
    
    // 1.2. Sửa thông tin sinh viên
    void updateStudent(String studentId, Scanner scanner);
    
    // 1.3. Xóa sinh viên
    boolean deleteStudent(String studentId);
    
    // 1.4. Tìm kiếm sinh viên
    List<Student> searchStudent(String searchType, String keyword);
    
    // 1.5. Xem tất cả sinh viên
    List<Student> getAllStudents();
    
    // Xuất ra Excel
    void exportToExcel(String filePath);
}