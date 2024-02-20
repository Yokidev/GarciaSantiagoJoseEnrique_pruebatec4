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

    @Override
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @Override
    public void saveRoom(RoomDTO roomDto) {

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getIdHotel());
        if (optionalHotel.isPresent()){

            Hotel hotel = optionalHotel.get();

            Room newRoom = new Room();

            newRoom.setHotel(hotel);
            newRoom.setRoomType(roomDto.getRoomType());
            newRoom.setMaxCapacity(roomDto.getMaxCapacity());
            newRoom.setPrice(roomDto.getPrice());
            newRoom.setAvailable(roomDto.getAvailable());

            roomRepository.save(newRoom);
        }else {
            throw new EntityNotFoundException("Hotel no encontrado");
        }

    }

    @Override
    public void deleteRoom(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()){
            Room room = optionalRoom.get();

            for (RoomBooking roomBooking: room.getRoomBookings()){
                roomBookingRepository.deleteById(roomBooking.getId());
            }

            roomRepository.deleteById(id);

        }else {
            throw new EntityNotFoundException("Habitacion no encontrada");
        }
    }

    @Override
    public Room findRoom(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if (optionalRoom.isPresent()){
            return optionalRoom.get();
        }else {
            throw new EntityNotFoundException("Habitacion no encontrada");
        }
    }

    @Override
    public void editRoom(RoomDTO roomDTO, Long id) {

        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()){

            Room room = optionalRoom.get();

            if (roomDTO.getRoomType()!= null)
                room.setRoomType(roomDTO.getRoomType());

            if (roomDTO.getMaxCapacity()!= null)
                room.setMaxCapacity(roomDTO.getMaxCapacity());

            if (roomDTO.getPrice()!= null)
                room.setPrice(roomDTO.getPrice());

            if (roomDTO.getAvailable()!= null)
                room.setAvailable(roomDTO.getAvailable());

            roomRepository.save(room);

        }else {
            throw new EntityNotFoundException("Habitacion no encontrada");
        }

    }


}
