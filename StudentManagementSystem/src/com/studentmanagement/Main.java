package com.studentmanagement;

import com.studentmanagement.menus.MainMenu;
import com.studentmanagement.utils.ConsoleUtils;

public class Main {
    public static void main(String[] args) {
        ConsoleUtils.showHeader("HỆ THỐNG QUẢN LÝ SINH VIÊN");
        System.out.println("Đang khởi động hệ thống...");
        
        try {
            // Hiển thị loading
            ConsoleUtils.showLoading("Khởi tạo dữ liệu");
            
            // Tạo đối tượng MainMenu và bắt đầu
            MainMenu mainMenu = new MainMenu();
            
            // Hiển thị thông báo thành công
            ConsoleUtils.showSuccess("Khởi động thành công!");
            ConsoleUtils.pressEnterToContinue(new java.util.Scanner(System.in));
            
            // Bắt đầu chương trình
            mainMenu.start();
            
        } catch (Exception e) {
            ConsoleUtils.showError("Lỗi khởi động hệ thống: " + e.getMessage());
            e.printStackTrace();
        }
    }
}