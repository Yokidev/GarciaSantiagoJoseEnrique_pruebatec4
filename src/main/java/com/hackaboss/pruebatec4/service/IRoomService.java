package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.dto.HotelDTO;
import com.hackaboss.pruebatec4.dto.RoomDTO;
import com.hackaboss.pruebatec4.model.Room;

import java.util.List;

public interface IRoomService {

    public List<Room> getRooms();
    public void saveRoom(RoomDTO room);
    public void deleteRoom(Long id);
    public Room findRoom(Long id);
    public void editRoom(RoomDTO roomDTO, Long id);

}
