package com.driver.bookMyShow.service;

import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnScreenDto;
import com.driver.bookMyShow.Exceptions.NotFoundException;
import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.Models.Theater;
import com.driver.bookMyShow.Repositories.ScreenRepository;
import com.driver.bookMyShow.ServiceImpl.ScreenServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScreenServiceImplTest
{
    @InjectMocks
    private ScreenServiceImpl screenService;

    @Mock
    private ScreenRepository screenRepository;

    private Screen screen;
    private Theater theater;

    @BeforeEach
    public void setUp()
    {
        theater = Theater.builder()
                .id("theaterId")
                .name("INOX")
                .address("Fun Plaza, High Street")
                .isActive(true)
                .deleted(false)
                .build();

        screen = Screen.builder()
                .id("screenId")
                .resolution("3D")
                .theater(theater)
                .isActive(true)
                .deleted(false)
                .build();
    }

    @Test
    void insertTheaterScreen()
    {
        when(screenRepository.save(any(Screen.class))).thenReturn(screen);

        Screen result = screenService.insertTheaterScreen(screen);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo("screenId");
        Assertions.assertThat(result.getResolution()).isEqualTo("3D");
    }

    @Test
    void screenActiveInactive()
    {
        when(screenRepository.save(any(Screen.class))).thenReturn(screen);
        ReturnScreenDto screenDto = screenService.screenActiveInactive(screen);

        Assertions.assertThat(screenDto).isNotNull();
        Assertions.assertThat(screenDto.getIsActive()).isFalse();
    }

    @Test
    void deleteById_Success()
    {
        when(screenRepository.findById(screen.getId())).thenReturn(Optional.of(screen));
        doNothing().when(screenRepository).deleteById(screen.getId());

        screenService.deleteById("screenId");
        verify(screenRepository,times(1)).deleteById("screenId");
    }

    @Test
    void deleteById_NotFound()
    {
        when(screenRepository.findById(screen.getId())).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
                () -> screenService.deleteById("screenId"));
    }

    @Test
    void existsByIdAndTheater_success()
    {
       when(screenRepository.existsByIdAndTheater(screen.getId(), theater)).thenReturn(true);
       screenService.existsByIdAndTheater(screen.getId(),theater);

       verify(screenRepository,times(1)).existsByIdAndTheater("screenId",theater);
    }

    @Test
    void existsByIdAndTheater_NotFound()
    {
        when(screenRepository.existsByIdAndTheater(screen.getId(),theater)).thenReturn(false);

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
                () -> screenService.existsByIdAndTheater("screenId", theater));
    }

    @Test
    void findById()
    {
            when(screenRepository.findById(screen.getId())).thenReturn(Optional.of(screen));
            Optional<Screen> optionalScreen = screenService.findById("screenId");

            Assertions.assertThat(optionalScreen).isPresent();
            Assertions.assertThat(optionalScreen.get().getResolution()).isEqualTo("3D");
    }

    @Test
    void findByIdAndScreenType_success()
    {
        when(screenRepository.existsById(screen.getId())).thenReturn(true);
        when(screenRepository.findByIdAndResolution(screen.getId(),screen.getResolution())).thenReturn(Optional.of(screen));

        ReturnScreenDto result = screenService.findByIdAndScreenType("screenId","3D");

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getResolution()).isEqualTo("3D");
    }

    @Test
    void findByIdAndScreenType_ScreenNotFound()
    {
        when(screenRepository.existsById(screen.getId())).thenReturn(false);
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
                () -> screenService.findByIdAndScreenType("screenId","3D"));
    }

    @Test
    void findByIdAndScreenType_ScreenTypeNotMatch()
    {
        when(screenRepository.existsById(screen.getId())).thenReturn(true);
        when(screenRepository.findByIdAndResolution(screen.getId(),"3D")).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
                () -> screenService.findByIdAndScreenType("screenId","3D"));
    }
}
