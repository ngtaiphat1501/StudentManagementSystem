package com.studentmanagement.utils;

import java.util.regex.Pattern;

/**
 * Utility class for input validation
 */
public class Validator {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^(0|\\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$");
    
    private static final Pattern STUDENT_ID_PATTERN = 
        Pattern.compile("^CE\\d{6}$");
    
    /**
     * Validates email format
     * @param email Email to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validates phone number format (Vietnamese)
     * @param phone Phone number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * Validates student ID format (CE followed by 6 digits)
     * @param studentId Student ID to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidStudentId(String studentId) {
        return studentId != null && STUDENT_ID_PATTERN.matcher(studentId).matches();
    }
    
    /**
     * Validates GPA value (0.0 - 4.0)
     * @param gpa GPA to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidGPA(double gpa) {
        return gpa >= 0.0 && gpa <= 4.0;
    }
    
    /**
     * Validates score value (0.0 - 10.0)
     * @param score Score to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidScore(double score) {
        return score >= 0.0 && score <= 10.0;
    }
    
    /**
     * Checks if a string is numeric
     * @param str String to check
     * @return true if numeric, false otherwise
     */
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Checks if a string is an integer
     * @param str String to check
     * @return true if integer, false otherwise
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}