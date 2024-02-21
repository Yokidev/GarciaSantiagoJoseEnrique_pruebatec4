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

    /***
     * Devuelve la lista de reservas
     * @return
     */
    @Override
    public List<FlightBooking> getFlightBookings() {
        return flightBookingRepository.findAll();
    }

    /***
     * Devuelve el costo de la reserva
     * @param flightBookingDTO
     * @return
     * @throws FlightBookingDataException
     */
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

    /***
     * Valida los datos proporcionados para que coincidan con el vuelo elegido
     * @param flight
     * @param flightBookingDTO
     * @throws FlightBookingDataException
     */
    private void validateFlightDetails(Flight flight, FlightBookingDTO flightBookingDTO) throws FlightBookingDataException {
        if (!flight.getDestination().equals(flightBookingDTO.getDestination()) ||
                !flight.getOrigin().equals(flightBookingDTO.getOrigin()) ||
                !flight.getDate().equals(flightBookingDTO.getDate())) {
            throw new FlightBookingDataException("Los datos de la reserva no coinciden con los del vuelo");
        }
    }

    /***
     * Comprueba que haya asientos disponibles para realizar la reserva
     * @param flight
     * @param passengersCount
     * @return
     */
    private boolean flightHasEnoughSeats(Flight flight, int passengersCount) {
        return flight.getTotalSeat() >= (flight.getBookedSeat() + passengersCount);
    }

    /***
     * Crea una reserva de vuelo
     * @param flight
     * @param flightBookingDTO
     * @return
     */
    private FlightBooking createFlightBooking(Flight flight, FlightBookingDTO flightBookingDTO) {
        FlightBooking flightBooking = new FlightBooking();
        flightBooking.setFlight(flight);
        flightBooking.setNumberTickets(flightBookingDTO.getPassengers().size());
        flightBookingRepository.save(flightBooking);
        return flightBooking;
    }

    /***
     * Actualiza los pasageros y el cliente de una reserva
     * @param passengers
     * @param flightBooking
     */
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
     * Actualiza los asientos disponbles de un vuelo
     * @param flight
     * @param passengersCount
     */
    private void updateFlightSeatsBooked(Flight flight, int passengersCount) {
        int newFlightTotalBookedSeat = flight.getBookedSeat() + passengersCount;
        flight.setBookedSeat(newFlightTotalBookedSeat);
        flightRepository.save(flight);
    }

    /***
     * Borra una reserva que coincida con el id proporcionado
     * @param id
     */
    @Override
    public void deleteFlightBooking(Long id) {
        FlightBooking flightBooking = flightBookingRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Reserva de vuelo no encontrada"));

        Flight flight = flightBooking.getFlight();
        int releasedSeats = flightBooking.getNumberTickets();

        releaseFlightSeats(flight, releasedSeats);

        flightBookingRepository.delete(flightBooking);
    }

    /***
     * Libera los asientos que estaban ocupados por la reserva eliminada
     * @param flight
     * @param releasedSeats
     */
    private void releaseFlightSeats(Flight flight, int releasedSeats) {
        int bookedSeats = flight.getBookedSeat();
        flight.setBookedSeat(bookedSeats - releasedSeats);
        flightRepository.save(flight);
    }

    /***
     * Devuelve una reserva que coincida con la id proporcionada
     * @param id
     * @return
     */
    @Override
    public FlightBooking findFlightBooking(Long id) {
        return flightBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva de vuelo no encontrada"));
    }

}
