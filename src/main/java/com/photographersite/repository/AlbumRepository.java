package com.photographersite.repository;

import com.photographersite.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Optional<Album> findByTitle(String title);

    void deleteByTitle(String title);


}
