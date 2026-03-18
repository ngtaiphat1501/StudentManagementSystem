package com.studentmanagement.services;

import com.studentmanagement.models.Student;
import java.util.List;
import java.util.Scanner;

/**
 * Interface for student management operations
 */
public interface StudentService {
    /**
     * Adds a new student
     * @param scanner Scanner for user input
     */
    void addStudent(Scanner scanner);
    
    /**
     * Updates an existing student
     * @param studentId Student ID to update
     * @param scanner Scanner for user input
     */
    void updateStudent(String studentId, Scanner scanner);
    
    /**
     * Deletes a student
     * @param studentId Student ID to delete
     * @return true if successful, false otherwise
     */
    boolean deleteStudent(String studentId);
    
    /**
     * Searches for students by various criteria
     * @param searchType Search type (id/name/class/email)
     * @param keyword Search keyword
     * @return List of matching students
     */
    List<Student> searchStudent(String searchType, String keyword);
    
    /**
     * Gets all students
     * @return List of all students
     */
    List<Student> getAllStudents();
    
    /**
     * Exports student data to Excel file
     * @param filePath Path to export file
     */
    void exportToExcel(String filePath);
}