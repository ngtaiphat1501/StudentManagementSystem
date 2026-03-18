// Khanh
package com.studentmanagement.models;

import java.io.Serializable;

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
        return this.password.equals(password);
    }
    
    public boolean isAdmin(){
         return role.equalsIgnoreCase("admin");
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}