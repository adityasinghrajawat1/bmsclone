package com.driver.bookMyShow.Transformers;

import com.driver.bookMyShow.Dtos.RequestDtos.MovieRequestDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.MovieResponseDto;
import com.driver.bookMyShow.Models.Movie;

public class MovieTransformer
{
    public static MovieResponseDto MovieToReponseDTO(Movie movie)
    {
        MovieResponseDto movieResponseDto = MovieResponseDto.builder()
                .id(movie.getId())
                .adult(movie.isAdult())
                .backdropPath(movie.getBackdropPath())
                .genreIds(movie.getGenreIds())
                .title(movie.getTitle())
                .video(movie.isVideo())
                .originalTitle(movie.getOriginalTitle())
                .originalLanguage(movie.getOriginalLanguage())
                .overview(movie.getOverview())
                .popularity(movie.getPopularity())
                .releaseDate(movie.getReleaseDate())
                .voteCount(movie.getVoteCount())
                .voteAverage(movie.getVoteAverage())
                .posterPath(movie.getPosterPath())
                .isActive(movie.getIsActive())
                .build();
        return movieResponseDto;
    }

    public static Movie MovieRequestDtoToMovie(MovieRequestDto movieRequestDto)
    {
        Movie movie = Movie.builder()

                .adult(movieRequestDto.isAdult())
                .backdropPath(movieRequestDto.getBackdropPath())
                .genreIds(movieRequestDto.getGenreIds())
                .title(movieRequestDto.getTitle())
                .video(movieRequestDto.isVideo())
                .originalTitle(movieRequestDto.getOriginalTitle())
                .originalLanguage(movieRequestDto.getOriginalLanguage())
                .overview(movieRequestDto.getOverview())
                .popularity(movieRequestDto.getPopularity())
                .releaseDate(movieRequestDto.getReleaseDate())
                .voteCount(movieRequestDto.getVoteCount())
                .voteAverage(movieRequestDto.getVoteAverage())
                .posterPath(movieRequestDto.getPosterPath())
                .build();

        return movie;
    }
}
