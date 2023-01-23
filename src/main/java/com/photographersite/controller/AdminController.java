package com.photographersite.controller;

import com.photographersite.entity.Album;
import com.photographersite.entity.Photo;
import com.photographersite.service.AlbumService;
import com.photographersite.util.UploadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;
import java.util.Set;

@Controller
public class AdminController {
    private final AlbumService albumService;

    public AdminController(AlbumService albumService) {
        this.albumService = albumService;
    }


    @GetMapping("/admin")
    public String uploadAlbum() {
        return "admin";
    }

    @PostMapping("/admin")
    public ResponseEntity<?> saveAlbum(@RequestAttribute final MultipartFile image, @RequestAttribute final MultipartFile zip
            , @RequestParam String title, @RequestParam String description) {
        String uploadDir = System.getProperty("user.dir") + "/albums";

        File albumDir = new File(uploadDir);
        if (!albumDir.exists())
            albumDir.mkdir();
        String albumPath = uploadDir + "/" + title;
        new File(albumPath).mkdir();
        Set<Photo> photos = UploadUtil.unzipFiles(zip, albumPath);
        String imageType = image.getOriginalFilename();
        imageType = imageType.substring(imageType.lastIndexOf("."));
        String coverPath = Paths.get(albumPath + "/" + title + imageType).toString();
        byte[] buffer = new byte[4 * 1024];
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(coverPath));
             BufferedInputStream bis = new BufferedInputStream(image.getInputStream())) {
            while (bis.read(buffer) != -1)
                bos.write(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Album album = new Album(title, coverPath, description, photos);
        photos.forEach((x) -> x.setAlbum(album));
        albumService.saveAlbum(album);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
