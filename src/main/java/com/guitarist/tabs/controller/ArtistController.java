package com.guitarist.tabs.controller;

import com.guitarist.tabs.entity.Artist;
import com.guitarist.tabs.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public List<Artist> getAll(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return artistService.search(search);
        }
        return artistService.findAll();
    }

    @GetMapping("/{id}")
    public Artist getById(@PathVariable Long id) {
        return artistService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Artist> create(@RequestBody Artist artist) {
        Artist created = artistService.create(artist);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public Artist update(@PathVariable Long id, @RequestBody Artist artist) {
        return artistService.update(id, artist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        artistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}