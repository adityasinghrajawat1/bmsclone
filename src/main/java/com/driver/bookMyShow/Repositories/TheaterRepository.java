package com.driver.bookMyShow.Repositories;

import com.driver.bookMyShow.Models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TheaterRepository extends JpaRepository<Theater, String> {
    Optional<Theater> findByAddress(String address);

    Boolean existsByAddress(String address);
}
