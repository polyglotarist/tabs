package com.guitarist.tabs.service;

import com.guitarist.tabs.entity.Song;
import com.guitarist.tabs.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

    private static final Logger log = LoggerFactory.getLogger(SongService.class);

    private final SongRepository songRepository;

    public List<Song> findAll() {
        log.debug("Fetching all songs");
        return songRepository.findAll();
    }

    public Song findById(Long id) {
        log.debug("Fetching song with id={}", id);
        return songRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Song not found with id={}", id);
                    return new RuntimeException("Song not found with id: " + id);
                });
    }

    public Song create(Song song) {
        Song saved = songRepository.save(song);
        log.info("Created song id={} title='{}' artistId={}",
                saved.getId(), saved.getTitle(), saved.getArtist().getId());
        return saved;
    }

    public Song update(Long id, Song updatedSong) {
        Song existing = findById(id);
        existing.setTitle(updatedSong.getTitle());
        existing.setArtist(updatedSong.getArtist());
        existing.setOriginalKey(updatedSong.getOriginalKey());
        existing.setCapo(updatedSong.getCapo());
        existing.setTempo(updatedSong.getTempo());
        existing.setDifficulty(updatedSong.getDifficulty());
        existing.setBody(updatedSong.getBody());
        Song saved = songRepository.save(existing);
        log.info("Updated song id={} title='{}'", saved.getId(), saved.getTitle());
        return saved;
    }

    public void delete(Long id) {
        songRepository.deleteById(id);
        log.info("Deleted song id={}", id);
    }

    public List<Song> searchByTitle(String title) {
        log.debug("Searching songs by title='{}'", title);
        return songRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Song> searchByArtist(String artistName) {
        log.debug("Searching songs by artist name='{}'", artistName);
        return songRepository.findByArtist_NameContainingIgnoreCase(artistName);
    }
}