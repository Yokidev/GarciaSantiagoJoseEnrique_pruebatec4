package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.dto.RoomBookingDTO;
import com.hackaboss.pruebatec4.exceptions.RoomBookingDataException;
import com.hackaboss.pruebatec4.model.Room;
import com.hackaboss.pruebatec4.model.RoomBooking;

import java.util.List;

public interface IRoomBookingService {

    List<RoomBooking> getRoomBookings();
    Double saveRoomBooking(RoomBookingDTO roomBooking) throws RoomBookingDataException;
    void deleteRoomBooking(Long id);
    RoomBooking findRoomBooking(Long id);


}
