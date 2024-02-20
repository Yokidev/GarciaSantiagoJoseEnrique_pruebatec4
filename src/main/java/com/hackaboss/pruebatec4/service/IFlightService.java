package com.hackaboss.pruebatec4.service;



import com.hackaboss.pruebatec4.dto.FlightDTO;
import com.hackaboss.pruebatec4.model.Flight;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IFlightService {

    public List<Flight> getFlights();
    public void saveFlight(FlightDTO flightDTO);
    public void deleteFlight(Long id);
    public Flight findFlight(Long id);
    public void editFlight(FlightDTO flightDTO, Long id);
    List<Flight> findByOriginAndDestinationAndDateBetween(String origin, String destination, LocalDate startDate, LocalDate endDate);


}
