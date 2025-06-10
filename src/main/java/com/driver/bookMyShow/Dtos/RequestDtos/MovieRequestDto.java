package com.driver.bookMyShow.Dtos.RequestDtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequestDto
{
    private boolean adult;
    private String backdropPath;
    private List<Integer> genreIds;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private double popularity;
    private String posterPath;
    private String releaseDate;
    private String title;
    private boolean video;
    private double voteAverage;
    private int voteCount;
}
