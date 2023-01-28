package com.photographersite.service;

import com.photographersite.state.CreateAlbumState;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SaverService {
    private final Path root = Paths.get(System.getProperty("user.dir") + "/albums");

    public CreateAlbumState saveAlbum(MultipartFile cover, MultipartFile[] photos, String title) throws IOException {
        boolean isCoverSaved = false;
        boolean isPhotoSaved = false;
        Path albumPath = root.resolve(title);
        if (Files.exists(albumPath)) {
            throw new FileAlreadyExistsException(title + " album already exists");
        }
        Files.createDirectories(albumPath);
        if (cover != null && cover.getContentType().startsWith("image/")) {
            String coverName = title + "." + cover.getContentType().substring(6);
            Path coverPath = albumPath.resolve(coverName);
            Files.copy(cover.getInputStream(), coverPath);
            isCoverSaved = true;
        }

        if (photos != null) {
            for (MultipartFile file : photos) {
                if (file.getContentType().startsWith("image/")) {
                    Files.copy(file.getInputStream(), albumPath.resolve(file.getOriginalFilename()));
                    isPhotoSaved = true;
                }
            }
        }

        if (isCoverSaved && isPhotoSaved)
            return CreateAlbumState.CREATED;
        else if (isCoverSaved && !isPhotoSaved)
            return CreateAlbumState.CREATED_WITHOUT_PHOTOS;
        else if (!isCoverSaved && isPhotoSaved)
            return CreateAlbumState.CREATED_WITHOUT_COVER;
        else
            return CreateAlbumState.CREATED_EMPTY;
    }

}
