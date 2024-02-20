package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.dto.RoomBookingDTO;
import com.hackaboss.pruebatec4.exceptions.RoomBookingDataException;
import com.hackaboss.pruebatec4.model.RoomBooking;
import com.hackaboss.pruebatec4.service.IRoomBookingService;
import com.hackaboss.pruebatec4.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class RoomBookingController {

    @Autowired
    private IRoomBookingService roomBookingService;

    @GetMapping("/room-bookings")
    public List<RoomBooking> getRoomBookings(){
        return roomBookingService.getRoomBookings();
    }

    @GetMapping("/room-booking/{id}")
    public RoomBooking getRoomBookingById(@PathVariable Long id){
        return roomBookingService.findRoomBooking(id);
    }

    @PostMapping("/room-booking/new")
    public String createRoomBooking(@RequestBody RoomBookingDTO roomBookingDTO) throws RoomBookingDataException {
        return "El precio de la reserva es: " + roomBookingService.saveRoomBooking(roomBookingDTO) + "€.";
    }


    @DeleteMapping("/room-booking/delete/{id}")
    public String deleteRoomBooking(@PathVariable Long id){
        roomBookingService.deleteRoomBooking(id);
        return "Reserva de habitacion borrada";
    }


}
