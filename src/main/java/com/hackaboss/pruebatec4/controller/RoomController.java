package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.dto.RoomDTO;
import com.hackaboss.pruebatec4.model.FlightBooking;
import com.hackaboss.pruebatec4.model.Room;
import com.hackaboss.pruebatec4.service.IRoomService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getRooms(){
        List<Room> roomList = roomService.getRooms();
        if (roomList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id){
        try {
            Room room = roomService.findRoom(id);
            return new ResponseEntity<>(room, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/rooms/new")
    public ResponseEntity<String> createRoom(@RequestBody RoomDTO room){

        try {
            roomService.saveRoom(room);
            return  new ResponseEntity<>("Habitacion creada", HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/rooms/edit/{id}")
    public ResponseEntity<String> editRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO){

        try {
            roomService.editRoom(roomDTO, id);
            return new ResponseEntity<>("Habitacion editada", HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/rooms/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id){

        try {
            roomService.deleteRoom(id);
            return new ResponseEntity<>("Habitacion borrada", HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



}
