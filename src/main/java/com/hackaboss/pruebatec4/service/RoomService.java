package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.model.Room;
import com.hackaboss.pruebatec4.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoomService implements IRoomService{

    @Autowired
    RoomRepository roomRepository;

    @Override
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @Override
    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Room findRoom(Long id) {
        return roomRepository.findById(id).orElse(null);
    }
}
