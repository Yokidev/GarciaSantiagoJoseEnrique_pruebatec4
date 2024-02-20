package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.dto.ClientDTO;
import com.hackaboss.pruebatec4.dto.FlightBookingDTO;
import com.hackaboss.pruebatec4.exceptions.FlightBookingDataException;
import com.hackaboss.pruebatec4.model.Client;
import com.hackaboss.pruebatec4.model.Flight;
import com.hackaboss.pruebatec4.model.FlightBooking;
import com.hackaboss.pruebatec4.repository.ClientRepository;
import com.hackaboss.pruebatec4.repository.FlightBookingRepository;
import com.hackaboss.pruebatec4.repository.FlightRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightBookingService implements IFlightBookingService{

    @Autowired
    FlightBookingRepository flightBookingRepository;

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<FlightBooking> getFlightBookings() {
        return flightBookingRepository.findAll();
    }

    @Override
    public Double saveFlightBooking(FlightBookingDTO flightBookingDTO) throws FlightBookingDataException {

        //Comprobamos si existe el vuelo
        Optional<Flight> optionalFlight = flightRepository.findByCode(flightBookingDTO.getCode());
        if (optionalFlight.isPresent()){
            Flight flight = optionalFlight.get();
            //Comprobamos que los datos del vuelo y la reserva son los mismos
            if (flight.getDestination().equals(flightBookingDTO.getDestination()) &&
                flight.getOrigin().equals(flightBookingDTO.getOrigin()) &&
                flight.getDate().equals(flightBookingDTO.getDate())
                ) {
                //Si existe comprobamos si ha asientos suficientes para la reserva
                if (flight.getTotalSeat() > (flight.getBookedSeat() + flightBookingDTO.getPassengers().size())) {
                    FlightBooking flightBooking = new FlightBooking();

                    String nameClient = flightBookingDTO.getPassengers()
                            .stream()
                            .findFirst()
                            .get().getName();

                    flightBooking.setFlight(flight);
                    flightBooking.setNameClient(nameClient);
                    flightBooking.setNumberTickets(flightBookingDTO.getPassengers().size());


                    flightBookingRepository.save(flightBooking);

                    //Actualizamos el numero de asientos
                    int newFlightTotalBookedSeat = flight.getBookedSeat() + flightBookingDTO.getPassengers().size();
                    flight.setBookedSeat(newFlightTotalBookedSeat);

                    flightRepository.save(flight);

                    //Rellenamos la lista de clientes
                    for (ClientDTO clientDTO : flightBookingDTO.getPassengers()) {

                        //si ya existen les añadimos la reserva
                        Optional<Client> optionalClient = clientRepository.findByIdentification(clientDTO.getIdentification());
                        if (optionalClient.isPresent()) {
                            Client client = optionalClient.get();
                            client.addFlightBooking(flightBooking);
                            clientRepository.save(client);

                            //añadimos el pasajero a la reserva
                            flightBooking.addPassenger(client);
                            flightBookingRepository.save(flightBooking);

                        } else {
                            // si no existe el cliente lo creamos
                            Client newClient = new Client();
                            newClient.setName(clientDTO.getName());
                            newClient.setSurname(clientDTO.getSurname());
                            newClient.setBirthdate(clientDTO.getBirthdate());
                            newClient.setIdentification(clientDTO.getIdentification());
                            newClient.addFlightBooking(flightBooking);

                            clientRepository.save(newClient);

                            //añadimos el pasajero a la reserva
                            flightBooking.addPassenger(newClient);
                            flightBookingRepository.save(flightBooking);

                        }

                    }

                } else {
                    throw new FlightBookingDataException("El vuelo no admite tantos pasajeros");
                }
            }else {
                throw new FlightBookingDataException("Los datos de la reserva no coinciden con los del vuelo");
            }

        }else {
            throw new EntityNotFoundException("Vuelo no encontrado");
        }

        return flightBookingDTO.getPassengers().size()*optionalFlight.get().getPrice();
    }

    @Override
    public void deleteFlightBooking(Long id) {
        Optional<FlightBooking> optionalFlightBooking = flightBookingRepository.findById(id);
        if (optionalFlightBooking.isPresent()){
            FlightBooking flightBooking = optionalFlightBooking.get();

            //Liberamos los asientos de la reserva que se va a eliminar
            Flight flight = flightBooking.getFlight();
            Integer bookedSeats = flight.getBookedSeat();
            Integer releasedSeats = flightBooking.getNumberTickets();

            flight.setBookedSeat(bookedSeats-releasedSeats);

            flightRepository.save(flight);

            flightBookingRepository.delete(flightBooking);

        } else {
            throw new EntityNotFoundException("Reserva de vuelo no encontrada");
        }
    }

    @Override
    public FlightBooking findFlightBooking(Long id) {

        Optional<FlightBooking> optionalFlightBooking = flightBookingRepository.findById(id);
        if (optionalFlightBooking.isPresent()){
            return optionalFlightBooking.get();
        }else {
            throw new EntityNotFoundException("Reserva de vuelo no encontrada");
        }

    }

}
