package com.photographersite.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;


@Entity
@Getter
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String pathToImage;
    private String description;
    @OneToMany
    private List<Photo> photos;
}
