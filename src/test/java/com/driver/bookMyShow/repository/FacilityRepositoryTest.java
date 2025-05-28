package com.driver.bookMyShow.repository;

import com.driver.bookMyShow.Models.Facility;
import com.driver.bookMyShow.Repositories.FacilityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
public class FacilityRepositoryTest
{
    @Autowired
    private FacilityRepository facilityRepository;

    private Facility facility1;
    private Facility facility2;

    @BeforeEach
    void init()
    {
        facility1 = Facility.builder()
                .id("1")
                .name("WiFi")
                .logo("wifi.png")
                .isActive(true)
                .deleted(false)
                .build();

       facility2 = Facility.builder()
                .id("2")
                .name("Parking")
                .logo("park.jpeg")
                .isActive(true)
                .deleted(false)
                .build();

       facilityRepository.save(facility1);
       facilityRepository.save(facility2);
    }

    @AfterEach
    void clean()
    {
        facilityRepository.deleteAll();
    }

    @Test
    @DisplayName("It should save the facility to the database")
    void save()
    {
        Facility newFacility = Facility.builder()
                .id("3")
                .name("Wheel Chair")
                .logo("wc.svg")
                .isActive(true)
                .deleted(false)
                .build();

        Facility saved = facilityRepository.save(newFacility);
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isEqualTo("3");
        Assertions.assertThat(saved.getName()).isEqualTo("Wheel Chair");
    }

    @Test
    @DisplayName("It should return the facility by its id")
    void findById()
    {
        Facility current = facilityRepository.findById("2").orElse(null);
        Assertions.assertThat(current).isNotNull();
        Assertions.assertThat(current.getName()).isEqualTo("Parking");
    }

    @Test
    @DisplayName("It should confirm the existance of facility by its id")
    void existsById()
    {
        boolean exists = facilityRepository.existsById("1");
        Assertions.assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("It should confirm the existance of facility by its id and should be active")
    void existsByIdAndIsActiveTrue()
    {
        boolean exists = facilityRepository.existsByIdAndIsActiveTrue("2");
        Assertions.assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("It should confirm the existance of facility by its name")
    void existsByName()
    {
        boolean exists = facilityRepository.existsByName("Parking");
        Assertions.assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("It should return the facility by its name")
    void findByName()
    {
        Facility current = facilityRepository.findByName("WiFi").orElse(null);
        Assertions.assertThat(current).isNotNull();
        Assertions.assertThat(current.getLogo()).isEqualTo("wifi.png");
        Assertions.assertThat(current.getId()).isEqualTo("1");
    }

    @Test
    @DisplayName("It should return all the facilities with id and should be active")
    void findAllByIdInAndIsActiveTrue()
    {
        List<String> ids = new ArrayList<>(Arrays.asList("1","2"));
        List<Facility> facilities = facilityRepository.findAllByIdInAndIsActiveTrue(ids);
        Assertions.assertThat(facilities).hasSize(2);
        Assertions.assertThat(facilities).isNotNull();
    }

    @Test
    @DisplayName("It should return the facility list with size 2")
    void findAll()
    {
        List<Facility> facilities = facilityRepository.findAll();
        Assertions.assertThat(facilities).hasSize(2);
        Assertions.assertThat(facilities).isNotNull();
    }

    @Test
    @DisplayName("It should delete a facility by id")
    void delete()
    {
        facilityRepository.deleteById("2");
        Facility facility = facilityRepository.findById("2").orElse(null);
        List<Facility> facilities = facilityRepository.findAll();
        Assertions.assertThat(facilities).hasSize(1);
        Assertions.assertThat(facilities).isNotNull();
        Assertions.assertThat(facility).isNull();
    }

    @Test
    @DisplayName("It should update the facility")
    void update()
    {
        Facility existingFacility = facilityRepository.findById("1").get();
        existingFacility.setName("Wireless Fidelity");

        Facility updatedFacility = facilityRepository.save(existingFacility);
        Assertions.assertThat(updatedFacility.getName()).isEqualTo("Wireless Fidelity");
        Assertions.assertThat(updatedFacility.getLogo()).isEqualTo("wifi.png");
    }
}
