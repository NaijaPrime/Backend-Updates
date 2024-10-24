package com.app.naijaprime.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Table(name = "tbl_content_creators")
@Entity
@Data
public class ContentCreator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  //  @NotEmpty(message = "Name cannot be empty")
    private String name;

    private Double totalEarnings = 0.0;
    private String email;

    @OneToMany(mappedBy = "contentCreator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonManagedReference
    private List<MovieEnt> movies = new ArrayList<>();
}
