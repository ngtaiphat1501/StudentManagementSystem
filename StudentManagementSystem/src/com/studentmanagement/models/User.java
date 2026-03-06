//Khánh
package com.studentmanagement.models;

import java.io.Serializable;

/**
 *
 * @author TUF GAMING
 */
public class User implements Serializable  {
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
    
    public boolean checkPassword(String password){
        
        
        return this.password.equals(password); // xem đúng pass 
    }
    
    public boolean isAdmin(){
         return role.equalsIgnoreCase("admin"); // so sánh chọn quyền nào không quan tâm hoa hay thường
    }
    public boolean isStaff(){
         return role.equalsIgnoreCase("staff");
    }
    
    public boolean isTeacher(){
         return role.equalsIgnoreCase("teacher");
    }
    public boolean isStudent(){
          return role.equalsIgnoreCase("student");
    }
    
    
}
