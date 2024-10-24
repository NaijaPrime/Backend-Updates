package com.app.naijaprime.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
@Table(name = "tbl_movies")
@Entity
@Data
public class MovieEnt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    private String description;
    private String trailerUrl;
    private String fullVideoUrl;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonBackReference
    private ContentCreator contentCreator;

    public MovieEnt() {
    }


}
