package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.dto.RoomDTO;
import com.hackaboss.pruebatec4.model.Room;
import com.hackaboss.pruebatec4.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @GetMapping("/rooms")
    public List<Room> getRooms(){
        return roomService.getRooms();
    }

    @GetMapping("/rooms/{id}")
    public Room getRoomById(@PathVariable Long id){
        return roomService.findRoom(id);
    }

    @PostMapping("/rooms/new")
    public String createRoom(@RequestBody RoomDTO room){

        roomService.saveRoom(room);
        return "Habitacion creada";
    }

    @PutMapping("/rooms/edit/{id}")
    public String editRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO){
        roomService.editRoom(roomDTO, id);

        return "Habitacion editada";
    }

    @DeleteMapping("/rooms/delete/{id}")
    public String deleteRoom(@PathVariable Long id){
        roomService.deleteRoom(id);

        return "Habitacion borrada";
    }



}
