package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.model.Room;

import java.util.List;

public interface IRoomService {

    public List<Room> getRooms();
    public void saveRoom(Room room);
    public void deleteRoom(Long id);
    public Room findRoom(Long id);

}
