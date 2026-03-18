package com.studentmanagement.managers;

import com.studentmanagement.models.Course;
import com.studentmanagement.services.FileService;
import com.studentmanagement.services.FileServiceImpl;
import com.studentmanagement.utils.ConsoleUtils;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

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
                        // Skip
                    }
                }
            }
            System.out.println("📥 Loaded " + courses.size() + " courses from file");
        }
    }

    private void saveCoursesToFile() {
        fileService.saveData("courses.dat", courses);
    }

    private void initializeSampleData() {
        courses.add(new Course(
                generateCourseId(),
                "Java Programming Basics (CT101)",
                3,
                "IT",
                1,
                "2023-2024",
                "Dr. Nguyen Van A"
        ));

        courses.add(new Course(
                generateCourseId(),
                "Data Structures and Algorithms (CT102)",
                4,
                "IT",
                1,
                "2023-2024",
                "Prof. Tran Thi B"
        ));

        courses.add(new Course(
                generateCourseId(),
                "Database Systems (CT201)",
                3,
                "IT",
                2,
                "2023-2024",
                "MSc. Le Van C"
        ));

        courses.add(new Course(
                generateCourseId(),
                "Computer Networks (CT202)",
                3,
                "IT",
                2,
                "2023-2024",
                "Dr. Pham Thi D"
        ));

        courses.add(new Course(
                generateCourseId(),
                "Advanced Mathematics (MATH101)",
                3,
                "IT",
                2,
                "2023-2024",
                "Prof. Nguyen Van E"
        ));

        saveCoursesToFile();
        System.out.println("✅ Sample course data created");
    }

    private String generateCourseId() {
        return "C" + String.format("%03d", nextCourseId++);
    }

    public String extractCourseCode(String courseName) {
        if (courseName == null) {
            return "";
        }
        int start = courseName.lastIndexOf("(");
        int end = courseName.lastIndexOf(")");
        if (start >= 0 && end > start) {
            return courseName.substring(start + 1, end);
        }
        return "";
    }

    private String formatCourseName(String courseName, String courseCode) {
        return courseName + " (" + courseCode + ")";
    }

    public void addCourse(Scanner scanner) {
        ConsoleUtils.showHeader("ADD NEW COURSE");

        System.out.print("Course code (e.g., CT101): ");
        String courseCode = scanner.nextLine();

        // Check if course code already exists
        if (findCourseByCode(courseCode) != null) {
            System.out.println("❌ Course code already exists!");
            return;
        }

        System.out.print("Course name: ");
        String courseName = scanner.nextLine();

        System.out.print("Credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Department: ");
        String department = scanner.nextLine();

        System.out.print("Semester (1-9): ");
        int semester = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Academic year (e.g., 2023-2024): ");
        String academicYear = scanner.nextLine();

        System.out.print("Teacher: ");
        String teacher = scanner.nextLine();

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

        System.out.println("\n✅ Course added successfully!");
        displayCourseInfo(course);
    }

    private void displayCourseInfo(Course course) {
        String courseCode = extractCourseCode(course.getCourseName());
        String courseName = course.getCourseName().replace(" (" + courseCode + ")", "");

        System.out.println("┌─────────────────────────────────────────────────────┐");
        System.out.println("│                   COURSE INFORMATION                │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s: %-35s │\n", "System ID", course.getCourseId());
        System.out.printf("│ %-15s: %-35s │\n", "Course Code", courseCode);
        System.out.printf("│ %-15s: %-35s │\n", "Course Name", courseName);
        System.out.printf("│ %-15s: %-35d │\n", "Credits", course.getCredits());
        System.out.printf("│ %-15s: %-35s │\n", "Department", course.getDepartmentId());
        System.out.printf("│ %-15s: %-35s │\n", "Semester", "Sem" + course.getSemester());
        System.out.printf("│ %-15s: %-35s │\n", "Academic Year", course.getAcademicYear());
        System.out.printf("│ %-15s: %-35s │\n", "Teacher", course.getTeacher());
        System.out.println("└─────────────────────────────────────────────────────┘");
    }

    public void updateCourse(Scanner scanner) {
        ConsoleUtils.showHeader("UPDATE COURSE INFORMATION");

        System.out.print("Enter course system ID to update: ");
        String courseId = scanner.nextLine();

        Course course = findCourseById(courseId);
        if (course == null) {
            System.out.println("❌ Course not found with ID: " + courseId);
            return;
        }

        System.out.println("\nCurrent information:");
        displayCourseInfo(course);

        String oldCourseCode = extractCourseCode(course.getCourseName());
        String oldCourseName = course.getCourseName().replace(" (" + oldCourseCode + ")", "");

        System.out.println("\nEnter new information (Enter to keep current):");

        System.out.print("Course code [" + oldCourseCode + "]: ");
        String newCourseCode = scanner.nextLine();
        if (newCourseCode.isEmpty()) {
            newCourseCode = oldCourseCode;
        } else {
            Course existingCourse = findCourseByCode(newCourseCode);
            if (existingCourse != null && !existingCourse.getCourseId().equalsIgnoreCase(courseId)) {
                System.out.println("❌ Course code already exists!");
                return;
            }
        }

        System.out.print("Course name [" + oldCourseName + "]: ");
        String newCourseName = scanner.nextLine();
        if (newCourseName.isEmpty()) {
            newCourseName = oldCourseName;
        }

        course.setCourseName(formatCourseName(newCourseName, newCourseCode));

        System.out.print("Credits [" + course.getCredits() + "]: ");
        String creditsStr = scanner.nextLine();
        if (!creditsStr.isEmpty()) {
            course.setCredits(Integer.parseInt(creditsStr));
        }

        System.out.print("Department [" + course.getDepartmentId() + "]: ");
        String department = scanner.nextLine();
        if (!department.isEmpty()) {
            course.setDepartmentId(department);
        }

        System.out.print("Semester [Sem" + course.getSemester() + "]: ");
        String semesterStr = scanner.nextLine();
        if (!semesterStr.isEmpty()) {
            course.setSemester(Integer.parseInt(semesterStr));
        }

        System.out.print("Academic year [" + course.getAcademicYear() + "]: ");
        String academicYear = scanner.nextLine();
        if (!academicYear.isEmpty()) {
            course.setAcademicYear(academicYear);
        }

        System.out.print("Teacher [" + course.getTeacher() + "]: ");
        String teacher = scanner.nextLine();
        if (!teacher.isEmpty()) {
            course.setTeacher(teacher);
        }

        saveCoursesToFile();
        System.out.println("\n✅ Course information updated successfully!");
        displayCourseInfo(course);
    }

    public boolean deleteCourse(Scanner scanner) {
        ConsoleUtils.showHeader("DELETE COURSE");

        System.out.print("Enter course system ID to delete: ");
        String courseId = scanner.nextLine();

        Course course = findCourseById(courseId);
        if (course == null) {
            System.out.println("❌ Course not found with ID: " + courseId);
            return false;
        }

        System.out.println("\nCourse to delete:");
        displayCourseInfo(course);

        System.out.print("\nAre you sure you want to delete? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            courses.remove(course);
            saveCoursesToFile();
            System.out.println("✅ Course deleted successfully!");
            return true;
        } else {
            System.out.println("Delete cancelled!");
            return false;
        }
    }

    public Course findCourseById(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equalsIgnoreCase(courseId)) {
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

        String normalizedKeyword = removeAccent(keyword.toLowerCase().trim());

        for (Course course : courses) {
            String courseName = course.getCourseName()
                    .replace(" (" + extractCourseCode(course.getCourseName()) + ")", "");

            String normalizedCourseName = removeAccent(courseName.toLowerCase());

            if (normalizedCourseName.contains(normalizedKeyword)) {
                results.add(course);
            } else if (courseName.toLowerCase().contains(keyword.toLowerCase())) {
                results.add(course);
            }
        }
        return results;
    }

    private String removeAccent(String str) {
        if (str == null) {
            return "";
        }
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

    public List<Course> findCoursesByAcademicYear(String academicYear) {
        List<Course> results = new ArrayList<>();
        for (Course course : courses) {
            if (course.getAcademicYear() != null
                    && course.getAcademicYear().contains(academicYear)) {
                results.add(course);
            }
        }
        return results;
    }

    public List<Course> getCoursesByDepartment(String department) {
        List<Course> results = new ArrayList<>();
        for (Course course : courses) {
            if (course.getDepartmentId() != null
                    && course.getDepartmentId().equalsIgnoreCase(department)) {
                results.add(course);
            }
        }
        return results;
    }

    public List<Course> findCoursesByTeacher(String teacher) {
        List<Course> results = new ArrayList<>();
        for (Course course : courses) {
            if (course.getTeacher() != null
                    && course.getTeacher().toLowerCase().contains(teacher.toLowerCase())) {
                results.add(course);
            }
        }
        return results;
    }

    // CHỈ GIỮ LẠI MỘT METHOD displayAllCourses() - ĐÃ XÓA METHOD TRÙNG LẶP
    public void displayAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("📭 Course list is empty!");
            return;
        }

        System.out.println("\n┌────┬──────────┬────────────────────────────────────┬──────────┬────────────┬──────────┬─────────────┐");
        System.out.println("│ No │ Code     │ Course Name                        │ Credits  │ Department │ Semester │ Teacher     │");
        System.out.println("├────┼──────────┼────────────────────────────────────┼──────────┼────────────┼──────────┼─────────────┤");

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            String courseCode = extractCourseCode(course.getCourseName());

            String courseName = course.getCourseName();
            if (courseName.contains("(")) {
                courseName = courseName.substring(0, courseName.lastIndexOf("(")).trim();
            }

            System.out.printf("│ %-3d│ %-8s │ %-34s │ %-6d │ %-10s │ Sem%-5d │ %-11s │\n",
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
        System.out.println(" Total courses: " + courses.size());
    }

    private String truncateString(String str, int maxLength) {
        if (str == null) {
            return "";
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }

    public void showCourseStatistics() {
        if (courses.isEmpty()) {
            System.out.println("📭 No data for statistics!");
            return;
        }

        ConsoleUtils.showHeader("COURSE STATISTICS");

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

        System.out.println("📊 OVERVIEW:");
        System.out.println(" Total courses: " + totalCourses);
        System.out.printf(" Total credits: %d\n", totalCredits);
        System.out.printf(" Average credits/course: %.1f\n", avgCredits);

        System.out.println("\n📊 BY DEPARTMENT:");
        for (Map.Entry<String, Integer> entry : departmentCount.entrySet()) {
            System.out.printf(" %s: %d courses (%.1f%%)\n",
                    entry.getKey(), entry.getValue(),
                    (entry.getValue() * 100.0 / totalCourses));
        }

        System.out.println("\n📊 BY SEMESTER:");
        for (int i = 1; i <= 9; i++) {
            int count = semesterCount.getOrDefault(i, 0);
            if (count > 0) {
                System.out.printf(" Semester %d: %d courses\n", i, count);
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