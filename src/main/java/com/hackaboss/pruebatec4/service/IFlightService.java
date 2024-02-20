package com.hackaboss.pruebatec4.service;



import com.hackaboss.pruebatec4.dto.FlightDTO;
import com.hackaboss.pruebatec4.model.Flight;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IFlightService {

    List<Flight> getFlights();
    void saveFlight(FlightDTO flightDTO);
    void deleteFlight(Long id);
    Flight findFlight(Long id);
    void editFlight(FlightDTO flightDTO, Long id);
    List<Flight> findByOriginAndDestinationAndDateBetween(String origin, String destination, LocalDate startDate, LocalDate endDate);


}
