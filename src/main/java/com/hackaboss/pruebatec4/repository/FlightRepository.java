package com.hackaboss.pruebatec4.repository;

import com.hackaboss.pruebatec4.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {
    Optional<Flight> findByCode(String code);
    List<Flight> findByOriginAndDestinationAndDateBetween(String origin, String destination, LocalDate startDate, LocalDate endDate);
}
