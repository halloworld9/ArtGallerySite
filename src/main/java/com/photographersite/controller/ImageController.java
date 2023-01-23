package com.photographersite.controller;

import com.photographersite.entity.Album;
import com.photographersite.entity.Photo;
import com.photographersite.service.AlbumService;
import com.photographersite.service.PhotoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

@Controller
public class ImageController {
    private final AlbumService albumService;
    private final PhotoService photoService;

    public ImageController(AlbumService albumService, PhotoService photoService) {
        this.albumService = albumService;
        this.photoService = photoService;
    }

    @GetMapping("/cover/{title}")
    public void getCover(@PathVariable String title, HttpServletResponse response) {
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        Optional<Album> album = albumService.getAlbumById(title);
        if (album.isEmpty()) return;
        try (OutputStream stream = response.getOutputStream();
             FileInputStream inputStream = new FileInputStream(album.get().getCoverPath())) {
            byte[] buffer = new byte[4 * 1024];
            while (inputStream.read(buffer) != -1)
                stream.write(buffer);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/image/{id}")
    public void getPhoto(@PathVariable Long id, HttpServletResponse response) {
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        Optional<Photo> opt = photoService.getPhotoById(id);
        if (opt.isEmpty()) return;
        Photo photo = opt.get();
        try (OutputStream stream = response.getOutputStream();
             FileInputStream inputStream = new FileInputStream(photo.getPathToImage())) {
            byte[] buffer = new byte[4 * 1024];
            while (inputStream.read(buffer) != -1)
                stream.write(buffer);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

    }
}
