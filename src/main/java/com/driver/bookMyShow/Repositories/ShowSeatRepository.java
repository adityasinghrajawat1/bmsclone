package com.driver.bookMyShow.Repositories;

import com.driver.bookMyShow.Models.Show;
import com.driver.bookMyShow.Models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, String> {
    Optional<ShowSeat> findByShowAndSeatType(Show show, String seatType);

    boolean existsByShow(Show show);

    //List<ShowSeatv1> findAllByShow(int id);
}
