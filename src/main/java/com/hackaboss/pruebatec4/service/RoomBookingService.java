package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.dto.ClientDTO;
import com.hackaboss.pruebatec4.dto.RoomBookingDTO;
import com.hackaboss.pruebatec4.exceptions.RoomBookingDataException;
import com.hackaboss.pruebatec4.model.Client;
import com.hackaboss.pruebatec4.model.Room;
import com.hackaboss.pruebatec4.model.RoomBooking;
import com.hackaboss.pruebatec4.repository.ClientRepository;
import com.hackaboss.pruebatec4.repository.RoomBookingRepository;
import com.hackaboss.pruebatec4.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RoomBookingService implements IRoomBookingService{

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    ClientRepository clientRepository;


    /***
     * Devuelve la lista de reservas
     * @return
     */
    @Override
    public List<RoomBooking> getRoomBookings() {
        return roomBookingRepository.findAll();
    }

    /***
     * Devuelve el costo de la reserva
     * @param roomBookingDTO
     * @return
     * @throws RoomBookingDataException
     */
    @Override
    public Double saveRoomBooking(RoomBookingDTO roomBookingDTO) throws RoomBookingDataException {

        Long numNights = ChronoUnit.DAYS.between(roomBookingDTO.getCheckIn(), roomBookingDTO.getCheckOut());

        Room room = roomRepository.findById(roomBookingDTO.getIdRoom()).orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada"));

        validateRoomBookingDates(roomBookingDTO);
        validateRoomDetails(room, roomBookingDTO);

        if (roomHasEnoughCapacity(room, roomBookingDTO.getHosts().size())) {
            if (isBookingDateAvailable(room, roomBookingDTO.getCheckIn(), roomBookingDTO.getCheckOut())) {
                RoomBooking roomBooking = createRoomBooking(room, roomBookingDTO);
                updateClientsAndHosts(roomBookingDTO.getHosts(), roomBooking);
                return numNights * room.getPrice();
            } else {
                throw new RoomBookingDataException("Las fechas elegidas para la reserva coinciden con una reserva ya realizada");
            }
        } else {
            throw new RoomBookingDataException("El numero de huespedes supera la capacidad de la habitación");
        }
    }

    /***
     * Valida que las fechas esten en orden correcto
     * @param roomBookingDTO
     * @throws RoomBookingDataException
     */
    private void validateRoomBookingDates(RoomBookingDTO roomBookingDTO) throws RoomBookingDataException {
        if (roomBookingDTO.getCheckIn().isAfter(roomBookingDTO.getCheckOut())) {
            throw new RoomBookingDataException("La fecha de check-in no puede ser posterior a la fecha de check-out");
        }
    }

    /***
     * Valida que los datos proporcionados coincidan con la habitacion elegida
     * @param room
     * @param roomBookingDTO
     * @throws RoomBookingDataException
     */
    private void validateRoomDetails(Room room, RoomBookingDTO roomBookingDTO) throws RoomBookingDataException {
        if (!room.getHotel().getName().equals(roomBookingDTO.getHotelName()) ||
                !room.getHotel().getCity().equals(roomBookingDTO.getCity())) {
            throw new RoomBookingDataException("Los datos de la reserva no coinciden con los de la habitacion");
        }
    }

    /***
     * Valida que el tamaño de la habitacion sea adecuado para el numero de huespedes
     * @param room
     * @param numHosts
     * @return
     */
    private boolean roomHasEnoughCapacity(Room room, int numHosts) {
        return numHosts <= room.getMaxCapacity();
    }

    /***
     * Valida que la habitacion este disponible en las fechas elegidas
     * @param room
     * @param checkIn
     * @param checkOut
     * @return
     */
    private boolean isBookingDateAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        return room.getRoomBookings().stream().noneMatch(
                roomBooking -> checkIn.isBefore(roomBooking.getCheckOut()) && checkOut.isAfter(roomBooking.getCheckIn())
        );
    }

    /****
     * Crea la reserva
     * @param room
     * @param roomBookingDTO
     * @return
     */
    private RoomBooking createRoomBooking(Room room, RoomBookingDTO roomBookingDTO) {
        RoomBooking roomBooking = new RoomBooking();
        roomBooking.setClientName(roomBookingDTO.getHosts().get(0).getName());
        roomBooking.setRoom(room);
        roomBooking.setCheckIn(roomBookingDTO.getCheckIn());
        roomBooking.setCheckOut(roomBookingDTO.getCheckOut());
        roomBookingRepository.save(roomBooking);
        return roomBooking;
    }

    /***
     * Actualiza los datos de los clientes y la lista de huespedes
     * @param hosts
     * @param roomBooking
     */
    private void updateClientsAndHosts(List<ClientDTO> hosts, RoomBooking roomBooking) {
        for (ClientDTO clientDTO : hosts) {
            Client client = clientRepository.findByIdentification(clientDTO.getIdentification())
                    .orElseGet(() -> createNewClient(clientDTO));

            client.addRoomBooking(roomBooking);
            roomBooking.addHosts(client);

            clientRepository.save(client);
            roomBookingRepository.save(roomBooking);
        }
    }

    /***
     * Crea un nuevo cliente
     * @param clientDTO
     * @return
     */
    private Client createNewClient(ClientDTO clientDTO) {
        Client newClient = new Client();
        newClient.setName(clientDTO.getName());
        newClient.setSurname(clientDTO.getSurname());
        newClient.setBirthdate(clientDTO.getBirthdate());
        newClient.setIdentification(clientDTO.getIdentification());
        return newClient;
    }

    /***
     * Borra la reserva que coincida con el id proporcionado
     * @param id
     */
    @Override
    public void deleteRoomBooking(Long id) {
        RoomBooking roomBooking = roomBookingRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Reserva de habitacion no encontrada"));
        roomBookingRepository.delete(roomBooking);

    }

    /***
     * Devuelve una reserva que coincida con la id proporcionada
     * @param id
     * @return
     */
    @Override
    public RoomBooking findRoomBooking(Long id) {
        return roomBookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reserva de habitacion no encontrada"));
    }

}
