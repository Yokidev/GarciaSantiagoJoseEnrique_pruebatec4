package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.dto.HotelDTO;
import com.hackaboss.pruebatec4.dto.RoomDTO;
import com.hackaboss.pruebatec4.exceptions.RoomBookingDataException;
import com.hackaboss.pruebatec4.model.Hotel;
import com.hackaboss.pruebatec4.model.Room;
import com.hackaboss.pruebatec4.repository.HotelRepository;
import com.hackaboss.pruebatec4.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService implements IHotelService{

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

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

        for (RoomDTO roomDto : hotelDto.getRooms()) {
            roomDto.setIdHotel(newHotel.getId());

            roomService.saveRoom(roomDto);
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

    @Override
    public List<HotelDTO> findHotelsByCityAndDateBetween(String city, LocalDate dateFrom, LocalDate dateTo) {

        List<Hotel> listHotel = hotelRepository.findByCity(city);

        List<HotelDTO> listHotelDTO = new ArrayList<>();

        if (listHotel.isEmpty())
            throw new EntityNotFoundException("No hay hoteles que coincidan con la busqueda");

        for (Hotel hotel:listHotel){

            HotelDTO hotelDTO = new HotelDTO();

            hotelDTO.setName(hotel.getName());
            hotelDTO.setCity(hotel.getCity());
            hotelDTO.setRooms(new ArrayList<>());

            for (Room room: hotel.getRooms()) {
                if (room.getRoomBookings().stream().anyMatch(roomBooked -> (dateFrom.isBefore(roomBooked.getCheckOut()) && dateTo.isAfter(roomBooked.getCheckIn()))) || !room.getAvailable())
                    continue;

                RoomDTO roomDTO = new RoomDTO();

                roomDTO.setIdHotel(hotel.getId());
                roomDTO.setRoomType(room.getRoomType());
                roomDTO.setMaxCapacity(room.getMaxCapacity());
                roomDTO.setPrice(room.getPrice());


                hotelDTO.getRooms().add(roomDTO);
            }

            listHotelDTO.add(hotelDTO);
        }

        return listHotelDTO;
    }
}











