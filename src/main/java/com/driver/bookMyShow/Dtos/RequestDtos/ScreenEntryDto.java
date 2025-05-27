package com.driver.bookMyShow.Dtos.RequestDtos;

import com.driver.bookMyShow.Enums.ScreenType;
import com.driver.bookMyShow.Exceptions.RequestFailedException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenEntryDto {
    private String id;
    private String resolution;


    public void validate() {
        if ((resolution == null || resolution.isEmpty()))
            throw new RequestFailedException();
        ScreenType.validate(resolution);
    }
}
