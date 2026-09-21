package com.guitarist.tabs.service;

import com.guitarist.tabs.entity.Artist;
import com.guitarist.tabs.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private static final Logger log = LoggerFactory.getLogger(ArtistService.class);

    private final ArtistRepository artistRepository;

    public List<Artist> findAll() {
        log.debug("Fetching all artists");
        return artistRepository.findAll();
    }

    public Artist findById(Long id) {
        log.debug("Fetching artist with id={}", id);
        return artistRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Artist not found with id={}", id);
                    return new RuntimeException("Artist not found with id: " + id);
                });
    }

    public Artist create(Artist artist) {
        Artist saved = artistRepository.save(artist);
        log.info("Created artist id={} name={}", saved.getId(), saved.getName());
        return saved;
    }

    public Artist update(Long id, Artist updatedArtist) {
        Artist existing = findById(id);
        existing.setName(updatedArtist.getName());
        existing.setBio(updatedArtist.getBio());
        Artist saved = artistRepository.save(existing);
        log.info("Updated artist id={}", saved.getId());
        return saved;
    }

    public void delete(Long id) {
        artistRepository.deleteById(id);
        log.info("Deleted artist id={}", id);
    }

    public List<Artist> search(String query) {
        log.debug("Searching artists with query='{}'", query);
        return artistRepository.findByNameContainingIgnoreCase(query);
    }
}