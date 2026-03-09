// Tai
package com.studentmanagement.services;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileServiceImpl implements FileService{

    private static final String DATA_DIR = "data/";
    private static final String BACKUP_DIR = "data/backups/";
    
     public FileServiceImpl() {
        // Tạo thư mục nếu chưa tồn tại
        new File(DATA_DIR).mkdirs();
        new File(BACKUP_DIR).mkdirs();
    }
    
  @Override
    public boolean saveData(String fileName, Object data) {
        String filePath = DATA_DIR + fileName;
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
            System.out.println(" Đã lưu dữ liệu vào file: " + filePath);
            return true;
        } catch (IOException e) {
            System.out.println(" Lỗi khi lưu file " + fileName + ": " + e.getMessage());
            return false;
        }
 }

    @Override
    public Object loadData(String fileName) {
        String filePath = DATA_DIR + fileName;
        File file = new File(filePath);
        
        if (!file.exists()) {
            System.out.println(" File " + fileName + " chưa tồn tại");
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object data = ois.readObject();
            System.out.println(" Đã tải dữ liệu từ file: " + fileName);
            return data;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" Lỗi khi đọc file " + fileName + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean exportToExcel(Object data, String filePath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean backupData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean restoreData(String backupFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
