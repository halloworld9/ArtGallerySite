package com.photographersite.controller;

import com.photographersite.entity.Album;
import com.photographersite.entity.Photo;
import com.photographersite.service.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Controller
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping(value = "/albums/{title}")
    public String getAlbum(@PathVariable String title, Model page) {
        Optional<Album> albumOptional = albumService.getAlbumByTitle(title);
        if (albumOptional.isEmpty())
            return "emptyAlbum";
        Album album = albumOptional.get();
        Set<Photo> photos = album.getPhotos() != null? album.getPhotos() : new HashSet<>();
        page.addAttribute("photos", photos);
        return "album";
    }

    @GetMapping("/albums/") //redirect from empty path variables without, it will be error 404 without it
    public String redirect() {
        return "forward:/albums";
    }

    @GetMapping("/albums")
    public String getAlbums(Model page) {
        var albums = albumService.getAlbums();
        albums.removeIf((x) -> !x.isVisible());
        page.addAttribute("albums", albums);
        return "albums";
    }


}
