package com.studentmanagement.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Abstract base class for all persons in the system
 */
public abstract class Person implements Serializable {
    protected String id;
    protected String fullName;
    protected Date birthDate;
    protected String gender;
    protected String phone;
    protected String email;
    protected String address;
    
    /**
     * Constructor for Person
     * @param id System ID
     * @param fullName Full name
     * @param birthDate Birth date
     * @param gender Gender
     * @param phone Phone number
     * @param email Email address
     * @param address Physical address
     */
    public Person(String id, String fullName, Date birthDate, String gender, 
                  String phone, String email, String address) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }
    
    /**
     * Abstract method to display person information
     */
    public abstract void displayInfo();
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    /**
     * Gets formatted birth date string
     * @return Birth date in dd/MM/yyyy format
     */
    public String getFormattedBirthDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(birthDate);
    }
}