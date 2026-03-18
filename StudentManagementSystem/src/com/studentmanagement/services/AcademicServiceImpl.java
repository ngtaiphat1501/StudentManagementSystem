//Khanh
package com.studentmanagement.services;

import com.studentmanagement.models.Course;
import com.studentmanagement.models.Enrollment;
import com.studentmanagement.models.Grade;
import com.studentmanagement.models.Student;
import java.util.ArrayList;

import java.util.List;

/**
 *
 * @author TUF GAMING
 */
public class AcademicServiceImpl implements AcademicService {

    private List<Course> courses;
    private List<Enrollment> enrollments;
    private List<Grade> grades;
    private StudentService studentService;
    private int nextEnrollmentId;
    private int nextGradeId;

    public AcademicServiceImpl(List<Course> courses, List<Enrollment> enrollments, List<Grade> grades, StudentService studentService) {
        this.courses = courses;
        this.enrollments = enrollments;
        this.grades = grades;
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

    public int getNextGradeInt() {
        return nextGradeId;
    }

    public void setNextGradeInt(int nextGradeId) {
        this.nextGradeId = nextGradeId;
    }

    public void initializeSampleData() {
        courses.add(new Course("PRO192", "Object Oriented Programming", 3, "SE", 2, "2026", "Mr.Son"));
        courses.add(new Course("NWC202", "Computer Networking", 3, "IT", 3, "2026", "Mr.Minh"));

        enrollments.add(new Enrollment(1, "S001", "PRO192", "Spring", "2026"));
        enrollments.add(new Enrollment(2, "S002", "NWC202", "Spring", "2026"));
        enrollments.add(new Enrollment(3, "S003", "PRO192", "Spring", "2026"));

        grades.add(new Grade(1, 1, 9.0, 8.5, 8.0));
        grades.add(new Grade(2, 2, 8.0, 7.5, 7.0));
        grades.add(new Grade(3, 3, 6.5, 6.0, 6.0));
    }

    @Override
    public boolean registerCourse(String studentId, String courseId, String semester, String academicYear) {
        // kiểm tra có học sinh 
        Student student = ((StudentServiceImpl) studentService).findStudentByStudentId(studentId);
        if (student == null) {
            System.out.println("Không tìm thấy Id học sinh...");
            return false;

        }
        //kiểm tra có môn không
        Course courses = findCourseByCourseId(studentId);
        if (courses == null) {
            System.out.println("Không tìm thấy môn học....");
            return false;
        }
        //kiểm tra sinh viên đã đăng ký chưa
        if (!isAlreadyRegistered(studentId, courseId, semester)) {
            System.out.println("Sinh viên đã đăng ký môn này ...");
            return false;
        }

        Enrollment enroll = new Enrollment(nextEnrollmentId++, studentId, courseId, semester, academicYear);

        enrollments.add(enroll);

        student.addRegisteredCourse(courses);

        System.out.println("✅ Đăng ký môn học thành công!");
        enroll.displayEnrollmentInfo();
        return true;
    }

    @Override
    public boolean enterGrade(String studentId, String courseId, double attendance, double midterm, double finalScore) {
        Enrollment enrollment = findEnrollment(studentId, courseId);
        if (enrollment == null) {
            System.out.println("không tìm thấy đăng ký môn học...");
            return false;
        }

        Grade grade = findGradebyEnrollment(enrollment.getEnrollmentId());

        if (grade == null) {
            grade = new Grade(nextGradeId++, enrollment.getEnrollmentId(), attendance, midterm, finalScore);
        } else {
            grade.enterGrade(attendance, midterm, finalScore);
        }

        // Cập nhật điểm vào môn học
        Course course = findCourseByCourseId(courseId);
        if (course != null) {
            course.setGrade(grade);
        }

        // Tính lại GPA cho sinh viên
        Student student = ((StudentServiceImpl) studentService).findStudentByStudentId(studentId);
        if (student != null) {
            student.calculateGPA();
        }

        System.out.println(" Nhập điểm thành công!");

        grade.displayGradeInfo();
        return true;
    }

    //Tính GPA học sinh
    @Override
    public double calculateGPA(String studentId) {
        Student student = ((StudentServiceImpl) studentService).findStudentByStudentId(studentId);

        if (student == null) {
            System.out.println("Không tìm thấy học sinh...");
            return 0.0;
        }

        student.calculateGPA();
        return student.getGpa();
    }

    // xem bảng điểm bằng enrollment
    @Override
    public List<Grade> getTranscript(String studentId) {
        ArrayList<Grade> transcript = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId().equals(studentId)) {
                Grade grade = findGradebyEnrollment(enrollment.getEnrollmentId());
                if (grade != null) {
                    transcript.add(grade);
                }
            }
        }

