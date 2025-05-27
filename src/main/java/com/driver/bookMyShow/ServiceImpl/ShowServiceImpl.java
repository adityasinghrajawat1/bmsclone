package com.driver.bookMyShow.ServiceImpl;

import com.driver.bookMyShow.Dtos.RequestDtos.ShowRequestDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ShowResponseDto;
import com.driver.bookMyShow.Enums.ShowStatus;
import com.driver.bookMyShow.Exceptions.AlreadyPresentException;
import com.driver.bookMyShow.Exceptions.NotFoundException;
import com.driver.bookMyShow.Models.Movie;
import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.Models.Show;
import com.driver.bookMyShow.Repositories.ShowRepository;
import com.driver.bookMyShow.Services.MovieService;
import com.driver.bookMyShow.Services.ScreenService;
import com.driver.bookMyShow.Services.ShowService;
import com.driver.bookMyShow.Transformers.ShowTransformer;
import com.driver.bookMyShow.constant.Messages;
import com.driver.bookMyShow.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShowServiceImpl implements ShowService {
    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ScreenService screenService;

    @Autowired
    private MovieService movieService;

    @Override
    public ShowResponseDto saveShow(ShowRequestDto showRequestDto, String movieId, String screenId) {
//        Movie movie = movieService.findById(movieId)
//                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.MOVIE));

        Screen screen = screenService.findById(screenId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.SCREEN));

        boolean isOverlapping = showRepository.existsByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan(screenId, showRequestDto.getShow_end_time(), showRequestDto.getShow_start_time());

        if (isOverlapping)
            throw new AlreadyPresentException(Messages.SHOW + Messages.ONE_TAB + Messages.ALREADY_EXISTS + Messages.ONE_TAB
                    + Messages.FOR + Messages.ONE_TAB + Messages.THIS + Messages.ONE_TAB + Messages.TIME_RANGE + Messages.DOT);

        showRequestDto.validate();
        Show show = ShowTransformer.ShowRequestDtoToShow(showRequestDto);
        show.setId(Utils.generateUUID(10));
        show.setMovie(null);
        show.setScreen(screen);
        ShowStatus showStatus = ShowStatus.fromString("initiated");
        show.setStatus(showStatus.getDisplayName());
        showRepository.save(show);
        return ShowTransformer.ShowToShowResponseDto(show);
    }

    @Override
    public List<ShowResponseDto> getShows(String movieId) {
        Movie movie = movieService.findById(movieId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.MOVIE));

        List<Show> shows = movie.getShows().stream()
                .filter(s -> s.getMovie().getId().equals(movieId))
                .toList();
        List<ShowResponseDto> showResponseDtoList = shows.stream()
                .map(show -> ShowTransformer.ShowToShowResponseDto(show))
                .toList();
        return showResponseDtoList;
    }

    @Override
    public ShowResponseDto showActiveInactive(String showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.SHOW));
        show.setIsActive(show.getIsActive() ? Boolean.FALSE : Boolean.TRUE);
        showRepository.save(show);
        return ShowTransformer.ShowToShowResponseDto(show);
    }

    @Override
    public String deleteShow(String showId) {
        showRepository.findById(showId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.SHOW));
        showRepository.deleteById(showId);
        return Messages.SHOW + Messages.ONE_TAB + Messages.DELETED + Messages.ONE_TAB + Messages.SUCCESSFULLY + Messages.DOT;
    }

    @Override
    public ShowResponseDto updateShow(String showId, ShowRequestDto showRequestDto) {
        showRequestDto.validate();

        Show existingShow = showRepository.findById(showId)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.SHOW));

        List<Show> overlappingShows = showRepository
                .findByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan(
                        existingShow.getScreen().getId(),
                        showRequestDto.getShow_end_time(),
                        showRequestDto.getShow_start_time()
                );


        boolean conflictExists = overlappingShows.stream()
                .anyMatch(show -> !show.getId().equals(showId));

        if (conflictExists)
            throw new AlreadyPresentException(Messages.SHOW + Messages.ONE_TAB + Messages.ALREADY_EXISTS + Messages.ONE_TAB
                    + Messages.FOR + Messages.ONE_TAB + Messages.THIS + Messages.ONE_TAB + Messages.TIME_RANGE + Messages.DOT);

        existingShow.setStartTime(showRequestDto.getShow_start_time());
        existingShow.setEndTime(showRequestDto.getShow_end_time());
        showRepository.save(existingShow);

        return ShowTransformer.ShowToShowResponseDto(existingShow);
    }
}
