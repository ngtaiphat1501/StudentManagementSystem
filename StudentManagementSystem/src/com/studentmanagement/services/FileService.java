// Tai
package com.studentmanagement.services;

import java.io.*;
import java.util.List;

public interface FileService {
    // 5.1. Lưu dữ liệu
    boolean saveData(String fileName, Object data);
    
    // 5.2. Đọc dữ liệu
    Object loadData(String fileName);
    
    // 5.3. Xuất Excel
    boolean exportToExcel(Object data, String filePath);
    
    // 5.4. Sao lưu dữ liệu
    boolean backupData();
    
    // Khôi phục dữ liệu
    boolean restoreData(String backupFile);
}