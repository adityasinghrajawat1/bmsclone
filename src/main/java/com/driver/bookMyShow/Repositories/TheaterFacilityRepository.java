package com.driver.bookMyShow.Repositories;

import com.driver.bookMyShow.Models.Facility;
import com.driver.bookMyShow.Models.TheaterFacility;
import com.driver.bookMyShow.Models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TheaterFacilityRepository extends JpaRepository<TheaterFacility, String> {
    Optional<TheaterFacility> findByTheaterAndFacility(Theater theater, Facility facility);

    List<TheaterFacility> findByFacility(Facility facility);

    void deleteByTheater(Theater theater);
}
