package com.photographersite.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;


@Entity
@Getter
public class Album {
    @Id
    @Column(name = "album_title")
    private String title;
    @Column(name = "cover_path")
    private String coverPath;
    private String description;
    @OneToMany(mappedBy = "album", cascade = {CascadeType.ALL})
    private Set<Photo> photos;
    private boolean visible;

    public Album setTitle(String title) {
        this.title = title;
        return this;
    }

    public Album setCoverPath(String coverPath) {
        this.coverPath = coverPath;
        return this;
    }

    public Album setDescription(String description) {
        this.description = description;
        return this;
    }

    public Album setPhotos(Set<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public Album setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public Album(String title, String coverPath, String description, Set<Photo> photos, boolean visible) {
        this.title = title;
        this.coverPath = coverPath;
        this.description = description;
        this.photos = photos;
        this.visible = visible;
    }

    public Album() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (!title.equals(album.title)) return false;
        if (!coverPath.equals(album.coverPath)) return false;
        if (!description.equals(album.description)) return false;
        return photos.equals(album.photos);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + coverPath.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (photos != null ? photos.hashCode(): 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "title = " + title + ", " +
                "coverPath = " + coverPath + ", " +
                "description = " + description + ")";
    }
}
