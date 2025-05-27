package com.driver.bookMyShow.Services;

import com.driver.bookMyShow.Dtos.RequestDtos.ShowRequestDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ShowResponseDto;

import java.util.List;

public interface ShowService {
    ShowResponseDto saveShow(ShowRequestDto showRequestDto, String movieId, String screenId);

    List<ShowResponseDto> getShows(String movieId);

    ShowResponseDto showActiveInactive(String showId);

    String deleteShow(String showId);

    ShowResponseDto updateShow(String showId, ShowRequestDto showRequestDto);
}
