package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.dto.RoomDTO;
import com.hackaboss.pruebatec4.model.FlightBooking;
import com.hackaboss.pruebatec4.model.Room;
import com.hackaboss.pruebatec4.service.IRoomService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    /***
     * Devuelve la lista de habitaciones
     * @return
     */
    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getRooms(){
        List<Room> roomList = roomService.getRooms();
        if (roomList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }

    /***
     * Devuelve la habitacion que coincida con el id proporcionado
     * @param id
     * @return
     */
    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id){
        try {
            Room room = roomService.findRoom(id);
            return new ResponseEntity<>(room, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /***
     * Crea la habitacion con los datos proporcionados
     * @param roomDTO
     * @return
     */
    @PostMapping("/rooms/new")
    public ResponseEntity<String> createRoom(@RequestBody RoomDTO roomDTO){

        try {
            roomService.saveRoom(roomDTO);
            return  new ResponseEntity<>("Habitacion creada", HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /***
     * Modifica los parametros de una habitacion existente
     * @param id
     * @param roomDTO
     * @return
     */
    @PutMapping("/rooms/edit/{id}")
    public ResponseEntity<String> editRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO){

        try {
            roomService.editRoom(roomDTO, id);
            return new ResponseEntity<>("Habitacion editada", HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    /***
     * Borra la habitacion que coincida con la id proporcionada
     * @param id
     * @return
     */
    @DeleteMapping("/rooms/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id){

        try {
            roomService.deleteRoom(id);
            return new ResponseEntity<>("Habitacion borrada", HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>("No se puede eliminar la habitacion porque tiene reservas asociadas.", HttpStatus.BAD_REQUEST);
        }
    }

}
