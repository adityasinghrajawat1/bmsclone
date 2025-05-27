package com.driver.bookMyShow.Transformers;

import com.driver.bookMyShow.Dtos.RequestDtos.ShowRequestDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ShowResponseDto;
import com.driver.bookMyShow.Enums.ShowStatus;
import com.driver.bookMyShow.Models.Show;

public class ShowTransformer {
    public static Show ShowRequestDtoToShow(ShowRequestDto showRequestDto) {
        Show show = Show.builder()
                .startTime(showRequestDto.getShow_start_time())
                .endTime(showRequestDto.getShow_end_time())
                .isActive(Boolean.TRUE)
                .deleted(Boolean.FALSE)
                .build();
        return show;
    }

    public static ShowResponseDto ShowToShowResponseDto(Show show) {
        ShowResponseDto showResponseDto = ShowResponseDto.builder()
                .show_start_time(show.getStartTime())
                .show_end_time(show.getEndTime())
                .isActive(show.getIsActive())
//                .movie(MovieTransformer.MovieToMovieResponseDto(show.getMovie()))
                .build();
        return showResponseDto;
    }
}
