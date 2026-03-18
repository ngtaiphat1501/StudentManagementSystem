// Tai
package com.studentmanagement.services;

import java.io.*;

public interface FileService {
    boolean saveData(String fileName, Object data);
    Object loadData(String fileName);
    boolean backupData();
    boolean restoreData(String backupFile);
}