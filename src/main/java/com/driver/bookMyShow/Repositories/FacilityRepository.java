package com.driver.bookMyShow.Repositories;

import com.driver.bookMyShow.Models.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, String> {
    boolean existsById(String id);

    boolean existsByIdAndIsActiveTrue(String id);

    boolean existsByName(String name);

    Optional<Facility> findByName(String name);

    List<Facility>  findAllByIdInAndIsActiveTrue(List<String> ids);
}
