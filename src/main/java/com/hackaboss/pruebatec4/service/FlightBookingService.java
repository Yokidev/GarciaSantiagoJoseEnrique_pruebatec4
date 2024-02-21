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
        Optional<Flight> optionalFlight = flightRepository.findByCode(flightBookingDTO.getCode());

        Flight flight = optionalFlight.orElseThrow(() -> new EntityNotFoundException("Vuelo no encontrado"));

        validateFlightDetails(flight, flightBookingDTO);

        int passengersCount = flightBookingDTO.getPassengers().size();

        if (flightHasEnoughSeats(flight, passengersCount)) {
            FlightBooking flightBooking = createFlightBooking(flight, flightBookingDTO);
            updateClientsAndPassengers(flightBookingDTO.getPassengers(), flightBooking);
            updateFlightSeatsBooked(flight, passengersCount);

            return passengersCount * flight.getPrice();
        } else {
            throw new FlightBookingDataException("El vuelo no admite tantos pasajeros");
        }
    }

    private void validateFlightDetails(Flight flight, FlightBookingDTO flightBookingDTO) throws FlightBookingDataException {
        if (!flight.getDestination().equals(flightBookingDTO.getDestination()) ||
                !flight.getOrigin().equals(flightBookingDTO.getOrigin()) ||
                !flight.getDate().equals(flightBookingDTO.getDate())) {
            throw new FlightBookingDataException("Los datos de la reserva no coinciden con los del vuelo");
        }
    }

    private boolean flightHasEnoughSeats(Flight flight, int passengersCount) {
        return flight.getTotalSeat() >= (flight.getBookedSeat() + passengersCount);
    }

    private FlightBooking createFlightBooking(Flight flight, FlightBookingDTO flightBookingDTO) {
        FlightBooking flightBooking = new FlightBooking();
        flightBooking.setFlight(flight);
        flightBooking.setNumberTickets(flightBookingDTO.getPassengers().size());
        flightBookingRepository.save(flightBooking);
        return flightBooking;
    }

    private void updateClientsAndPassengers(List<ClientDTO> passengers, FlightBooking flightBooking) {
        for (ClientDTO clientDTO : passengers) {
            Client client = clientRepository.findByIdentification(clientDTO.getIdentification())
                    .orElseGet(() -> createNewClient(clientDTO));

            client.addFlightBooking(flightBooking);
            flightBooking.addPassenger(client);

            clientRepository.save(client);
            flightBookingRepository.save(flightBooking);
        }
    }

    private Client createNewClient(ClientDTO clientDTO) {
        Client newClient = new Client();
        newClient.setName(clientDTO.getName());
        newClient.setSurname(clientDTO.getSurname());
        newClient.setBirthdate(clientDTO.getBirthdate());
        newClient.setIdentification(clientDTO.getIdentification());
        return newClient;
    }

    private void updateFlightSeatsBooked(Flight flight, int passengersCount) {
        int newFlightTotalBookedSeat = flight.getBookedSeat() + passengersCount;
        flight.setBookedSeat(newFlightTotalBookedSeat);
        flightRepository.save(flight);
    }

    @Override
    public void deleteFlightBooking(Long id) {
        FlightBooking flightBooking = flightBookingRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Reserva de vuelo no encontrada"));

        Flight flight = flightBooking.getFlight();
        int releasedSeats = flightBooking.getNumberTickets();

        releaseFlightSeats(flight, releasedSeats);

        flightBookingRepository.delete(flightBooking);
    }

    private void releaseFlightSeats(Flight flight, int releasedSeats) {
        int bookedSeats = flight.getBookedSeat();
        flight.setBookedSeat(bookedSeats - releasedSeats);
        flightRepository.save(flight);
    }

    @Override
    public FlightBooking findFlightBooking(Long id) {
        return flightBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva de vuelo no encontrada"));
    }

}
