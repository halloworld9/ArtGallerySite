package com.photographersite.service;

import com.photographersite.entity.Album;
import com.photographersite.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> getAlbums() {
        return albumRepository.findAll();
    }

    public Optional<Album> getAlbumById(Long id) {
        return albumRepository.findById(id);
    }

    public void saveAlbum(Album album) {
        albumRepository.save(album);
    }

    public Optional<Album> getAlbumByTitle(String title) {
        return albumRepository.findByTitle(title);
    }

    public void deleteAlbum(String title) {
        albumRepository.deleteByTitle(title);
    }

}
