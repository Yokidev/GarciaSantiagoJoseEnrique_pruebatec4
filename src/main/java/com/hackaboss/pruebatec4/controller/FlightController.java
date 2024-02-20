package com.hackaboss.pruebatec4.controller;


import com.hackaboss.pruebatec4.dto.FlightDTO;
import com.hackaboss.pruebatec4.model.Flight;
import com.hackaboss.pruebatec4.service.IFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agency")
public class FlightController {

    @Autowired
    private IFlightService flightService;

    @GetMapping("/flights")
    public List<Flight> getFlights(){
        return flightService.getFlights();
    }

    @GetMapping("/flights/{id}")
    public Flight getFlightById(@PathVariable Long id){
        return flightService.findFlight(id);
    }


    @GetMapping("/flightsFiltered")
    public List<Flight> getFlightsByOrigenAndDestinationAndDateBetween(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate dateFrom,
            @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate dateTo
            ){
        return flightService.findByOriginAndDestinationAndDateBetween(origin, destination, dateFrom, dateTo);
    }

    @PostMapping("/flights/new")
    public String createFlight(@RequestBody FlightDTO flightDTO){

        flightService.saveFlight(flightDTO);

        return "Vuelo creado";
    }

    @PutMapping("/flights/edit/{id}")
    public String editFlight(@PathVariable Long id, @RequestBody FlightDTO flightDTO){

        flightService.editFlight(flightDTO, id);

        return "Vuelo editado";
    }

    @DeleteMapping("/flights/delete/{id}")
    public String deleteFlight(@PathVariable Long id){

        flightService.deleteFlight(id);

        return "Vuelo borrado";
    }

}
