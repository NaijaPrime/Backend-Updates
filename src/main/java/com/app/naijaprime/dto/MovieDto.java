package com.app.naijaprime.dto;

import lombok.Data;

@Data
public class MovieDto {
    private Long id;
    private String title;
    private String description;
    private String trailerUrl;
    private String fullVideoUrl;
    private Double price;
    private String genre;
    private Long contentCreatorId;


}
