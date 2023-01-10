package com.photographersite.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;

@Entity
@Getter
public class Photo {
    @Id
    private Long id;
    private String title;

    @Lob
    private byte[] image;
}
