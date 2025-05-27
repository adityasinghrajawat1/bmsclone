package com.driver.bookMyShow.Dtos.ResponseDtos;

import com.driver.bookMyShow.Models.Movie;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponseDto {
    private LocalDateTime show_start_time;
    private LocalDateTime show_end_time;
    private Boolean isActive;
    private MovieResponseDto movie;
}
