package com.driver.bookMyShow.Dtos.RequestDtos;

import com.driver.bookMyShow.Enums.ShowStatus;
import com.driver.bookMyShow.Exceptions.RequestFailedException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ShowRequestDto {
    private LocalDateTime show_start_time;
    private LocalDateTime show_end_time;

    public void validate() {
        if (show_start_time == null || show_end_time == null)
            throw new RequestFailedException();
    }
}
