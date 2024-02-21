package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.dto.HotelDTO;
import com.hackaboss.pruebatec4.dto.RoomDTO;
import com.hackaboss.pruebatec4.exceptions.HotelExistException;
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

@Service
public class HotelService implements IHotelService{

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    /***
     * Devuelve la lista de hoteles de la BBDD
     * @return
     */
    @Override
    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    /***
     * Guarda un hotel en la BBDD
     * @param hotelDto
     * @throws HotelExistException
     */
    @Override
    public void saveHotel(HotelDTO hotelDto) throws HotelExistException {

        if (AlreadyExist(hotelDto))
            throw new HotelExistException("Hotel ya existente");

        Hotel newHotel = new Hotel();

        newHotel.setName(hotelDto.getName());
        newHotel.setCity(hotelDto.getCity());

        hotelRepository.save(newHotel);

        for (RoomDTO roomDto : hotelDto.getRooms()) {
            roomDto.setIdHotel(newHotel.getId());

            roomService.saveRoom(roomDto);
        }

    }

    /***
     * Comprueba si un hotel existe en la BBDD
     * @param hotelDto
     * @return
     */
    private boolean AlreadyExist(HotelDTO hotelDto) {
        Hotel hotel = hotelRepository.findByNameAndCity(hotelDto.getName(), hotelDto.getCity());
        if (hotel == null){
            return false;
        }
        return true;
    }

    /***
     * Borra un hotel que coincida con la id proporcionada
     * @param id
     */
    @Override
    public void deleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));

        for (Room room : hotel.getRooms()) {
            roomRepository.deleteById(room.getId());
        }

        hotelRepository.deleteById(id);
    }

    /***
     * Devuelve un hotel que coincida con la id proporcionada
     * @param id
     * @return
     */
    @Override
    public Hotel findHotel(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));
    }

    /***
     * Edita el hotel con la informacion proporcionada
     * @param hotelDTO
     * @param id
     */
    @Override
    public void editHotel(HotelDTO hotelDTO, Long id) {

        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));

        if (hotelDTO.getName()!= null)
            hotel.setName(hotelDTO.getName());

        if (hotelDTO.getCity()!= null)
            hotel.setCity(hotelDTO.getCity());

        hotelRepository.save(hotel);

    }

    /***
     * Devuelve una lista de hoteles que coincidan con los parametros de busqueda
     * @param city
     * @param dateFrom
     * @param dateTo
     * @return
     */
    @Override
    public List<HotelDTO> findHotelsByCityAndDateBetween(String city, LocalDate dateFrom, LocalDate dateTo) {

        List<Hotel> listHotel = hotelRepository.findByCity(city);

        List<HotelDTO> listHotelDTO = new ArrayList<>();

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
                roomDTO.setAvailable(room.getAvailable());


                hotelDTO.getRooms().add(roomDTO);
            }

            listHotelDTO.add(hotelDTO);
        }

        return listHotelDTO;
    }
}











