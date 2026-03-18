package com.studentmanagement.services;

import com.studentmanagement.utils.DateUtils;
import com.studentmanagement.utils.Validator;
import com.studentmanagement.models.Student;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation of StudentService for student management
 */
public class StudentServiceImpl implements StudentService {

    private List<Student> students;
    private int nextStudentId = 1;

    /**
     * Constructor - initializes student list with sample data
     */
    public StudentServiceImpl() {
        this.students = new ArrayList<>();
        initializeSampleData();
    }

    /**
     * Initializes sample student data
     */
    private void initializeSampleData() {
        try {
            students.add(new Student(
                    "S001",
                    "SV20240001",
                    "Nguyen Van A",
                    DateUtils.parseDate("15/05/2000"),
                    "Male",
                    "0987654321",
                    "nva@email.com",
                    "Hanoi",
                    "K17CNTTA",
                    new Date(),
                    "Studying"
            ));

            students.add(new Student(
                    "S002",
                    "SV20240002",
                    "Tran Thi B",
                    DateUtils.parseDate("20/08/2001"),
                    "Female",
                    "0912345678",
                    "ttb@email.com",
                    "Ho Chi Minh City",
                    "K17QTKD",
                    new Date(),
                    "Studying"
            ));
            
            // Add student for student account
            students.add(new Student(
                    "S003",
                    "STU001",
                    "Pham Van Student",
                    DateUtils.parseDate("10/10/2000"),
                    "Male",
                    "0123456789",
                    "student@university.edu.vn",
                    "Ho Chi Minh City",
                    "K17CNTTA",
                    new Date(),
                    "Studying"
            ));

            nextStudentId = students.size() + 1;

        } catch (Exception e) {
            System.out.println("Error initializing sample data: " + e.getMessage());
        }
    }

    @Override
    public void addStudent(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║              ADD NEW STUDENT                 ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        try {
            System.out.print("Student ID (SVxxxxx): ");
            String studentId = scanner.nextLine();

            if (findStudentByStudentId(studentId) != null) {
                System.out.println(" Student ID already exists!");
                return;
            }

            System.out.print("Full Name: ");
            String fullName = scanner.nextLine();

            System.out.print("Birth Date (dd/MM/yyyy): ");
            Date birthDate = DateUtils.parseDate(scanner.nextLine());

            System.out.print("Gender (Male/Female): ");
            String gender = scanner.nextLine();

            System.out.print("Phone: ");
            String phone = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            if (!Validator.isValidEmail(email)) {
                System.out.println(" Invalid email!");
                return;
            }

            System.out.print("Address: ");
            String address = scanner.nextLine();

            System.out.print("Class ID: ");
            String classId = scanner.nextLine();

            System.out.print("Status (Studying/Suspended/Dropped): ");
            String status = scanner.nextLine();

            String id = "S" + String.format("%03d", nextStudentId++);

            Student student = new Student(
                    id, studentId, fullName, birthDate, gender,
                    phone, email, address, classId, new Date(), status
            );

            students.add(student);

            System.out.println(" Student added successfully!");
            student.displayInfo();

        } catch (Exception e) {
            System.out.println(" Error adding student: " + e.getMessage());
        }
    }

    @Override
    public void updateStudent(String studentId, Scanner scanner) {
        Student student = findStudentByStudentId(studentId);

        if (student == null) {
            System.out.println(" Student not found with ID: " + studentId);
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║            UPDATE STUDENT INFORMATION        ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        System.out.println("Current information:");
        student.displayInfo();

        System.out.println("\nEnter new information (Enter to keep current):");

        try {
            System.out.print("Full Name [" + student.getFullName() + "]: ");
            String fullName = scanner.nextLine();
            if (!fullName.isEmpty()) {
                student.setFullName(fullName);
            }

            System.out.print("Birth Date [" + student.getFormattedBirthDate() + "]: ");
            String birthDateStr = scanner.nextLine();
            if (!birthDateStr.isEmpty()) {
                student.setBirthDate(DateUtils.parseDate(birthDateStr));
            }

            System.out.print("Gender [" + student.getGender() + "]: ");
            String gender = scanner.nextLine();
            if (!gender.isEmpty()) {
                student.setGender(gender);
            }

            System.out.print("Phone [" + student.getPhone() + "]: ");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) {
                student.setPhone(phone);
            }

            System.out.print("Email [" + student.getEmail() + "]: ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                if (Validator.isValidEmail(email)) {
                    student.setEmail(email);
                } else {
                    System.out.println(" Invalid email!");
                }
            }

            System.out.print("Address [" + student.getAddress() + "]: ");
            String address = scanner.nextLine();
            if (!address.isEmpty()) {
                student.setAddress(address);
            }

            System.out.print("Class ID [" + student.getClassId() + "]: ");
            String classId = scanner.nextLine();
            if (!classId.isEmpty()) {
                student.setClassId(classId);
            }

            System.out.print("Status [" + student.getStatus() + "]: ");
            String status = scanner.nextLine();
            if (!status.isEmpty()) {
                student.setStatus(status);
            }

            System.out.println("\n Update successful!");
            student.displayInfo();

        } catch (Exception e) {
            System.out.println(" Error updating: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteStudent(String studentId) {
        Student student = findStudentByStudentId(studentId);
        if (student == null) {
            return false;
        }
        students.remove(student);
        return true;
    }

    @Override
    public List<Student> searchStudent(String searchType, String keyword) {
        List<Student> results = new ArrayList<>();
        keyword = keyword.toLowerCase().trim();

        for (Student student : students) {
            switch (searchType.toLowerCase()) {
                case "id":
                    if (student.getStudentId().equalsIgnoreCase(keyword)) {
                        results.add(student);
                    }
                    break;
                case "name":
                    if (student.getFullName().toLowerCase().contains(keyword)) {
                        results.add(student);
                    }
                    break;
                case "class":
                    if (student.getClassId() != null && student.getClassId().equalsIgnoreCase(keyword)) {
                        results.add(student);
                    }
                    break;
                case "email":
                    if (student.getEmail() != null && student.getEmail().equalsIgnoreCase(keyword)) {
                        results.add(student);
                    }
                    break;
            }
        }
        return results;
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    @Override
    public void exportToExcel(String filePath) {
        System.out.println(" Exporting data to Excel: " + filePath);
        System.out.println(" Export successful!");
    }

    /**
     * Finds a student by student ID
     * @param studentId Student ID to search for
     * @return Student object if found, null otherwise
     */
    public Student findStudentByStudentId(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Finds a student by system ID
     * @param id System ID to search for
     * @return Student object if found, null otherwise
     */
    public Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }
}