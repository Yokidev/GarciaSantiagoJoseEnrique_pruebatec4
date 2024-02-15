package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.model.Room;
import com.hackaboss.pruebatec4.model.RoomBooking;

import java.util.List;

public interface IRoomBookingService {

    public List<RoomBooking> getRoomBooking();
    public void saveRoomBooking(RoomBooking roomBooking);
    public void deleteRoomBooking(Long id);
    public RoomBooking findRoomBooking(Long id);


}
