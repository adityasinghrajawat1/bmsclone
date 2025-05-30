package com.driver.bookMyShow.repository;

import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.Models.Theater;
import com.driver.bookMyShow.Repositories.ScreenRepository;
import com.driver.bookMyShow.Repositories.TheaterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ScreenRepositoryTest
{
    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    private Theater theater;
    private Screen screen;

    @BeforeEach
    void setUp()
    {
       theater = Theater.builder()
               .id("theaterId")
               .name("INOX")
               .address("Fun Plaza, High Street")
               .isActive(true)
               .deleted(false)
               .build();

       theaterRepository.save(theater);

       screen = Screen.builder()
               .id("screenId")
               .resolution("3D")
               .theater(theater)
               .isActive(true)
               .deleted(false)
               .build();
       screenRepository.save(screen);
    }

    @Test
    void existsById()
    {
        boolean exists = screenRepository.existsById(screen.getId());
        Assertions.assertThat(exists).isTrue();
    }

    @Test
    void existsByIdAndTheater()
    {
       boolean exists = screenRepository.existsByIdAndTheater(screen.getId(),theater);
       Assertions.assertThat(exists).isTrue();
    }

    @Test
    void findByIdAndResolution()
    {
        Optional<Screen> optionalScreen =  screenRepository.findByIdAndResolution(screen.getId(),"3D");

        Assertions.assertThat(optionalScreen).isPresent();
        Assertions.assertThat(optionalScreen.get().getResolution()).isEqualTo("3D");
    }

    @Test
    void findByIdAndResolution_NotFound()
    {
        Optional<Screen> optionalScreen = screenRepository.findByIdAndResolution(screen.getId(), "4K");

        Assertions.assertThat(optionalScreen).isNotPresent();
    }
}

