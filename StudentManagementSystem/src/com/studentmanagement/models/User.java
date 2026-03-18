package com.studentmanagement.models;

import java.io.Serializable;

/**
 * Represents a system user account with authentication and role information
 */
public class User implements Serializable  {
    private String username;
    private String password;
    private String role;
    private String fullName;
    private String email;

    /**
     * Constructor for User
     * @param username Username for login
     * @param password Password
     * @param role User role (ADMIN/STAFF/TEACHER/STUDENT)
     * @param fullName Full name
     * @param email Email address
     */
    public User(String username, String password, String role, String fullName, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.email = email;
    }
    
    /**
     * Checks if password matches
     * @param password Password to check
     * @return true if matches, false otherwise
     */
    public boolean checkPassword(String password){
        return this.password.equals(password);
    }
    
    /**
     * Checks if user is admin
     * @return true if admin, false otherwise
     */
    public boolean isAdmin(){
         return role.equalsIgnoreCase("admin");
    }
    
    /**
     * Checks if user is staff
     * @return true if staff, false otherwise
     */
    public boolean isStaff(){
         return role.equalsIgnoreCase("staff");
    }
    
    /**
     * Checks if user is teacher
     * @return true if teacher, false otherwise
     */
    public boolean isTeacher(){
         return role.equalsIgnoreCase("teacher");
    }
    
    /**
     * Checks if user is student
     * @return true if student, false otherwise
     */
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