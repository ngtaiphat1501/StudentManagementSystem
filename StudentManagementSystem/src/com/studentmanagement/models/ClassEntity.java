package com.studentmanagement.models;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a class entity containing a group of students
 */
public class ClassEntity implements Serializable {

    private String classId;
    private String className;
    private String departmentId;
    private String academicYear;
    private String advisor;
    private int totalStudents;
    private List<Student> students;

    /**
     * Constructor for ClassEntity
     * @param classId Class ID
     * @param className Class name
     * @param departmentId Department ID
     * @param academicYear Academic year
     * @param advisor Advisor name
     * @param totalStudents Total student count
     * @param students List of students in the class
     */
    public ClassEntity(String classId, String className, String departmentId, String academicYear, String advisor, int totalStudents, List<Student> students) {
        this.classId = classId;
        this.className = className;
        this.departmentId = departmentId;
        this.academicYear = academicYear;
        this.advisor = advisor;
        this.totalStudents = totalStudents;
        this.students = students;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * Adds a student to the class
     * @param student Student to add
     */
    public void addStudent(Student student) {
        System.out.println("Add Student: " + student.getFullName());
        students.add(student);
    }

    /**
     * Removes a student from the class
     * @param studentId ID of student to remove
     * @return true if removal successful, false otherwise
     */
    public boolean removeStudent(String studentId) {
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.println("===Student list=== \n " + "Id: " + s.getId() + ", " + " Name: " + s.getFullName() + "\n");
            if (s.getId().equals(studentId)) {
                students.remove(i);
                System.out.println("Remove Student: " + studentId);
                return true;
            }
        }
        return false;
    }
}