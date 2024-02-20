package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.dto.RoomBookingDTO;
import com.hackaboss.pruebatec4.exceptions.FlightBookingDataException;
import com.hackaboss.pruebatec4.exceptions.RoomBookingDataException;
import com.hackaboss.pruebatec4.model.RoomBooking;
import com.hackaboss.pruebatec4.service.IRoomBookingService;
import com.hackaboss.pruebatec4.service.RoomService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class RoomBookingController {

    @Autowired
    private IRoomBookingService roomBookingService;

    @GetMapping("/room-bookings")
    public ResponseEntity<List<RoomBooking>> getRoomBookings(){
        List<RoomBooking> roomBookingList = roomBookingService.getRoomBookings();
        if (roomBookingList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roomBookingList, HttpStatus.OK);
    }

    @GetMapping("/room-booking/{id}")
    public ResponseEntity<RoomBooking> getRoomBookingById(@PathVariable Long id){
        try {
            RoomBooking roomBooking = roomBookingService.findRoomBooking(id);
            return new ResponseEntity<>(roomBooking, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/room-booking/new")
    public ResponseEntity<String> createRoomBooking(@RequestBody RoomBookingDTO roomBookingDTO) throws RoomBookingDataException {
        try {
            double price = roomBookingService.saveRoomBooking(roomBookingDTO);
            String message = "El precio de la reserva es: " + price + "€.";
            return  new ResponseEntity<>(message, HttpStatus.CREATED);

        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RoomBookingDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/room-booking/delete/{id}")
    public ResponseEntity<String> deleteRoomBooking(@PathVariable Long id){

        try {
            roomBookingService.deleteRoomBooking(id);
            return new ResponseEntity<>("Reserva de habitacion borrada", HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


}
