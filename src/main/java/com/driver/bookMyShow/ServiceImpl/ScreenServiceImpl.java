package com.driver.bookMyShow.ServiceImpl;

import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnScreenDto;
import com.driver.bookMyShow.Exceptions.NotFoundException;
import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.Models.Theater;
import com.driver.bookMyShow.Repositories.ScreenRepository;
import com.driver.bookMyShow.Services.ScreenService;
import com.driver.bookMyShow.Transformers.ScreenTransformer;
import com.driver.bookMyShow.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ScreenServiceImpl implements ScreenService {

    @Autowired
    private ScreenRepository screenRepository;


    @Override
    public Screen insertTheaterScreen(Screen screen) {
        return screenRepository.save(screen);
    }

    @Override
    public ReturnScreenDto screenActiveInactive(Screen screen) {
        screen.setIsActive(screen.getIsActive() ? Boolean.FALSE : Boolean.TRUE);
        screenRepository.save(screen);
        return ScreenTransformer.ScreenToReturnScreenDto(screen);
    }

    @Override
    public void deleteById(String screenId) {
        screenRepository.findById(screenId).orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.SCREEN));
        screenRepository.deleteById(screenId);
    }

    @Override
    public void existsByIdAndTheater(String id, Theater theater) {
        Boolean flag = screenRepository.existsByIdAndTheater(id, theater);
        if (Boolean.FALSE.equals(flag))
            throw NotFoundException.getExceptionWithDesc(Messages.SCREEN);
    }

    @Override
    public Optional<Screen> findById(String id) {
        return screenRepository.findById(id);
    }

    @Override
    public ReturnScreenDto findByIdAndScreenType(String id, String screenType) {
        if (!screenRepository.existsById(id))
            throw NotFoundException.getExceptionWithDesc(Messages.SCREEN);

        Screen screen = screenRepository.findByIdAndResolution(id, screenType)
                .orElseThrow(() -> NotFoundException.getExceptionWithDesc(Messages.SCREEN));
        return ScreenTransformer.ScreenToReturnScreenDto(screen);
    }


}
