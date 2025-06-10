package com.driver.bookMyShow.Dtos.ResponseDtos;

import com.driver.bookMyShow.Models.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieAPIResponseDto
{
    private int page;
    private List<MovieResponseDto> results;
    private int total_pages;
    private int total_results;
}
