package com.photographersite.service;

import com.photographersite.entity.Photo;
import com.photographersite.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record PhotoService(PhotoRepository repository) {
    public Optional<Photo> getPhotoById(Long id) {
        return repository.findById(id);
    }

    public List<Photo> getAllPhotos() {
        return repository.findAll();
    }
}