        return transcript;
    }

    //kiểm tra học vụ
    @Override
    public List<Student> checkAcademicWarning() {
        List<Student> warningAcademic = new ArrayList<>();
        List<Student> allStudent = studentService.getAllStudents();

        for (Student student : allStudent) {
            boolean hasWarning = false;

            // Cảnh báo nếu GPA < 2.0
            if (student.getGpa() < 2.0) {
                hasWarning = true;
            }

            // Kiểm tra có môn nào điểm F không
            for (Course course : student.getRegisteredCourses()) {
                if (course.getGrade() != null && course.getGrade().getLetterGrade().equals("F")) {
                    hasWarning = true;
                    break;  // Thoát vòng lặp khi tìm thấy môn F đầu tiên
                }
            }

            // Kiểm tra số tín chỉ nợ (nếu có nhiều môn F)
            int failedCredits = 0;
            for (Course course : student.getRegisteredCourses()) {
                if (course.getGrade() != null && course.getGrade().getLetterGrade().equals("F")) {
                    failedCredits += course.getCredits();
                }
            }
            if (failedCredits >= 12) {  // Nợ >= 12 tín chỉ
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
        System.out.println("║            BÁO CÁO HỌC TẬP                   ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        System.out.println("Tổng số môn học: " + courses.size());
        System.out.println("Tổng số đăng ký: " + enrollments.size());
        System.out.println("Tổng số điểm đã nhập: " + grades.size());

        // Thống kê điểm
        double totalScore = 0;
        int count = 0;
        for (Grade grade : grades) {
            totalScore += grade.getTotalScore();
            count++;
        }

        if (count > 0) {
            System.out.printf("Điểm trung bình toàn hệ thống: %.2f\n", totalScore / count);
        }

        // Danh sách sinh viên bị cảnh báo
        List<Student> warningStudents = checkAcademicWarning();
        if (!warningStudents.isEmpty()) {
            System.out.println("\nDANH SÁCH CẢNH BÁO HỌC VỤ:");
            for (Student student : warningStudents) {
                System.out.printf("- %s (GPA: %.2f)\n", student.getFullName(), student.getGpa());
            }
        }
    }

    public Course findCourseByCourseId(String Id) {

        for (Course c : courses) {
            if (c.getCourseId().equals(Id)) {
                return c;
            }
        }

        return null;
    }

    public Enrollment findEnrollment(String studentId, String courseId) {
        for (Enrollment enroll : enrollments) {
            if (enroll.getStudentId().equals(studentId) && enroll.getCourseId().equals(courseId)) {
                return enroll;
            }
        }
        return null;
    }

    public Grade findGradebyEnrollment(int enrollmentId) {
        for (Grade g : grades) {
            if (g.getGradeId() == enrollmentId) {
                return g;
            }
        }
        return null;
    }

    private boolean isAlreadyRegistered(String studentId, String courseId, String semester) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId().equals(studentId)
                    && enrollment.getCourseId().equals(courseId)
                    && enrollment.getSemester().equals(semester)) {
                return true;
            }
        }
        return false;
    }

}
