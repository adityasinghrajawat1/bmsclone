package com.driver.bookMyShow.Repositories;

import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.Models.SeatingArrangement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatingArrangementRepository extends JpaRepository<SeatingArrangement, String> {
//        Optional<SeatingArrangement> findBySeatType(String seatType);
//        Optional<List<SeatingArrangement>> findByScreen(Screen screen);
}
