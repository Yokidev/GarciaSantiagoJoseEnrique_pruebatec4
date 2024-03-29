package com.hackaboss.pruebatec4.repository;

import com.hackaboss.pruebatec4.model.Flight;
import com.hackaboss.pruebatec4.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByCity(String city);
    Hotel findByNameAndCity(String name, String city);
}
