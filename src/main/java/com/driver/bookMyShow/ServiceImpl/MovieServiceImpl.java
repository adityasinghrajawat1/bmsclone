package com.driver.bookMyShow.ServiceImpl;

import com.driver.bookMyShow.Dtos.RequestDtos.MovieRequestDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.MovieAPIResponseDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.MovieResponseDto;
import com.driver.bookMyShow.Exceptions.NotFoundException;
import com.driver.bookMyShow.Models.Movie;
import com.driver.bookMyShow.Repositories.MovieRepository;
import com.driver.bookMyShow.Services.MovieService;
import com.driver.bookMyShow.Transformers.MovieTransformer;
import com.driver.bookMyShow.config.ApiConfig;
import com.driver.bookMyShow.constant.ApiConstants;
import com.driver.bookMyShow.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApiConfig apiConfig;

    @Override
    public boolean existsById(String id) {
        return movieRepository.existsById(id);
    }

    @Override
    public Optional<Movie> findById(String id) {
        return movieRepository.findById(id);
    }

    @Override
    public String fetchAndSave()
    {
        HttpEntity<Void> httpEntity = new HttpEntity<>(getHttpHeaders());
        StringBuilder urlBuilder = new StringBuilder(apiConfig.getBaseurlfordata());
        urlBuilder.append(ApiConstants.FORWARD_SLASH).append(ApiConstants.DISCOVER)
                .append(ApiConstants.FORWARD_SLASH).append(ApiConstants.MOVIE)
                .append(ApiConstants.QUESTION_MARK).append(ApiConstants.API_KEY)
                .append(ApiConstants.ASSIGN_OPTR).append(apiConfig.getApikey())
                .append(ApiConstants.AND_OPTR).append(ApiConstants.PAGE).append(ApiConstants.ASSIGN_OPTR)
                .append(ApiConstants.ONE);

        ResponseEntity<MovieAPIResponseDto> response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.GET,
                httpEntity,
                MovieAPIResponseDto.class
        );

        if (response.getStatusCode() == HttpStatus.OK  && response.getBody() != null)
        {
            List<MovieResponseDto> movieResponseDtos = response.getBody().getResults();

            for (MovieResponseDto movieDto : movieResponseDtos)
            {
                Movie movie = new Movie();
                movie.setId(String.valueOf(movieDto.getId()));
                movie.setTitle(movieDto.getTitle());
                movie.setOverview(movieDto.getOverview());
                movie.setOriginalLanguage(movieDto.getOriginalLanguage());
                movie.setOriginalTitle(movieDto.getOriginalTitle());
                movie.setReleaseDate(movieDto.getReleaseDate());
                movie.setPosterPath(movieDto.getPosterPath());
                movie.setBackdropPath(movieDto.getBackdropPath());
                movie.setPopularity(movieDto.getPopularity());
                movie.setVoteAverage(movieDto.getVoteAverage());
                movie.setVoteCount(movieDto.getVoteCount());
                movie.setAdult(movieDto.isAdult());
                movie.setVideo(movieDto.isVideo());
                movie.setGenreIds(movieDto.getGenreIds());
                movie.setIsActive(true);
                movie.setDeleted(false);

                    if (!movieRepository.existsById(movie.getId()))
                        movieRepository.save(movie);
            }
            return "Movies fetched and saved successfully.";
        }

        return "Failed to fetch movies.";
    }

    @Override
    public MovieResponseDto findMovieById(String id)
    {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.MOVIE));
        return MovieTransformer.MovieToReponseDTO(movie);
    }

    @Override
    public List<MovieResponseDto> findAllMovies()
    {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty())
            throw NotFoundException.getExceptionWithDesc(Messages.MOVIE);

        List<MovieResponseDto> movieResponseDtos = movies.stream()
                .map(movie -> {
               return      MovieTransformer.MovieToReponseDTO(movie);
                }).toList();
        return movieResponseDtos;
    }

    @Override
    public MovieResponseDto movieActiveInactive(String id)
    {
       Movie movie =  movieRepository.findById(id)
               .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.MOVIE));

       movie.setIsActive(movie.getIsActive()?Boolean.FALSE:Boolean.TRUE);
       movieRepository.save(movie);
       return MovieTransformer.MovieToReponseDTO(movie);
    }

    @Override
    public String deleteById(String id)
    {
        boolean exists = movieRepository.existsById(id);
        if (!exists)
            throw NotFoundException.getExceptionWithDesc(Messages.MOVIE);
        movieRepository.deleteById(id);
        return Messages.MOVIE+Messages.ONE_TAB+Messages.DELETED
                +Messages.ONE_TAB+Messages.SUCCESSFULLY+Messages.DOT;
    }

    @Override
    public MovieResponseDto update(String id, MovieRequestDto movieRequestDto)
    {
        Optional<Movie> existing = movieRepository.findById(id);

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.MOVIE));

        if (!existing.get().getId().equals(id))
         throw NotFoundException.getExceptionWithDesc(Messages.MOVIE);

        movie.setAdult(movieRequestDto.isAdult());
        movie.setBackdropPath(movieRequestDto.getBackdropPath());
        movie.setReleaseDate(movieRequestDto.getReleaseDate());
        movie.setGenreIds(movieRequestDto.getGenreIds());
        movie.setVoteCount(movieRequestDto.getVoteCount());
        movie.setTitle(movieRequestDto.getTitle());
        movie.setVoteAverage(movieRequestDto.getVoteAverage());
        movie.setOverview(movieRequestDto.getOverview());
        movie.setIsActive(true);
        movie.setDeleted(false);
        movie.setOriginalLanguage(movieRequestDto.getOriginalLanguage());
        movie.setPosterPath(movieRequestDto.getPosterPath());
        movie.setOriginalTitle(movieRequestDto.getOriginalTitle());
        movie.setPopularity(movieRequestDto.getPopularity());
        movie.setVideo(movieRequestDto.isVideo());
        movieRepository.save(movie);
        return MovieTransformer.MovieToReponseDTO(movie);
    }


    private HttpHeaders getHttpHeaders()
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }
}
