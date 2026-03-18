package com.studentmanagement;

import com.studentmanagement.menus.MainMenu;
import com.studentmanagement.utils.ConsoleUtils;

public class Main {
    public static void main(String[] args) {
        ConsoleUtils.showHeader("STUDENT MANAGEMENT SYSTEM");
        System.out.println("Starting system...");
        
        try {
            ConsoleUtils.showLoading("Initializing data");
            
            MainMenu mainMenu = new MainMenu();
            
            ConsoleUtils.showSuccess("System started successfully!");
            ConsoleUtils.pressEnterToContinue(new java.util.Scanner(System.in));
            
            mainMenu.start();
            
        } catch (Exception e) {
            ConsoleUtils.showError("System startup error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}