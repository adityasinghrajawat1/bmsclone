package com.driver.bookMyShow.Repositories;

import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.Models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, String> {
    List<Show> findByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan(
            String screenId, LocalDateTime endTime, LocalDateTime startTime
    );

    Optional<Boolean> existsByScreenIdAndStartTimeBetweenOrScreenIdAndEndTimeBetween(
            String screenId1, LocalDateTime start1, LocalDateTime end1,
            String screenId2, LocalDateTime start2, LocalDateTime end2
    );

    boolean existsByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan(
            String screenId, LocalDateTime newEnd, LocalDateTime newStart
    );


//    Optional<Boolean> existsByScreenIdAndStartTimeBetweenOrEndTimeBetween(
//            String screenId, LocalDateTime start1,LocalDateTime end1,
//            LocalDateTime start2, LocalDateTime end2);

}
