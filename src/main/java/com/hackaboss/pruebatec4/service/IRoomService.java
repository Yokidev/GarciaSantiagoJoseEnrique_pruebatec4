package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.dto.HotelDTO;
import com.hackaboss.pruebatec4.dto.RoomDTO;
import com.hackaboss.pruebatec4.model.Room;

import java.util.List;

public interface IRoomService {

    List<Room> getRooms();
    void saveRoom(RoomDTO room);
    void deleteRoom(Long id);
    Room findRoom(Long id);
    void editRoom(RoomDTO roomDTO, Long id);

}
