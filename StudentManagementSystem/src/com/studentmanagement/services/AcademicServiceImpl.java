package com.studentmanagement.services;

import com.studentmanagement.models.Course;
import com.studentmanagement.models.Enrollment;
import com.studentmanagement.models.Grade;
import com.studentmanagement.models.Student;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of AcademicService for academic operations
 */
public class AcademicServiceImpl implements AcademicService {

    private List<Course> courses;
    private List<Enrollment> enrollments;
    private List<Grade> grades;
    private StudentService studentService;
    private int nextEnrollmentId = 1;
    private int nextGradeId = 1;

    /**
     * Constructor for AcademicServiceImpl
     * @param courses List of courses
     * @param enrollments List of enrollments
     * @param grades List of grades
     * @param studentService Student service instance
     */
    public AcademicServiceImpl(List<Course> courses, List<Enrollment> enrollments, List<Grade> grades, StudentService studentService) {
        this.courses = courses;
        this.enrollments = enrollments != null ? enrollments : new ArrayList<>();
        this.grades = grades != null ? grades : new ArrayList<>();
        this.studentService = studentService;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public int getNextEnrollmentId() {
        return nextEnrollmentId;
    }

    public void setNextEnrollmentId(int nextEnrollmentId) {
        this.nextEnrollmentId = nextEnrollmentId;
    }

    public int getNextGradeId() {
        return nextGradeId;
    }

    public void setNextGradeId(int nextGradeId) {
        this.nextGradeId = nextGradeId;
    }

    /**
     * Initializes sample academic data
     */
    public void initializeSampleData() {
        courses.add(new Course("PRO192", "Object Oriented Programming", 3, "SE", 2, "2026", "Mr.Son"));
        courses.add(new Course("NWC202", "Computer Networking", 3, "IT", 3, "2026", "Mr.Minh"));

        enrollments.add(new Enrollment(nextEnrollmentId++, "S001", "PRO192", "Spring", "2026"));
        enrollments.add(new Enrollment(nextEnrollmentId++, "S002", "NWC202", "Spring", "2026"));
        enrollments.add(new Enrollment(nextEnrollmentId++, "S003", "PRO192", "Spring", "2026"));

        grades.add(new Grade(nextGradeId++, 1, 9.0, 8.5, 8.0));
        grades.add(new Grade(nextGradeId++, 2, 8.0, 7.5, 7.0));
        grades.add(new Grade(nextGradeId++, 3, 6.5, 6.0, 6.0));
    }

    @Override
    public boolean registerCourse(String studentId, String courseId, String semester, String academicYear) {
        // Check if student exists
        Student student = ((StudentServiceImpl) studentService).findStudentByStudentId(studentId);
        if (student == null) {
            System.out.println(" Student not found: " + studentId);
            return false;
        }

        // Check if course exists
        Course course = findCourseByCourseId(courseId);
        if (course == null) {
            System.out.println(" Course not found: " + courseId);
            return false;
        }

        // Check if already registered
        if (isAlreadyRegistered(studentId, courseId, semester)) {
            System.out.println(" Student already registered for this course!");
            return false;
        }

        Enrollment enroll = new Enrollment(nextEnrollmentId++, studentId, courseId, semester, academicYear);
        enrollments.add(enroll);

        student.addRegisteredCourse(course);

        System.out.println(" Course registration successful!");
        enroll.displayEnrollmentInfo();
        return true;
    }

    @Override
    public boolean enterGrade(String studentId, String courseId, double attendance, double midterm, double finalScore) {
        Enrollment enrollment = findEnrollment(studentId, courseId);
        if (enrollment == null) {
            System.out.println(" Enrollment not found...");
            return false;
        }

        Grade grade = findGradeByEnrollment(enrollment.getEnrollmentId());

        if (grade == null) {
            grade = new Grade(nextGradeId++, enrollment.getEnrollmentId(), attendance, midterm, finalScore);
            grades.add(grade);
        } else {
            grade.enterGrade(attendance, midterm, finalScore);
        }

        // Update grade in course
        Course course = findCourseByCourseId(courseId);
        if (course != null) {
            course.setGrade(grade);
        }

        // Recalculate GPA for student
        Student student = ((StudentServiceImpl) studentService).findStudentByStudentId(studentId);
        if (student != null) {
            student.calculateGPA();
        }

        System.out.println(" Grade entry successful!");
        grade.displayGradeInfo();
        return true;
    }

    @Override
    public double calculateGPA(String studentId) {
        Student student = ((StudentServiceImpl) studentService).findStudentByStudentId(studentId);

        if (student == null) {
            System.out.println(" Student not found...");
            return 0.0;
        }

        student.calculateGPA();
        return student.getGpa();
    }

    @Override
    public List<Grade> getTranscript(String studentId) {
        ArrayList<Grade> transcript = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId().equals(studentId)) {
                Grade grade = findGradeByEnrollment(enrollment.getEnrollmentId());
                if (grade != null) {
                    transcript.add(grade);
                }
            }
        }

