package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.dto.RoomDTO;
import com.hackaboss.pruebatec4.model.Hotel;
import com.hackaboss.pruebatec4.model.Room;
import com.hackaboss.pruebatec4.model.RoomBooking;
import com.hackaboss.pruebatec4.repository.HotelRepository;
import com.hackaboss.pruebatec4.repository.RoomBookingRepository;
import com.hackaboss.pruebatec4.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements IRoomService{

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    /***
     * Devuelve la lista de habitaciones de la BBDD
     * @return
     */
    @Override
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    /***
     * Guarda una habitacion en la BBDD
     * @param roomDto
     */
    @Override
    public void saveRoom(RoomDTO roomDto) {

        Hotel hotel = hotelRepository.findById(roomDto.getIdHotel()).orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));

        Room newRoom = new Room();

        newRoom.setHotel(hotel);
        newRoom.setRoomType(roomDto.getRoomType());
        newRoom.setMaxCapacity(roomDto.getMaxCapacity());
        newRoom.setPrice(roomDto.getPrice());
        newRoom.setAvailable(roomDto.getAvailable());

        roomRepository.save(newRoom);

    }

    /***
     * Borra una habitacion que coincida con la id proporcionada
     * @param id
     */
    @Override
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada"));

        roomRepository.deleteById(id);

    }

    /***
     * Devuelve una habitacion que coincida con la id proporcionada
     * @param id
     * @return
     */
    @Override
    public Room findRoom(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada"));
    }

    /***
     * Edita la habitacion con la informacion proporcionada
     * @param roomDTO
     * @param id
     */
    @Override
    public void editRoom(RoomDTO roomDTO, Long id) {

        Room room = roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada"));

        if (roomDTO.getRoomType()!= null)
            room.setRoomType(roomDTO.getRoomType());

        if (roomDTO.getMaxCapacity()!= null)
            room.setMaxCapacity(roomDTO.getMaxCapacity());

        if (roomDTO.getPrice()!= null)
            room.setPrice(roomDTO.getPrice());

        if (roomDTO.getAvailable()!= null)
            room.setAvailable(roomDTO.getAvailable());

        roomRepository.save(room);

    }


}
