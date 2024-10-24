package com.app.naijaprime.dto;

import lombok.Data;

@Data
public class MovieResponseDto {
    private Long id;
    private String title;
    private String genre;
    private String description;
    private String trailerUrl;
    private String fullVideoUrl;
    private Double price;
}
