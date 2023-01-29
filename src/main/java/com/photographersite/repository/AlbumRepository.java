package com.photographersite.repository;

import com.photographersite.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    @Query("SELECT a " +
            "FROM Album a " +
            "WHERE a.title = :title")
    Optional<Album> findByTitle(@Param("title") String title);

    @Query("DELETE FROM Album a " +
            "WHERE a.title = :title")
    void deleteAlbumsByTitle(@Param("title") String title);
}
