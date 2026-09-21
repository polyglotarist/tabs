package com.guitarist.tabs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @Column(nullable = false)
    private String title;

    @Column(name = "original_key", nullable = false)
    private String originalKey;

    private Integer capo;

    private Integer tempo;

    private String difficulty;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String body;
}