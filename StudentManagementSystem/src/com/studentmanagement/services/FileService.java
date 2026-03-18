package com.studentmanagement.services;

import java.io.*;

/**
 * Interface for file operations including save, load, backup, and restore
 */
public interface FileService {
    /**
     * Saves data to a file
     * @param fileName Name of the file
     * @param data Data object to save
     * @return true if successful, false otherwise
     */
    boolean saveData(String fileName, Object data);
    
    /**
     * Loads data from a file
     * @param fileName Name of the file
     * @return Loaded data object or null if not found
     */
    Object loadData(String fileName);
    
    /**
     * Creates a backup of all data files
     * @return true if successful, false otherwise
     */
    boolean backupData();
    
    /**
     * Restores data from a backup file
     * @param backupFile Name of the backup file
     * @return true if successful, false otherwise
     */
    boolean restoreData(String backupFile);
}