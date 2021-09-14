package com.SugarP1g.unzip;

import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Demo {

    static final int BUFFER = 512;
    static final long MAX_SIZE_OF_FILE = 0x640000; // Max size of unzipped data, 10MB
    static final int MAX_NUMBER_OF_FILE = 512;      // Max number of files

    private static void createDirIfNotExist(File file) {
        if (!file.exists()) {
            if (new File(file.getParent()).mkdirs()) {
                System.out.println("create dir:{}" + file.getParent());
            }
        }
    }

    private static String validateFilename(String filename, String intendedDir) throws Exception {
        if (filename.startsWith(intendedDir)) {
            return filename;
        } else {
            throw new Exception("File is outside extraction target directory.");
        }
    }

    private static void validateZipFile(int entries, long total) throws Exception {
        if (entries > MAX_NUMBER_OF_FILE) {
            throw new Exception("Too many files to unzip. more than 512");
        }
        if (total + BUFFER > MAX_SIZE_OF_FILE) {
            throw new Exception("File being unzipped is too big. more than 10MB");
        }
    }

    private static void writeFile(ZipInputStream zis, String destDirPath) throws Exception {
        ZipEntry entry;
        File file = null;
        int entries = 0;
        long total = 0;
        while ((entry = zis.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                file = new File(destDirPath, entry.getName());
                createDirIfNotExist(file);
                String name = validateFilename(file.getCanonicalPath(), destDirPath);
                entries++;
                int count = 0;
                byte[] data = new byte[BUFFER];
                try (FileOutputStream fos = FileUtils.openOutputStream(file); BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER)) {
                    while (total + BUFFER <= MAX_SIZE_OF_FILE && (count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                        total += count;
                    }
                    validateZipFile(entries, total);
                }
            }
        }
    }

    public static void unZipFile(File filename, String destDirPath) throws Exception {
        try (FileInputStream fis = FileUtils.openInputStream(filename); ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis))) {
            writeFile(zis, destDirPath);
        }
    }

    public String getUnzipFilePath(String file, String path) throws Exception {

        File zipFile = new File(path + file);
        String destDirPath = path + zipFile.getName().substring(0, zipFile.getName().lastIndexOf("."));
        File unZipDir = new File(destDirPath);
        if (!unZipDir.mkdirs()) {
            System.out.println("create unZipDir:{} failed" + unZipDir.getPath());
        }
        //此处将网络数据写入文件
        // file.transferTo(zipFile);
        unZipFile(zipFile, destDirPath);
        return destDirPath;
    }

    public static void main(String[] args) throws Exception {
        Demo demo = new Demo();
        String path = "D:\\\\tmp\\";
        demo.getUnzipFilePath("..\\test", path);
    }
}
