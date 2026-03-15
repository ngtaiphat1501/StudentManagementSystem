package com.studentmanagement.managers;

import com.studentmanagement.models.User;
import com.studentmanagement.services.FileService;
import com.studentmanagement.services.FileServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    
    private List<User> users;
    private User currentUser; // ⚠ SỬA: curentUser -> currentUser (sai chính tả)
    private FileService fileService;

    public UserManager() {
        this.users = new ArrayList<>();
        this.fileService = new FileServiceImpl();
        loadUsersFromFile();
        
        if (users.isEmpty()) {
            createDefaultAccounts();
        }
    }

    public void loadUsersFromFile(){
        Object data = fileService.loadData("users.dat");
        if(data instanceof List){
            users = (List<User>) data;
            System.out.println("Dang tai "+users.size()+" Nguoi dung tu file");
        }
    }
    
    public void createDefaultAccounts(){
        // tao 4 tk co san 
        users.add(new User("admin","admin123","ADMIN","Nguyen Phat Tai","tainguyen@gmail.com"));
        users.add(new User("staff", "staff123", "STAFF", "Giang Quoc Khanh", "staff@university.edu.vn"));
        users.add(new User("teacher", "teacher123", "TEACHER", "Lê Văn Teacher", "teacher@university.edu.vn"));
        users.add(new User("student", "student123", "STUDENT", "Phạm Văn Student", "student@university.edu.vn"));
        saveUserToFile();
        System.out.println(" Đã tạo tài khoản mặc định");
    }

    // luu file
    public void saveUserToFile(){
        fileService.saveData("users.dat",users);
    }

    public boolean login(String username , String password){
        for(User user : users){
            
            // ⚠ SỬA: user.getPassword().equals(password)
            // -> dùng user.checkPassword(password) để đúng OOP (Encapsulation)
            if(user.getUsername().equals(username) && user.checkPassword(password)){
                 currentUser = user; // ⚠ SỬA: curentUser -> currentUser
                 System.out.println("Dang nhap thanh cong ! xin chao "+user.getFullName());
                 return true;
                 
            }      
         } 
        System.out.println("Sai thong tin dang nhap va mat khau ");
        return false;
   
    }
    
    public void logout(){
        if(currentUser != null){ // ⚠ SỬA: curentUser -> currentUser
            System.out.println(" da dang xuat tai khoan "+ currentUser.getFullName());
            currentUser = null;      
        }
    }

    public void changePassword(Scanner scanner){
        if(currentUser == null){ // ⚠ SỬA: curentUser -> currentUser
            System.out.println("Chua dang nhap tai khoan");
            return ;
        }

        System.out.println("Nhap mat khau hien tai : ");
        String currentPass = scanner.nextLine();
        
        if(!currentUser.checkPassword(currentPass)){ // ⚠ SỬA: curentUser -> currentUser
            System.out.println("Mat khau hien tai khong dung ");
            return;        
        }

        System.out.println("Nhap mat khau moi ");
        String newPass = scanner.nextLine();
        
        System.out.println("xac nhan mat khau moi ");
        String confirmPass = scanner.nextLine();

        if(!newPass.equals(confirmPass)){
            System.out.println("Xac nhan mat khau moi khong dung ");
            return ;
        }

        currentUser.setPassword(newPass); // ⚠ SỬA: curentUser -> currentUser
        saveUserToFile();
        System.out.println("Doi mat khau thanh cong");
        
    }

    // get va set 
    public User getCurrentUser() { // ⚠ SỬA: getCurentUser -> getCurrentUser
        return currentUser;
    }

    public boolean isAdmin(){
        return currentUser != null && currentUser.isAdmin(); // ⚠ SỬA: curentUser -> currentUser
    }

    public boolean isStudent(){
        return currentUser != null && currentUser.isStudent(); // ⚠ SỬA: curentUser -> currentUser
    }

    public boolean isStaff(){
        return currentUser != null && currentUser.isStaff(); // ⚠ SỬA: curentUser -> currentUser
    }

    public boolean isTeacher(){
        return currentUser != null && currentUser.isTeacher(); // ⚠ SỬA: curentUser -> currentUser
    }

    public List<User> getUsers() { 
        return users; 
    }    
}