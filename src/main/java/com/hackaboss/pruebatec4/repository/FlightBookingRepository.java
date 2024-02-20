package com.hackaboss.pruebatec4.repository;

import com.hackaboss.pruebatec4.model.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking,Long> {
}
