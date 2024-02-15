package com.hackaboss.pruebatec4.repository;

import com.hackaboss.pruebatec4.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {
}
