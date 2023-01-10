package com.photographersite.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.List;


@Entity
@Getter
public class Album {
    @Id
    private Long id;
    private String title;
    @Lob
    private byte[] previewImage;
    private String description;
    @OneToMany
    private List<Photo> photos;
}
