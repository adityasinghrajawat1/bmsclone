package com.driver.bookMyShow.repository;


import com.driver.bookMyShow.Models.Facility;
import com.driver.bookMyShow.Models.Movie;
import com.driver.bookMyShow.Repositories.FacilityRepository;
import com.driver.bookMyShow.utils.FacilityTestHelper;
import com.driver.bookMyShow.utils.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class FacilityRepositoryTest {
    @Autowired
    private FacilityRepository facilityRepository;

    @Test
    @DisplayName("It should save the facility to database")
    void save() {
        Facility facility = FacilityTestHelper.createFacilityInstance("Parking", "This logo is working for parking logo");
        //Act
        Facility facility1 = facilityRepository.save(facility);
        //Assert
        assertNotNull(facility1);
        assertThat(facility1.getId()).isNotEqualTo(null);
    }

    @Test
    @DisplayName("It should return facility's list with size of 2 ")
    void getAllFacility() {
        //Assert
        Facility facility = FacilityTestHelper.createFacilityInstance("Gaming zone", "This logo is for gaming zone");
        facilityRepository.save(facility);
        //Act
        Facility facility2 = FacilityTestHelper.createFacilityInstance("Parking zone", "This logo is for parking logo");
        facilityRepository.save(facility2);
        List<Facility> facilityList = facilityRepository.findAll();
        assertNotNull(facilityList);
        assertEquals(2, facilityList.size());
    }

    @Test
    @DisplayName("It should return the facility by id")
    void getFacilityById() {
        Facility facility = new Facility();
        facility.setId(Utils.generateUUID(10));
        facility.setDeleted(false);
        facility.setIsActive(true);
        facility.setName("Gaming Zone");
        facility.setLogo("This is gaming zone url ");

        facilityRepository.save(facility);
        Facility facility1 = facilityRepository.findById(facility.getId()).get();

        assertNotNull(facility1);
        assertEquals("Gaming Zone", facility1.getName());

    }

    @Test
    @DisplayName("It should return the update values")
    void updateFacility() {
        Facility facility = FacilityTestHelper.createFacilityInstance("Parking", "This is for parking logo");
        facilityRepository.save(facility);
        Facility newFacility = facilityRepository.findById(facility.getId()).get();
        newFacility.setIsActive(false);
        newFacility.setDeleted(true);
        newFacility.setName("Gaming Zone");
        Facility newfacility = facilityRepository.save(newFacility);
        assertNotNull(newfacility);
        assertEquals("Gaming Zone", newfacility.getName());
    }

    @Test
    @DisplayName("It should delete the existing facility")
    void deletedFacility() {
        Facility facility = FacilityTestHelper.createFacilityInstance("Gaming Zone", "this is gaming logo url");
        facilityRepository.save(facility);
        String id = facility.getId();
        Facility newFacility = FacilityTestHelper.createFacilityInstance("Parking", "This is parking logo");
        facilityRepository.save(newFacility);
        facilityRepository.delete(facility);
        Optional<Facility> exitingFacility = facilityRepository.findById(id);
        List<Facility> facilityList = facilityRepository.findAll();
        assertEquals(1, facilityList.size());
        assertThat(exitingFacility).isEmpty();

    }

    @Test
    @DisplayName("It should be provide result of facility")
    void existsById() {
        Facility facility = FacilityTestHelper.createFacilityInstance("Parking", "This logo is for parking");
        Facility newFacility = FacilityTestHelper.createFacilityInstance("Gaming zone", "This is for the gaming  zone");
        Facility existFacility = facilityRepository.save(facility);
        facilityRepository.save(newFacility);
        boolean exists = facilityRepository.existsById(existFacility.getId());
        assertThat(true).isTrue();
    }

    @Test
    @DisplayName("It should find facility by name")
    void existsByName() {
        Facility facility = FacilityTestHelper.createFacilityInstance("Parking", "this is parking logo");
        Facility newFacility = FacilityTestHelper.createFacilityInstance("Gaming Zone", "This is gmaing logo");
        facilityRepository.save(facility);
        facilityRepository.save(newFacility);
        boolean exists = facilityRepository.existsByName("pking");
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("It should be get all result that match by name")
    void findByName() {
        Facility facility = FacilityTestHelper.createFacilityInstance("Parking", "This is parking logo");
        Facility newfacility = FacilityTestHelper.createFacilityInstance("Gaming zone", "This is for the Gaming zone logo");
        facilityRepository.save(facility);
        facilityRepository.save(newfacility);
        Optional<Facility> optionalFacility= facilityRepository.findByName("Parking");
        assertNotNull(optionalFacility);
        assertThat(optionalFacility).isPresent();
        assertEquals("Parking",optionalFacility.get().getName());
    }




}
