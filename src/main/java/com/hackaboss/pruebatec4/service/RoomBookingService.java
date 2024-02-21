package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.dto.ClientDTO;
import com.hackaboss.pruebatec4.dto.RoomBookingDTO;
import com.hackaboss.pruebatec4.exceptions.FlightBookingDataException;
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

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class RoomBookingService implements IRoomBookingService{

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    ClientRepository clientRepository;


    @Override
    public List<RoomBooking> getRoomBookings() {
        return roomBookingRepository.findAll();
    }


    @Override
    public Double saveRoomBooking(RoomBookingDTO roomBookingDTO) throws RoomBookingDataException {

        Long numNights = ChronoUnit.DAYS.between(roomBookingDTO.getCheckIn(),roomBookingDTO.getCheckOut());

        //Comprobamos si existe la habitación
        Optional<Room> optionalRoom = roomRepository.findById(roomBookingDTO.getIdRoom());
        if (optionalRoom.isPresent()){
            Room room = optionalRoom.get();

            //Comprobamos que los datos de la habitacion y la reserva son los mismos
            if (room.getHotel().getName().equals(roomBookingDTO.getHotelName()) &&
                room.getHotel().getCity().equals(roomBookingDTO.getCity())){

                //Comprobamos que el numero de huespedes no supera la capacidad de la habitacion
                if (roomBookingDTO.getHosts().size()<= room.getMaxCapacity()) {

                    //Comprobamos que la reserva nueva no se pisa con una ya existente
                    if (room.getRoomBookings().stream().anyMatch(
                            roomBooked -> (roomBookingDTO.getCheckIn().isBefore(roomBooked.getCheckOut())
                                    && roomBookingDTO.getCheckOut().isAfter(roomBooked.getCheckIn()))
                    )) {
                        throw new RoomBookingDataException("Las fechas elegidas para la reserva coinciden con una reserva ya realizada");

                    } else {

                        RoomBooking roomBooking = new RoomBooking();

                        String nameClient = roomBookingDTO.getHosts().stream()
                                .findFirst()
                                .get()
                                .getName();

                        roomBooking.setClientName(nameClient);
                        roomBooking.setRoom(room);
                        roomBooking.setCheckIn(roomBookingDTO.getCheckIn());
                        roomBooking.setCheckOut(roomBookingDTO.getCheckOut());

                        roomBookingRepository.save(roomBooking);

                        //Rellenamos la lista de clientes
                        for (ClientDTO clientDTO : roomBookingDTO.getHosts()) {
                            //si ya existen les añadimos la reserva
                            Optional<Client> optionalClient = clientRepository.findByIdentification(clientDTO.getIdentification());
                            if (optionalClient.isPresent()) {
                                Client client = optionalClient.get();
                                client.addRoomBooking(roomBooking);
                                clientRepository.save(client);

                                //añadimos el huésped a la reserva
                                roomBooking.addHosts(client);
                                roomBookingRepository.save(roomBooking);

                            } else {
                                // si no existe el cliente lo creamos
                                Client newClient = new Client();
                                newClient.setName(clientDTO.getName());
                                newClient.setSurname(clientDTO.getSurname());
                                newClient.setBirthdate(clientDTO.getBirthdate());
                                newClient.setIdentification(clientDTO.getIdentification());
                                newClient.addRoomBooking(roomBooking);

                                clientRepository.save(newClient);

                                //añadimos el huésped a la reserva
                                roomBooking.addHosts(newClient);
                                roomBookingRepository.save(roomBooking);

                            }

                        }

                    }
                }else {
                    throw new RoomBookingDataException("El numero de huespedes supera la capacidad de la habitación");
                }
            }else {
                throw new RoomBookingDataException("Los datos de la reserva no coinciden con los de la habitacion");
            }

        }else {
            throw new EntityNotFoundException("Habitacion no encontrada");
        }

        return numNights*optionalRoom.get().getPrice();
    }

    @Override
    public void deleteRoomBooking(Long id) {
        RoomBooking roomBooking = roomBookingRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Reserva de habitacion no encontrada"));
        roomBookingRepository.delete(roomBooking);

    }

    @Override
    public RoomBooking findRoomBooking(Long id) {
        return roomBookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reserva de habitacion no encontrada"));
    }

}
