package com.photographersite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Photo  {
    public Photo() {
    }

    public Photo(String title, String pathToImage) {
        this.title = title;
        this.pathToImage = pathToImage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;
    private String title;
    @Column(name = "path_to_image", nullable = false)
    private String pathToImage;
    @ManyToOne
    @JoinColumn(name = "album_title")
    private Album album;
}
