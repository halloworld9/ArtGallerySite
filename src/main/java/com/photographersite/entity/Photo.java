package com.photographersite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Photo {
    public Photo() {

    }
    public Photo(String title, String pathToImage, Album album) {
        this.title = title;
        this.pathToImage = pathToImage;
        this.album = album;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;
    private String title;
    @Column(name = "path_to_image", nullable = false)
    private String pathToImage;
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        if (!id.equals(photo.id)) return false;
        if (!title.equals(photo.title)) return false;
        if (!pathToImage.equals(photo.pathToImage)) return false;
        return album.getTitle().equals(photo.album.getTitle());
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + title.hashCode();
        result = 31 * result + pathToImage.hashCode();
        result = 31 * result + album.getTitle().hashCode();
        return result;
    }
}
