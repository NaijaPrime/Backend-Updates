package com.app.naijaprime.controller;

import com.app.naijaprime.dto.MovieDto;
import com.app.naijaprime.entity.MovieEnt;
import com.app.naijaprime.service.MovieService;
import com.app.naijaprime.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {
    @Autowired
    private MovieService movieService;

    private static final String VIDEO_PATH = "/path/to/movies/";

    @GetMapping("/trailer/{movieId}")
    public ResponseEntity<MovieDto> getMovieTrailer(@PathVariable Long movieId) {
        return movieService.getMovieTrailer(movieId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{movieId}/full")
    public ResponseEntity<byte[]> streamFullMovieWithStaticPath(@PathVariable Long movieId, @RequestHeader HttpHeaders headers, @RequestParam Long userId) throws IOException, IOException {
        MovieDto movieDto = movieService.getFullMovie(movieId, userId);

        File video = new File(VIDEO_PATH + movieDto.getFullVideoUrl());
        if (!video.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpRange range = headers.getRange().stream().findFirst().orElse(null);
        if (range != null) {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .header(HttpHeaders.CONTENT_RANGE, range.toString())
                    .body(Files.readAllBytes(video.toPath()));
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .body(Files.readAllBytes(video.toPath()));
    }

    @GetMapping("/{movieId}/full-movie")
    public ResponseEntity<byte[]> streamFullMovie(@PathVariable Long movieId, @RequestHeader HttpHeaders headers, @RequestParam Long userId) throws IOException {

        MovieDto movieDto = movieService.getFullMovie(movieId, userId);


        String videoPath = movieDto.getFullVideoUrl();
        File video = new File(videoPath);

        if (!video.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpRange range = headers.getRange().stream().findFirst().orElse(null);
        if (range != null) {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .header(HttpHeaders.CONTENT_RANGE, range.toString())
                    .body(Files.readAllBytes(video.toPath()));
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .body(Files.readAllBytes(video.toPath()));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieEnt> getMovieDetails(@PathVariable Long movieId) {
        return ResponseEntity.ok(movieService.getMovieById(movieId));
    }

    @PostMapping("/upload")
    public ResponseEntity<MovieEnt> uploadMovie(@RequestBody MovieDto movieDto) {
        MovieEnt savedMovie = movieService.uploadMovie(movieDto);
        return ResponseEntity.ok(savedMovie);
    }
}

