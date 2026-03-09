package com.studentmanagement;

import com.studentmanagement.managers.UserManager;
import com.studentmanagement.managers.StudentManager;
import com.studentmanagement.menus.MainMenu;
import com.studentmanagement.utils.ConsoleUtils;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        StudentManager studentManager = new StudentManager();
        
        // Tải dữ liệu từ file
        ConsoleUtils.showLoading("Đang tải dữ liệu hệ thống...");
        
        // Tạo tài khoản mặc định
        userManager.createDefaultAccounts();
        
        // Hiển thị menu chính
        MainMenu mainMenu = new MainMenu(scanner, userManager, studentManager);
        mainMenu.show();
        
        scanner.close();
        System.out.println("\n👋 Cảm ơn đã sử dụng hệ thống!");
    }
}