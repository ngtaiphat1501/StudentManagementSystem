package com.studentmanagement.managers;

import com.studentmanagement.models.Student;
import com.studentmanagement.models.ClassEntity;
import com.studentmanagement.services.*;
import com.studentmanagement.utils.ConsoleUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Manages student-related operations by delegating to StudentService
 */
public class StudentManager {

    private StudentService studentService;
    private FileService fileService;
    private List<ClassEntity> classes;

    /**
     * Constructor - initializes services and sample classes
     */
    public StudentManager() {
        this.studentService = new StudentServiceImpl();
        this.fileService = new FileServiceImpl();
        this.classes = new ArrayList<>();
        initializeSampleClasses();
    }

    /**
     * Gets all students from the service
     * @return List of all students
     */
    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }

    /**
     * Creates sample class data
     */
    private void initializeSampleClasses() {
        List<Student> students = studentService.getAllStudents();
        classes.add(new ClassEntity("K17CNTTA", "Information Technology A", "IT", "2025", "Mr. Ngach", students.size(), students));
        classes.add(new ClassEntity("K17QTKD", "Business Administration", "BA", "2025", "Mr. Tham", students.size(), students));
    }

    /**
     * Adds a new student
     * @param scanner Scanner for user input
     */
    public void addStudent(Scanner scanner) {
        studentService.addStudent(scanner);
    }

    /**
     * Updates an existing student
     * @param scanner Scanner for user input
     */
    public void updateStudent(Scanner scanner) {
        System.out.print("Enter student ID to update: ");
        String studentId = scanner.nextLine();
        studentService.updateStudent(studentId, scanner);
    }

    /**
     * Deletes a student after confirmation
     * @param scanner Scanner for user input
     */
    public void deleteStudent(Scanner scanner) {
        System.out.print("Enter student ID to delete: ");
        String studentId = scanner.nextLine();
        
        Student student = findStudentByStudentId(studentId);
        if (student == null) {
            System.out.println("❌ Student not found!");
            return;
        }
        
        student.displayInfo();
        System.out.print("Are you sure you want to delete? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            if (studentService.deleteStudent(studentId)) {
                System.out.println("✅ Student deleted successfully!");
            } else {
                System.out.println("❌ Failed to delete student!");
            }
        } else {
            System.out.println("Delete cancelled!");
        }
    }

    /**
     * Searches for students by various criteria
     * @param scanner Scanner for user input
     */
    public void searchStudent(Scanner scanner) {
        System.out.println("Search by: 1-ID, 2-Name, 3-Class, 4-Email");
        System.out.print("Choose: ");
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
                System.out.println("Invalid choice!");
                return;
        }
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();
        List<Student> results = studentService.searchStudent(type, keyword);
        if (results.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("\nSEARCH RESULTS:");
            for (Student s : results) {
                s.displayInfo();
            }
        }
    }

    /**
     * Displays all students
     */
    public void displayAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students yet.");
        } else {
            for (Student s : students) {
                s.displayInfo();
            }
        }
    }

    /**
     * Finds a student by their student ID
     * @param studentId Student ID to search for
     * @return Student object if found, null otherwise
     */
    public Student findStudentByStudentId(String studentId) {
        return ((StudentServiceImpl) studentService).findStudentByStudentId(studentId);
    }
    
    /**
     * Finds a student by email
     * @param email Email to search for
     * @return Student object if found, null otherwise
     */
    public Student findStudentByEmail(String email) {
        for (Student s : studentService.getAllStudents()) {
            if (s.getEmail() != null && s.getEmail().equalsIgnoreCase(email)) {
                return s;
            }
        }
        return null;
    }
    
    /**
     * Finds a student by name (accent-insensitive)
     * @param name Name to search for
     * @return Student object if found, null otherwise
     */
    public Student findStudentByName(String name) {
        String normalizedName = removeAccent(name.toLowerCase());
        for (Student s : studentService.getAllStudents()) {
            if (s.getFullName() != null && 
                removeAccent(s.getFullName().toLowerCase()).contains(normalizedName)) {
                return s;
            }
        }
        return null;
    }
    
    /**
     * Removes accents from a string for accent-insensitive search
     * @param str Input string
     * @return String with accents removed
     */
    private String removeAccent(String str) {
        if (str == null) return "";
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("")
                   .replace('đ', 'd')
                   .replace('Đ', 'D');
        } catch (Exception e) {
            return str;
        }
    }
}