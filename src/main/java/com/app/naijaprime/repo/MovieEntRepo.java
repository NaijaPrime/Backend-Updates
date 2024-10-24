package com.app.naijaprime.repo;

import com.app.naijaprime.entity.ContentCreator;
import com.app.naijaprime.entity.MovieEnt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieEntRepo extends JpaRepository<MovieEnt, Long> {
    Optional<MovieEnt> findByTitleAndContentCreator(String title, ContentCreator contentCreator);

}
