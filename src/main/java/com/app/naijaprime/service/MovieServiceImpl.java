package com.app.naijaprime.service;

import com.app.naijaprime.dto.MovieDto;
import com.app.naijaprime.entity.ContentCreator;
import com.app.naijaprime.entity.MovieEnt;
import com.app.naijaprime.repo.ContentCreatorRepo;
import com.app.naijaprime.repo.MovieEntRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieEntRepo movieEntRepo;
    @Autowired
    private ContentCreatorRepo contentCreatorRepo;

    @Override
    public Optional<MovieDto> getMovieTrailer(Long movieId) {
        return movieEntRepo.findById(movieId)
                .map(movieEnt -> {
                    MovieDto movieDto = new MovieDto();
                    movieDto.setId(movieEnt.getId());
                    movieDto.setTitle(movieEnt.getTitle());
                    movieDto.setDescription(movieEnt.getDescription());
                    movieDto.setGenre(movieEnt.getGenre());
                    movieDto.setPrice(movieEnt.getPrice());
                    movieDto.setTrailerUrl(movieEnt.getTrailerUrl());
                    movieDto.setFullVideoUrl(movieEnt.getFullVideoUrl());

                    return movieDto;
                });
    }

    @Override
    public MovieEnt getMovieById(Long movieId) {
        return movieEntRepo.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie with id " + movieId + " not found"));
    }

    @Override
    public MovieDto getFullMovie(Long movieId, Long userId) {
        if (!hasPaidForMovie(userId, movieId)) {
            throw new IllegalArgumentException("User has not paid for the movie.");
        }
        MovieEnt movieEnt = getMovieById(movieId);
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movieEnt.getId());
        movieDto.setTitle(movieEnt.getTitle());
        movieDto.setDescription(movieEnt.getDescription());
        movieDto.setGenre(movieEnt.getGenre());
        movieDto.setPrice(movieEnt.getPrice());
        movieDto.setTrailerUrl(movieEnt.getTrailerUrl());
        movieDto.setFullVideoUrl(movieEnt.getFullVideoUrl());

        return movieDto;
    }

    @Override
    public MovieEnt uploadMovie(MovieDto movieDto) {
        Optional<ContentCreator> contentCreatorOptional = contentCreatorRepo.findById(movieDto.getContentCreatorId());


        if (!contentCreatorOptional.isPresent()) {
            throw new IllegalArgumentException("Content creator not found.");
        }

        ContentCreator contentCreator = contentCreatorOptional.get();
        Optional<MovieEnt> existingMovie = movieEntRepo.findByTitleAndContentCreator(movieDto.getTitle(), contentCreator);
        if (existingMovie.isPresent()) {
            throw new IllegalArgumentException("A movie with the same title already exists for this content creator.");
        }

        MovieEnt movie = new MovieEnt();
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setGenre(movieDto.getGenre());
        movie.setPrice(movieDto.getPrice());
        movie.setFullVideoUrl("/movies/" + movieDto.getTitle().replace(" ", "_").toLowerCase() + ".mp4"); // For simplicity

        movie.setContentCreator(contentCreator);
        contentCreator.getMovies().add(movie);

        return movieEntRepo.save(movie);
    }


    private boolean hasPaidForMovie(Long userId, Long movieId) {
        return true;
    }
}
