package com.studentmanagement.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for date operations
 */
public class DateUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat sdfFull = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    /**
     * Parses a date string to Date object
     * @param dateStr Date string in dd/MM/yyyy format
     * @return Date object
     * @throws ParseException if parsing fails
     */
    public static Date parseDate(String dateStr) throws ParseException {
        return sdf.parse(dateStr);
    }
    
    /**
     * Formats a Date object to string
     * @param date Date to format
     * @return Formatted date string in dd/MM/yyyy format
     */
    public static String formatDate(Date date) {
        return sdf.format(date);
    }
    
    /**
     * Formats a Date object to date-time string
     * @param date Date to format
     * @return Formatted date-time string in dd/MM/yyyy HH:mm:ss format
     */
    public static String formatDateTime(Date date) {
        return sdfFull.format(date);
    }
    
    /**
     * Validates if a string is a valid date
     * @param dateStr Date string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDate(String dateStr) {
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}