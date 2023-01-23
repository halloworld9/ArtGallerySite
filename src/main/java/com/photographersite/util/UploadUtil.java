package com.photographersite.util;


import com.photographersite.entity.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UploadUtil {
    public static boolean isImage(String name) {
        name = name.toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
    }

    private static void saveFileFromZip(String path, ZipInputStream zin) {
        try (BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(path))) {
            byte[] buf = new byte[1024 * 4];
            while (zin.read(buf) > 0)
                fos.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<Photo> unzipFiles(MultipartFile zip, String path) {
        Set<Photo> photos = new HashSet<>();
        if (zip == null)
            return photos;
        try (ZipInputStream zin = new ZipInputStream(zip.getInputStream())) {
            ZipEntry entry;
            while((entry = zin.getNextEntry()) != null) {
                String fileName = entry.getName();
                String photoPath = path + "/" + fileName;
                if (isImage(fileName)) {
                    saveFileFromZip(photoPath, zin);
                    Photo photo = new Photo(fileName.substring(0, fileName.lastIndexOf(".")), photoPath);
                    photos.add(photo);
                }
            }
            return photos;
        } catch(IOException e) {
            return photos;
        }
    }

}

