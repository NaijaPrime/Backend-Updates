package com.app.naijaprime.service;

import com.app.naijaprime.dto.MovieDto;
import com.app.naijaprime.entity.MovieEnt;

import java.util.Optional;

public interface MovieService {
    public Optional<MovieDto> getMovieTrailer(Long movieId);
    public MovieEnt getMovieById(Long movieId);
    public MovieDto getFullMovie(Long movieId, Long userId);

    public MovieEnt uploadMovie(MovieDto movieDto);

}
