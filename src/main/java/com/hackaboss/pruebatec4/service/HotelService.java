package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.dto.HotelDTO;
import com.hackaboss.pruebatec4.model.Hotel;
import com.hackaboss.pruebatec4.model.Room;
import com.hackaboss.pruebatec4.repository.HotelRepository;
import com.hackaboss.pruebatec4.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService implements IHotelService{

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public void saveHotel(HotelDTO hotelDto) {

        Hotel newHotel = new Hotel();

        newHotel.setName(hotelDto.getName());
        newHotel.setCity(hotelDto.getCity());

        hotelRepository.save(newHotel);

        for (Room room : hotelDto.getRooms()) {
            room.setHotel(newHotel);
            roomRepository.save(room);
        }

    }

    @Override
    public void deleteHotel(Long id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){

            Hotel hotel = optionalHotel.get();

            for (Room room: hotel.getRooms()){
                roomRepository.deleteById(room.getId());
            }

            hotelRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Hotel no encontrado");
        }
    }

    @Override
    public Hotel findHotel(Long id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);

        if (optionalHotel.isPresent()){
            return optionalHotel.get();
        }else {
            throw new EntityNotFoundException("Hotel no encontrado");
        }

    }

    @Override
    public void editHotel(HotelDTO hotelDTO, Long id) {

        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){
            Hotel hotel = optionalHotel.get();

            if (hotelDTO.getName()!= null)
                hotel.setName(hotelDTO.getName());

            if (hotelDTO.getCity()!= null)
                hotel.setCity(hotelDTO.getCity());

            hotelRepository.save(hotel);

        }else {
            throw new EntityNotFoundException("Hotel no encontrado");
        }

    }
}