        return transcript;
    }

    @Override
    public List<Student> checkAcademicWarning() {
        List<Student> warningAcademic = new ArrayList<>();
        List<Student> allStudent = studentService.getAllStudents();

        for (Student student : allStudent) {
            boolean hasWarning = false;

            // Warning if GPA < 2.0
            if (student.getGpa() < 2.0) {
                hasWarning = true;
            }

            // Check if any course has F grade
            for (Course course : student.getRegisteredCourses()) {
                if (course.getGrade() != null && course.getGrade().getLetterGrade().equals("F")) {
                    hasWarning = true;
                    break;
                }
            }

            // Check failed credits
            int failedCredits = 0;
            for (Course course : student.getRegisteredCourses()) {
                if (course.getGrade() != null && course.getGrade().getLetterGrade().equals("F")) {
                    failedCredits += course.getCredits();
                }
            }
            if (failedCredits >= 12) {
                hasWarning = true;
            }

            if (hasWarning && !warningAcademic.contains(student)) {
                warningAcademic.add(student);
            }
        }
        return warningAcademic;
    }

    @Override
    public void generateAcademicReport() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║            ACADEMIC REPORT                   ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        System.out.println("Total courses: " + courses.size());
        System.out.println("Total enrollments: " + enrollments.size());
        System.out.println("Total grades entered: " + grades.size());

        // Grade statistics
        double totalScore = 0;
        int count = 0;
        for (Grade grade : grades) {
            totalScore += grade.getTotalScore();
            count++;
        }

        if (count > 0) {
            System.out.printf("Average score: %.2f\n", totalScore / count);
        }

        // Academic warning list
        List<Student> warningStudents = checkAcademicWarning();
        if (!warningStudents.isEmpty()) {
            System.out.println("\nACADEMIC WARNING LIST:");
            for (Student student : warningStudents) {
                System.out.printf("- %s (GPA: %.2f)\n", student.getFullName(), student.getGpa());
            }
        }
    }

    /**
     * Finds a course by its ID
     * @param Id Course ID to search for
     * @return Course object if found, null otherwise
     */
    public Course findCourseByCourseId(String Id) {
        if (Id == null || courses == null) {
            return null;
        }

        for (Course c : courses) {
            if (c.getCourseId() != null && c.getCourseId().trim().equalsIgnoreCase(Id.trim())) {
                return c;
            }
        }

        for (Course c : courses) {
            String code = extractCourseCode(c.getCourseName());
            if (code.equalsIgnoreCase(Id)) {
                return c;
            }
        }

        return null;
    }

    /**
     * Extracts course code from course name
     * @param courseName Course name with code in parentheses
     * @return Extracted course code
     */
    private String extractCourseCode(String courseName) {
        if (courseName == null) {
            return "";
        }
        int start = courseName.lastIndexOf("(");
        int end = courseName.lastIndexOf(")");
        if (start >= 0 && end > start) {
            return courseName.substring(start + 1, end).trim();
        }
        return "";
    }

    /**
     * Finds enrollment by student ID and course ID
     * @param studentId Student ID
     * @param courseId Course ID
     * @return Enrollment object if found, null otherwise
     */
    public Enrollment findEnrollment(String studentId, String courseId) {
        for (Enrollment enroll : enrollments) {
            if (enroll.getStudentId().equals(studentId) && enroll.getCourseId().equals(courseId)) {
                return enroll;
            }
        }
        return null;
    }

    /**
     * Finds grade by enrollment ID
     * @param enrollmentId Enrollment ID
     * @return Grade object if found, null otherwise
     */
    public Grade findGradeByEnrollment(int enrollmentId) {
        for (Grade g : grades) {
            if (g.getEnrollmentId() == enrollmentId) {
                return g;
            }
        }
        return null;
    }

    /**
     * Checks if a student is already registered for a course in a semester
     * @param studentId Student ID
     * @param courseId Course ID
     * @param semester Semester
     * @return true if already registered, false otherwise
     */
    private boolean isAlreadyRegistered(String studentId, String courseId, String semester) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId().equals(studentId)
                    && enrollment.getCourseId().equals(courseId)
                    && enrollment.getSemester().equals(semester)
                    && !enrollment.getStatus().equals("Cancelled")) {
                return true;
            }
        }
        return false;
    }
}