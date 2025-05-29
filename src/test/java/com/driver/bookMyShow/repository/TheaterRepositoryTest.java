package com.driver.bookMyShow.repository;

import com.driver.bookMyShow.Models.Theater;
import com.driver.bookMyShow.Repositories.TheaterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class TheaterRepositoryTest
{
    @Autowired
    private TheaterRepository theaterRepository;

    private Theater theater1;
    private Theater theater2;

    @BeforeEach
    void init()
    {
        theater1 = Theater.builder()
                .id("101")
                .name("INOX")
                .address("Watch Tower,Downtown")
                .isActive(true)
                .deleted(false)
                .build();

        theater2 = Theater.builder()
                .id("102")
                .name("PVR")
                .address("Fun Plaza,Silicon Valley")
                .isActive(true)
                .deleted(false)
                .build();

        theaterRepository.save(theater1);
        theaterRepository.save(theater2);
    }

    @AfterEach
    void clean()
    {
        theaterRepository.deleteAll();
    }

    @Test
    @DisplayName("It should save the theater object")
    void save()
    {
        Theater theater = Theater.builder()
                .id("111")
                .name("PVR 4K")
                .address("World Trade Center, High Street")
                .isActive(true)
                .deleted(false)
                .build();

        Theater saved =  theaterRepository.save(theater);

        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getName()).isEqualTo("PVR 4K");
        Assertions.assertThat(saved.getId()).isEqualTo("111");
    }

    @Test
    @DisplayName("It should return theater object by its id")
    void findById()
    {
        Theater current = theaterRepository.findById("101").orElse(null);

        Assertions.assertThat(current).isNotNull();
        Assertions.assertThat(current.getName()).isEqualTo("INOX");
    }

    @Test
    @DisplayName("It should return all theater object")
    void findAll()
    {
        List<Theater> theaters = theaterRepository.findAll();
        Assertions.assertThat(theaters).hasSize(2);
        Assertions.assertThat(theaters.get(1).getName()).isEqualTo("PVR");
    }

    @Test
    @DisplayName("It should delete the theater object by id")
    void delete()
    {
        theaterRepository.deleteById("102");
        Theater theater = theaterRepository.findById("102").orElse(null);
        List<Theater> theaters = theaterRepository.findAll();

        Assertions.assertThat(theater).isNull();
        Assertions.assertThat(theaters).hasSize(1);
        Assertions.assertThat(theaters).isNotNull();
    }

    @Test
    @DisplayName("It should save and retrieve theater object by address")
    void findByAddress()
    {
        Theater theater = Theater.builder()
                .id("110")
                .name("Cinepolis")
                .address("Shopping Mall, Tech City")
                .isActive(true)
                .deleted(false)
                .build();

        theaterRepository.save(theater);

        Optional<Theater> found = theaterRepository.findByAddress("Shopping Mall, Tech City");

        Assertions.assertThat(found).isPresent();
        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.get().getName()).isEqualTo("Cinepolis");
    }

    @Test
    @DisplayName("Check if theater with address exists")
    void existsByAddress()
    {
        Theater theater = Theater.builder()
                .id("1991")
                .name("INOX Premium")
                .address("FilmCity, Jogger's Park")
                .isActive(true)
                .deleted(false)
                .build();

        theaterRepository.save(theater);

        Assertions.assertThat(theaterRepository.existsByAddress("FilmCity, Jogger's Park")).isTrue();
        Assertions.assertThat(theaterRepository.existsByAddress("XYZ Plaza, ABC City"));
    }
}