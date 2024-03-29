package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.dto.FlightDTO;
import com.hackaboss.pruebatec4.model.Flight;
import com.hackaboss.pruebatec4.repository.FlightRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class FlightService implements IFlightService{

    @Autowired
    FlightRepository flightRepository;

    /***
     * Devuelve la lista de vuelos de la BBDD
     * @return
     */
    @Override
    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }

    /***
     * Guarda un vuelo en la BBDD
     * @param flightDto
     */
    @Override
    public void saveFlight(FlightDTO flightDto) {
        Flight newFlight = new Flight();

        newFlight.setOrigin(flightDto.getOrigin());
        newFlight.setDestination(flightDto.getDestination());
        newFlight.setCode(codeGenerator(flightDto.getOrigin(),flightDto.getDestination()));
        newFlight.setDate(flightDto.getDate());
        newFlight.setPrice(flightDto.getPrice());
        newFlight.setTypeSeat(flightDto.getTypeSeat());
        newFlight.setTotalSeat(flightDto.getTotalSeat());
        newFlight.setBookedSeat(flightDto.getBookedSeat());

        flightRepository.save(newFlight);
    }

    /***
     * Borra un vuelo que coincida con la id proporcionada
     * @param id
     */
    @Override
    public void deleteFlight(Long id) {

        Flight flight = flightRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Vuelo no encontrado"));

        flightRepository.delete(flight);

    }

    /***
     * Devuelve un vuelo que coincida con la id proporcionada
     * @param id
     * @return
     */
    @Override
    public Flight findFlight(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vuelo no encontrado"));
    }

    /***
     * Edita el vuelo con la informacion proporcionada
     * @param flightDto
     * @param id
     */
    @Override
    public void editFlight(FlightDTO flightDto, Long id) {

        Flight flight = flightRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Vuelo no encontrado"));

        if (flightDto.getOrigin()!=null)
            flight.setOrigin(flightDto.getOrigin());

        if (flightDto.getDestination()!=null)
            flight.setDestination(flightDto.getDestination());

        if (flightDto.getDate()!=null)
            flight.setDate(flightDto.getDate());

        if (flightDto.getPrice()!=null)
            flight.setPrice(flightDto.getPrice());

        if (flightDto.getTypeSeat()!=null)
            flight.setTypeSeat(flightDto.getTypeSeat());

        if (flightDto.getTotalSeat()!=null)
            flight.setTotalSeat(flightDto.getTotalSeat());

        if (flightDto.getBookedSeat()!=null)
            flight.setBookedSeat(flightDto.getBookedSeat());

        flightRepository.save(flight);

    }

    /***
     * Devuelve una lista de vuelos que coincidan con los parametros de busqueda
     * @param origin
     * @param destination
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<Flight> findByOriginAndDestinationAndDateBetween(String origin, String destination, LocalDate startDate, LocalDate endDate) {
         return flightRepository.findByOriginAndDestinationAndDateBetween(origin, destination, startDate, endDate);
    }

    /***
     * Metodo usado para generar los codigos de los Vuelos.
     * @param nombre1
     * @param nombre2
     * @return String code
     */
    public static String codeGenerator(String nombre1, String nombre2 ){

        StringBuilder code = new StringBuilder();
        Random random = new Random();

        Integer number = random.nextInt(1000)+1;

        code.append(nombre1.toUpperCase().substring(0,3));
        code.append(nombre2.toUpperCase().substring(0,3));
        code.append("-");
        code.append(number);

        return String.valueOf((code));
    }

}
