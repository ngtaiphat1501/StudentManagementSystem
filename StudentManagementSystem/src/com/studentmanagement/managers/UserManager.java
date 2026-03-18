// Khanh
package com.studentmanagement.managers;

import com.studentmanagement.models.User;
import com.studentmanagement.services.FileService;
import com.studentmanagement.services.FileServiceImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class UserManager {

    private List<User> users;
    private User currentUser;
    private FileService fileService;

    public UserManager() {
        this.users = new ArrayList<>();
        this.fileService = new FileServiceImpl();
        loadUsersFromFile();

        if (users.isEmpty()) {
            createDefaultAccounts();
        }
    }

    public void loadUsersFromFile() {
        Object data = fileService.loadData("users.dat");
        if (data instanceof List) {
            users = (List<User>) data;
            
            List<User> uniqueUsers = new ArrayList<>();
            HashSet<String> usernames = new HashSet<>();
            
            for (User user : users) {
                if (!usernames.contains(user.getUsername())) {
                    usernames.add(user.getUsername());
                    uniqueUsers.add(user);
                }
            }
            
            if (uniqueUsers.size() < users.size()) {
                System.out.println("⚠️ Detected duplicate users, auto fixing...");
                users = uniqueUsers;
                saveUserToFile();
            }
            
            System.out.println("📥 Loaded " + users.size() + " users from file");
        }

        if (users.isEmpty() && !new File("data/users.dat").exists()) {
            createDefaultAccounts();
        }
    }

    public void createDefaultAccounts() {
        boolean hasAdmin = false, hasStaff = false, hasTeacher = false, hasStudent = false;
        
        for (User user : users) {
            if (user.getUsername().equals("admin")) hasAdmin = true;
            if (user.getUsername().equals("staff")) hasStaff = true;
            if (user.getUsername().equals("teacher")) hasTeacher = true;
            if (user.getUsername().equals("student")) hasStudent = true;
        }
        
        if (!hasAdmin) users.add(new User("admin","admin123","ADMIN","Nguyen Phat Tai","tainguyen@gmail.com"));
        if (!hasStaff) users.add(new User("staff", "staff123", "STAFF", "Giang Quoc Khanh", "staff@university.edu.vn"));
        if (!hasTeacher) users.add(new User("teacher", "teacher123", "TEACHER", "Le Van Teacher", "teacher@university.edu.vn"));
        if (!hasStudent) users.add(new User("student", "student123", "STUDENT", "Pham Van Student", "student@university.edu.vn"));
        
        if (!users.isEmpty()) {
            saveUserToFile();
            System.out.println("✅ Default accounts created/loaded");
        }
    }

    public void saveUserToFile() {
        fileService.saveData("users.dat", users);
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                currentUser = user;
                System.out.println("✅ Login successful! Welcome " + user.getFullName());
                return true;
            }
        }
        System.out.println("❌ Invalid username or password");
        return false;
    }
    
    public void logout() {
        if (currentUser != null) {
            System.out.println("👋 Logged out user " + currentUser.getFullName());
            currentUser = null;
        }
    }

    public void changePassword(Scanner scanner) {
        if (currentUser == null) {
            System.out.println("❌ Not logged in");
            return;
        }

        System.out.print("Enter current password: ");
        String currentPass = scanner.nextLine();

        if (!currentUser.checkPassword(currentPass)) {
            System.out.println("❌ Current password is incorrect");
            return;
        }

        System.out.print("Enter new password: ");
        String newPass = scanner.nextLine();

        System.out.print("Confirm new password: ");
        String confirmPass = scanner.nextLine();

        if (!newPass.equals(confirmPass)) {
            System.out.println("❌ Password confirmation does not match");
            return;
        }

        currentUser.setPassword(newPass);
        saveUserToFile();
        System.out.println("✅ Password changed successfully");
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }

    public boolean isStudent() {
        return currentUser != null && currentUser.isStudent();
    }

    public boolean isStaff() {
        return currentUser != null && currentUser.isStaff();
    }

    public boolean isTeacher() {
        return currentUser != null && currentUser.isTeacher();
    }

    public List<User> getUsers() {
        return users;
    }
}