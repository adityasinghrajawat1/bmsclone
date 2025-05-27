package com.driver.bookMyShow.Repositories;

import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.Models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenRepository extends JpaRepository<Screen, String> {
    boolean existsById(String id);

    Boolean existsByIdAndTheater(String id, Theater theater);

    Optional<Screen> findByIdAndResolution(String id, String screenType);
}
