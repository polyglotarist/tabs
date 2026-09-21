package com.guitarist.tabs.controller;

import com.guitarist.tabs.entity.Song;
import com.guitarist.tabs.service.SongService;
import com.guitarist.tabs.service.TransposeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;
    private final TransposeService transposeService;

    @GetMapping
    public List<Song> getAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String artist) {
        if (title != null && !title.isBlank()) {
            return songService.searchByTitle(title);
        }
        if (artist != null && !artist.isBlank()) {
            return songService.searchByArtist(artist);
        }
        return songService.findAll();
    }

    @GetMapping("/{id}")
    public Song getById(@PathVariable Long id) {
        return songService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Song> create(@RequestBody Song song) {
        Song created = songService.create(song);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public Song update(@PathVariable Long id, @RequestBody Song song) {
        return songService.update(id, song);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        songService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/transpose")
    public Map<String, Object> transpose(
            @PathVariable Long id,
            @RequestParam int semitones,
            @RequestParam(defaultValue = "false") boolean useFlats) {
        Song song = songService.findById(id);
        String transposedBody = transposeService.transposeBody(song.getBody(), semitones, useFlats);

        Map<String, Object> response = new HashMap<>();
        response.put("songId", song.getId());
        response.put("title", song.getTitle());
        response.put("originalKey", song.getOriginalKey());
        response.put("semitones", semitones);
        response.put("transposedBody", transposedBody);
        return response;
    }
}