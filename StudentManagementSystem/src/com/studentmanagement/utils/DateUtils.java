package com.studentmanagement.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat sdfFull = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    public static Date parseDate(String dateStr) throws ParseException {
        return sdf.parse(dateStr);
    }
    
    public static String formatDate(Date date) {
        return sdf.format(date);
    }
    
    public static String formatDateTime(Date date) {
        return sdfFull.format(date);
    }
    
    public static boolean isValidDate(String dateStr) {
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}