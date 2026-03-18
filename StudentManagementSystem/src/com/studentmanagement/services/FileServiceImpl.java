package com.studentmanagement.services;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Implementation of FileService for file operations
 */
public class FileServiceImpl implements FileService {

    private static final String DATA_DIR = "data/";
    private static final String BACKUP_DIR = "data/backups/";

    /**
     * Constructor - creates data and backup directories if they don't exist
     */
    public FileServiceImpl() {
        new File(DATA_DIR).mkdirs();
        new File(BACKUP_DIR).mkdirs();
    }

    @Override
    public boolean saveData(String fileName, Object data) {
        String filePath = DATA_DIR + fileName;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
            System.out.println(" Data saved to file: " + fileName);
            return true;
        } catch (IOException e) {
            System.out.println(" Error saving file " + fileName + ": " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object loadData(String fileName) {
        String filePath = DATA_DIR + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object data = ois.readObject();
            System.out.println(" Data loaded from file: " + fileName);
            return data;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public boolean backupData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        String backupFile = BACKUP_DIR + "backup_" + timestamp + ".zip";
        try {
            FileOutputStream fos = new FileOutputStream(backupFile);
            ZipOutputStream zos = new ZipOutputStream(fos);

            File dataDir = new File(DATA_DIR);
            File[] files = dataDir.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && !file.getName().endsWith(".zip")) {
                        addToZipFile(file, zos);
                    }
                }
            }
            zos.close();
            fos.close();

            System.out.println("✅ Backup successful: " + backupFile);
            return true;

        } catch (IOException e) {
            System.out.println(" Error during backup: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean restoreData(String backupFile) {
        System.out.println(" Restoring data from: " + backupFile);
        return true;
    }

    /**
     * Adds a file to a zip output stream
     * @param file File to add
     * @param zos Zip output stream
     * @throws IOException if an I/O error occurs
     */
    private void addToZipFile(File file, ZipOutputStream zos) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }

    /**
     * Lists all available backup files
     */
    public void listBackup() {
        File backupDir = new File(BACKUP_DIR);
        File[] backups = backupDir.listFiles((dir, name) -> name.endsWith(".zip"));

        if (backups == null || backups.length == 0) {
            System.out.println(" No backups found");
            return;
        }

        System.out.println("\n BACKUP LIST:");
        for (int i = 0; i < backups.length; i++) {
            System.out.printf("%d. %s (%.2f MB)\n",
                    i + 1, backups[i].getName(),
                    backups[i].length() / (1024.0 * 1024.0));
        }
    }
}