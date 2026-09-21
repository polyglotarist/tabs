package com.guitarist.tabs.repository;

import com.guitarist.tabs.entity.ChordShape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChordShapeRepository extends JpaRepository<ChordShape, Long> {

    Optional<ChordShape> findByName(String name);
}