package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.model.Room;
import com.hackaboss.pruebatec4.model.RoomBooking;
import com.hackaboss.pruebatec4.repository.RoomBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomBookingService implements IRoomBookingService{

    @Autowired
    private RoomBookingRepository roomBookingRepository;


    @Override
    public List<RoomBooking> getRoomBooking() {
        return roomBookingRepository.findAll();
    }

    @Override
    public void saveRoomBooking(RoomBooking roomBooking) {
        roomBookingRepository.save(roomBooking);
    }

    @Override
    public void deleteRoomBooking(Long id) {
        roomBookingRepository.deleteById(id);
    }

    @Override
    public RoomBooking findRoomBooking(Long id) {
        return roomBookingRepository.findById(id).orElse(null);
    }
}
