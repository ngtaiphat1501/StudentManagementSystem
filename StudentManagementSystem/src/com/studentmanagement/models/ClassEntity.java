//(Khánh)
package com.studentmanagement.models;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author TUF GAMING
 */
public class ClassEntity implements Serializable {

    private String classId;
    private String className;
    private String departmentId;
    private String academicYear;
    private String advisor;
    private int totalStudents;
    private List<Student> students;

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

    // thêm học sinh 
    public void addStudent(Student student) {
        System.out.println("Add Student: " + student.getFullName());
        students.add(student);
    }

    //xóa học sinh
    public boolean removeStudent(String studentId) {
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.println("===Danh sách học sinh=== \n " + "Id: " + s.getId() + ", " + " Name: " + s.getFullName() + "\n");
            if (s.getId().equals(studentId)) {
                students.remove(i);  //  Xóa theo index
                System.out.println("Remove Student: " + studentId);
                return true;
            }
        }
        return false;
    }

    // + calculateStatistics(): ClassStatistics                                                        
}
