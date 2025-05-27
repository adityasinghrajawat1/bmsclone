package com.driver.bookMyShow.Repositories;

import com.driver.bookMyShow.Models.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, String> {
    //Optional<Facilityv1> findByTheatev1AndName(Theaterv1 theaterv1 , String name);
    // Optional<Facilityv1> findByTheaterv1AndName(Theaterv1 theaterv1, String name);
    boolean existsById(String id);

    boolean existsByIdAndIsActiveTrue(String id);

    //Optional<List<Facilityv1>> findAllByTheaterv1(Theaterv1 theaterv1);
    boolean existsByName(String name);

    boolean existsByIdAndDeletedTrue(String id);

    Optional<Facility> findByName(String name);

    List<Facility> findAllByIdInAndIsActiveTrueAndDeletedFalse(List<String> ids);

    List<Facility> findAllByIdInAndIsActiveTrue(List<String> ids);
}
