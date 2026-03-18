package com.studentmanagement.services;

import com.studentmanagement.models.Student;
import java.util.List;
import java.util.Scanner;

public interface StudentService {
    void addStudent(Scanner scanner);
    void updateStudent(String studentId, Scanner scanner);
    boolean deleteStudent(String studentId);
    List<Student> searchStudent(String searchType, String keyword);
    List<Student> getAllStudents();
    void exportToExcel(String filePath);
}