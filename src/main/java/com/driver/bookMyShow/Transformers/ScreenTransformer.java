package com.driver.bookMyShow.Transformers;

import com.driver.bookMyShow.Dtos.RequestDtos.ScreenEntryDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnScreenDto;
import com.driver.bookMyShow.Enums.ScreenType;
import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.constant.Messages;


public class ScreenTransformer {
    public static Screen ScreenEntryDtoToScreen(ScreenEntryDto screenEntryDto) {
        ScreenType screenType = ScreenType.fromString(screenEntryDto.getResolution());
        Screen screen = Screen.builder()
                .isActive(Messages.TRUE)
                .deleted(Messages.FALSE)
                .resolution(screenType.getScreenName())
                .build();
        return screen;
    }

    public static ReturnScreenDto ScreenToReturnScreenDto(Screen screen) {
        ReturnScreenDto returnScreenDto = ReturnScreenDto.builder()
                .resolution(screen.getResolution())
                .id(screen.getId())
                .isActive(screen.getIsActive())
                .build();
        return returnScreenDto;
    }
}
