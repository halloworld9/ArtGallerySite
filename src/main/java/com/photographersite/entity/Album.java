package com.photographersite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
public class Album {
    @Id
    @Column(name = "album_title")
    private String title;
    @Column(name = "cover_path")
    private String coverPath;
    private String description;
    @OneToMany(mappedBy = "album", cascade = {CascadeType.ALL})
    private Set<Photo> photos;

    public Album(String title, String coverPath, String description, Set<Photo> photos) {
        this.title = title;
        this.coverPath = coverPath;
        this.description = description;
        this.photos = photos;
    }

    public Album() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Album album = (Album) o;
        return title != null && Objects.equals(title, album.title);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "title = " + title + ", " +
                "coverPath = " + coverPath + ", " +
                "description = " + description + ")";
    }
}
