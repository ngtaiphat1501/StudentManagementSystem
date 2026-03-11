
package com.studentmanagement.managers;

import com.studentmanagement.models.User;
import com.studentmanagement.services.FileService;
import com.studentmanagement.services.FileServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class UserManager {
    
    private List<User> users;
    private User curentUser;
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
    
    public void saveUserToFile(){
        fileService.saveData("users.dat",users);
    }
    public boolean login(String username , String password){
        for(User user : users){
            
            if(user.getUsername().equals(username)&& user.getPassword().equals(password)){
                 curentUser= user;
                 System.out.println("Dang nhap thanh cong ! xin chao "+user.getFullName());
                 return true;
                 
            }      
         } 
        System.out.println("Sai thong tin dang nhap va mat khau ");
        return false;
   
        }
    
    public void logout(){
        if(curentUser != null){
            System.out.println(" da dang xuat tai khoan "+ curentUser.getFullName());
            curentUser = null;      
        }
    }
    public void changePassword(Scanner scanner){
        if(curentUser ==null){
            System.out.println("Chua dang nhap tai khoan");
            return ;
        }
        System.out.println("Nhap mat khau hien tai : ");
        String currentPass = scanner.nextLine();
        
        if(!curentUser.checkPassword(currentPass)){
            System.out.println("Mat khau hien tai khong dung ");
            return;        
        }
        System.out.println("Nhap mat khau moi ");
        String newPass= scanner.nextLine();
        
        System.out.println("xac nhan mat khau moi ");
        String confirmPass = scanner.nextLine();
        if(!newPass.equals(confirmPass)){
            System.out.println("Xac nhan mat khau moi khong dung ");
            return ;
            
        }
        curentUser.setPassword(newPass);
        saveUserToFile();
        System.out.println("Doi mat khau thanh cong");
        
    }
    // get va set 
    public User getCurentUser() {
        return curentUser;
    }
    public boolean isAdmin(){
        return curentUser !=null && curentUser.isAdmin();
    }
    public boolean isStudent(){
        return curentUser !=null && curentUser.isStudent();
    }
    public boolean isStaff(){
        return curentUser !=null && curentUser.isStaff();
    }
    public boolean isTeacher(){
        return curentUser != null && curentUser.isTeacher();
    }
    public List<User> getUsers() { return users; }    

    class User {
    private String username;
    private String password;
    private String role;
    private String fullName;
    private String email;
    
    public User(String username, String password, String role, String fullName, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.email = email;
    }
    
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
    
    public boolean isAdmin() { return "ADMIN".equals(role); }
    public boolean isStaff() { return "STAFF".equals(role); }
    public boolean isTeacher() { return "TEACHER".equals(role); }
    public boolean isStudent() { return "STUDENT".equals(role); }
    
    // Getter và Setter
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    
}
}
