package com.driver.bookMyShow.Services;

import com.driver.bookMyShow.Dtos.RequestDtos.ScreenEntryDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnScreenDto;
import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.Models.Theater;
import com.driver.bookMyShow.Transformers.ScreenTransformer;
import com.driver.bookMyShow.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ScreenService {
    Screen insertTheaterScreen(Screen screen);

    ReturnScreenDto screenActiveInactive(Screen screen);

    void deleteById(String screenId);

    void existsByIdAndTheater(String id, Theater theater);

    Optional<Screen> findById(String id);

    ReturnScreenDto findByIdAndScreenType(String id, String screenType);
}
