package com.photographersite.controller;

import com.photographersite.entity.Album;
import com.photographersite.entity.Photo;
import com.photographersite.service.AlbumService;
import com.photographersite.service.SaverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {
    private final AlbumService albumService;
    private final SaverService saverService;

    public AdminController(AlbumService albumService, SaverService saverService) {
        this.albumService = albumService;
        this.saverService = saverService;
    }


    @GetMapping("/admin")
    public String uploadAlbum() {
        return "admin";
    }

    @DeleteMapping("/admin")
    public String deleteAlbum(@RequestParam String title) {
        albumService.deleteAlbum(title);
        return "admin";
    }

    @PutMapping("/admin")
    public ResponseEntity<?> saveAlbum(@RequestAttribute final MultipartFile cover, @RequestAttribute final MultipartFile[] photos
            , @RequestParam String title, @RequestParam String description, @RequestParam Boolean visible) {
        try {
            var createAlbumState = saverService.saveAlbum(cover, photos, title);
            String coverPath = "/images/universal cover.png";
            Set<Photo> photosSet = new HashSet<>();
            Album album = new Album().setTitle(title); //set title for hashCode in photo
            switch (createAlbumState) {
                case CREATED -> {
                    coverPath = "/photos/" + title + "/" + title + "." + cover.getContentType().substring(6);
                    fillPhotosSet(photos, title, photosSet, album);
                }
                case CREATED_WITHOUT_COVER -> fillPhotosSet(photos, title, photosSet, album);
            }
            album.setDescription(description)
                    .setCoverPath(coverPath)
                    .setPhotos(photosSet)
                    .setVisible(visible);
            albumService.saveAlbum(album);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void fillPhotosSet(MultipartFile[] photos, String title, Set<Photo> photosSet, Album album) {
        for (MultipartFile photo : photos) {
            String photoTitle = photo.getOriginalFilename();
            photoTitle = photoTitle.substring(0, photoTitle.lastIndexOf('.'));
            String path = "/photos/" + title + "/" + photo.getOriginalFilename();
            photosSet.add(new Photo(photoTitle, path, album));
        }
    }


}
