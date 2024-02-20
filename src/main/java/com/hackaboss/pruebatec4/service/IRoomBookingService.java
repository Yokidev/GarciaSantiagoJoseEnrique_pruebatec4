package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.dto.RoomBookingDTO;
import com.hackaboss.pruebatec4.exceptions.RoomBookingDataException;
import com.hackaboss.pruebatec4.model.Room;
import com.hackaboss.pruebatec4.model.RoomBooking;

import java.util.List;

public interface IRoomBookingService {

    public List<RoomBooking> getRoomBookings();
    public Double saveRoomBooking(RoomBookingDTO roomBooking) throws RoomBookingDataException;
    public void deleteRoomBooking(Long id);
    public RoomBooking findRoomBooking(Long id);


}
