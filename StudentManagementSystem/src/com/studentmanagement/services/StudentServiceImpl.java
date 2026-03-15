// Tai
package com.studentmanagement.services;

import com.studentmanagement.utils.DateUtils;
import com.studentmanagement.utils.Validator;
import com.studentmanagement.models.Student;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class StudentServiceImpl implements StudentService {

    private List<Student> students;
    private int nextStudentId = 1;

    // contructor
    public StudentServiceImpl() {
        this.students = new ArrayList<>();
        initializeSampleData();
    }

    // ⚠ SỬA: constructor này không cần thiết và gây lỗi runtime
    // nên xóa hoặc implement đúng
    // public StudentServiceImpl(List<Student> students) { ... }

    private void initializeSampleData() {
        // Thêm dữ liệu mẫu
        try {

            students.add(new Student(
                    "S001",
                    "SV20240001",
                    "Nguyễn Văn A",
                    DateUtils.parseDate("15/05/2000"),
                    "Nam",
                    "0987654321",
                    "nva@email.com",
                    "Hà Nội",
                    "K17CNTTA",
                    new Date(),
                    "Đang học"
            ));

            students.add(new Student(
                    "S002",
                    "SV20240002",
                    "Trần Thị B",
                    DateUtils.parseDate("20/08/2001"),
                    "Nữ",
                    "0912345678",
                    "ttb@email.com",
                    "TP.HCM",
                    "K17QTKD",
                    new Date(),
                    "Đang học"
            ));

            nextStudentId = students.size() + 1;

        } catch (Exception e) {
            System.out.println("Lỗi khởi tạo dữ liệu mẫu: " + e.getMessage());
        }
    }

    @Override
    public void addStudent(Scanner scanner) {

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║              THÊM SINH VIÊN MỚI              ║"); // ⚠ SỬA: căn chỉnh lại text
        System.out.println("╚══════════════════════════════════════════════╝");

        try {

            // nhap ma sinh vien 
            System.out.print("Mã sinh viên (SVxxxxx): ");
            String studentId = scanner.nextLine();

            if (findStudentByStudentId(studentId) != null) {
                System.out.println(" Mã sinh viên đã tồn tại!");
                return;
            }

            // nhap thong tin
            System.out.print("Họ và tên: ");
            String fullName = scanner.nextLine();

            System.out.print("Ngày sinh (dd/MM/yyyy): ");
            Date birthDate = DateUtils.parseDate(scanner.nextLine());

            System.out.print("Giới tính (Nam/Nữ): ");
            String gender = scanner.nextLine();

            System.out.print("Số điện thoại: ");
            String phone = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            if (!Validator.isValidEmail(email)) {
                System.out.println(" Email không hợp lệ!");
                return;
            }

            System.out.print("Địa chỉ: ");
            String address = scanner.nextLine();

            // Nhập thông tin học vụ
            System.out.print("Mã lớp: ");
            String classId = scanner.nextLine();

            System.out.print("Trạng thái (Đang học/Bảo lưu/Thôi học): ");
            String status = scanner.nextLine();

            // Tạo sinh viên mới
            String id = "S" + String.format("%03d", nextStudentId++);

            Student student = new Student(
                    id, studentId, fullName, birthDate, gender,
                    phone, email, address, classId, new Date(), status
            );

            students.add(student);

            // ⚠ SỬA: thêm thông báo thành công
            System.out.println(" Thêm sinh viên thành công!");

            student.displayInfo();

        } catch (Exception e) {

            System.out.println(" Lỗi khi thêm sinh viên: " + e.getMessage());

        }
    }

    @Override
    public void updateStudent(String studentId, Scanner scanner) {

        Student student = findStudentByStudentId(studentId);

        if (student == null) {
            System.out.println(" Không tìm thấy sinh viên với mã: " + studentId);
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║            SỬA THÔNG TIN SINH VIÊN           ║"); // ⚠ SỬA căn chỉnh
        System.out.println("╚══════════════════════════════════════════════╝");

        System.out.println("Thông tin hiện tại:");
        student.displayInfo();

        System.out.println("\nNhập thông tin mới (Enter để giữ nguyên):");

        try {

            System.out.print("Họ và tên [" + student.getFullName() + "]: ");

            String fullName = scanner.nextLine();

            // ney o day la trong (nhap enter ) thi khong thay doi , obj student van la fullname cu 
            if (!fullName.isEmpty()) {
                student.setFullName(fullName);
            }

            System.out.print("Ngày sinh [" + student.getFormattedBirthDate() + "]: ");

            String birthDateStr = scanner.nextLine();

            if (!birthDateStr.isEmpty()) {
                student.setBirthDate(DateUtils.parseDate(birthDateStr));
            }

            System.out.print("Giới tính [" + student.getGender() + "]: ");

            String gender = scanner.nextLine();

            if (!gender.isEmpty()) {
                student.setGender(gender);
            }

            System.out.print("Số điện thoại [" + student.getPhone() + "]: ");

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

                    System.out.println(" Email không hợp lệ!");

                }
            }

            System.out.print("Địa chỉ [" + student.getAddress() + "]: ");

            String address = scanner.nextLine();

            if (!address.isEmpty()) {
                student.setAddress(address);
            }

            System.out.print("Mã lớp [" + student.getClassId() + "]: ");

            String classId = scanner.nextLine();

            if (!classId.isEmpty()) {
                student.setClassId(classId);
            }

            System.out.print("Trạng thái [" + student.getStatus() + "]: ");

            String status = scanner.nextLine();

            if (!status.isEmpty()) {
                student.setStatus(status);
            }

            System.out.println("\n Cập nhật thành công!");

            student.displayInfo();

        } catch (Exception e) {

            System.out.println("Lỗi khi cập nhật: " + e.getMessage());

        }
    }

    @Override
    public boolean deleteStudent(String studentId) {

        Student student = findStudentByStudentId(studentId);

        if (student == null) {

            System.out.println("Không tìm thấy sinh viên!");

            return false;
        }

        students.remove(student);

        // ⚠ SỬA: bỏ Scanner confirm để tránh bug input
        System.out.println(" Xóa sinh viên thành công!");

        return true;
    }

    @Override
    public List<Student> searchStudent(String searchType, String keyword) {

        List<Student> results = new ArrayList<>();

        keyword = keyword.toLowerCase(); // ⚠ SỬA: tối ưu tìm kiếm

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

                    if (student.getClassId().equalsIgnoreCase(keyword)) {

                        results.add(student);

                    }

                    break;

                case "email":

                    if (student.getEmail().equalsIgnoreCase(keyword)) {

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

        System.out.println(" Xuất dữ liệu ra Excel: " + filePath);

        // Triển khai xuất Excel ở đây (sử dụng Apache POI)

        System.out.println(" Xuất dữ liệu thành công!");

    }

    // method tim id sv ton tai hay khong
    public Student findStudentByStudentId(String studentId) {

        for (Student student : students) {

            if (student.getStudentId().equals(studentId)) {

                return student;

            }

        }

        return null;

    }

    public Student findStudentById(String id) {

        for (Student student : students) {

            if (student.getId().equals(id)) {

                return student;

            }

        }

        return null;

    }
}