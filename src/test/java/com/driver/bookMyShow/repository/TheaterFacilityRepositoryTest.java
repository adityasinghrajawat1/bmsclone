package com.driver.bookMyShow.repository;

import com.driver.bookMyShow.Models.Facility;
import com.driver.bookMyShow.Models.Theater;
import com.driver.bookMyShow.Models.TheaterFacility;
import com.driver.bookMyShow.Repositories.FacilityRepository;
import com.driver.bookMyShow.Repositories.TheaterFacilityRepository;
import com.driver.bookMyShow.Repositories.TheaterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TheaterFacilityRepositoryTest {
    @Autowired
    private TheaterFacilityRepository theaterFacilityRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private FacilityRepository facilityRepository;


    @Test
    void findByFacility() {
        Facility facility = new Facility();
        facility.setId("facilityId");
        facility.setName("Parking");
        facility.setLogo("park.png");
        facility = facilityRepository.save(facility);

        TheaterFacility tf = new TheaterFacility();
        tf.setId("tf1");
        tf.setFacility(facility);
        theaterFacilityRepository.save(tf);

        List<TheaterFacility> result = theaterFacilityRepository.findByFacility(facility);
        assertEquals(1, result.size());
    }

    @Test
    void deleteByTheater() {
        Theater theater = new Theater();
        theater.setId("theaterId");
        theater.setName("INOX");
        theater.setAddress("Fun Plaza, High Street");
        theaterRepository.save(theater);

        Facility facility = new Facility();
        facility.setId("facilityId");
        facility.setName("Parking");
        facility.setLogo("park.png");
        facilityRepository.save(facility);

        TheaterFacility theaterFacility = new TheaterFacility();
        theaterFacility.setId("thf1");
        theaterFacility.setTheater(theater);
        theaterFacility.setFacility(facility);
        theaterFacilityRepository.save(theaterFacility);


        theaterFacilityRepository.deleteByTheater(theater);

        List<TheaterFacility> result = theaterFacilityRepository.findByFacility(facility);
        Assertions.assertTrue(result.isEmpty());
    }
}
