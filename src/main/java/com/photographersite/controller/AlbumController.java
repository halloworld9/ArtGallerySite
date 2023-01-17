package com.photographersite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AlbumController {
    @GetMapping(value = "/albums/{title}")
    public String getAlbum(@PathVariable String title, Model page) {
        return "album";
    }

    @GetMapping("/albums/") //redirect from empty path variables without, it will be error 404 without it
    public String redirect() {
        return "forward:/albums";
    }

    @GetMapping("/albums")
    public String getAlbums(Model page) {

        return "albums";
    }

}
