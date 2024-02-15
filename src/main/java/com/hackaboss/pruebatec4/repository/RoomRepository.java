package com.hackaboss.pruebatec4.repository;

import com.hackaboss.pruebatec4.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
