package com.guitarist.tabs.repository;

import com.guitarist.tabs.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByName(String name);

    List<Artist> findByNameContainingIgnoreCase(String name);
}