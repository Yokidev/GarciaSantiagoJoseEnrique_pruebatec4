package com.hackaboss.pruebatec4.controller;


import com.hackaboss.pruebatec4.dto.FlightDTO;
import com.hackaboss.pruebatec4.model.Flight;
import com.hackaboss.pruebatec4.service.IFlightService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agency")
public class FlightController {

    @Autowired
    private IFlightService flightService;

    @GetMapping("/flights")
    public ResponseEntity<List<Flight>> getFlights(){
        List<Flight> flightList = flightService.getFlights();
        if (flightList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(flightList, HttpStatus.OK);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id){
        try {
            Flight flight = flightService.findFlight(id);
            return new ResponseEntity<>(flight, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/flightsFiltered")
    public ResponseEntity<List<Flight>> getFlightsByOrigenAndDestinationAndDateBetween(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate dateFrom,
            @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate dateTo
            ){

        List<Flight> flightList = flightService.findByOriginAndDestinationAndDateBetween(origin, destination, dateFrom, dateTo);
        if (flightList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(flightList, HttpStatus.OK);

    }

    @PostMapping("/flights/new")
    public ResponseEntity<String> createFlight(@RequestBody FlightDTO flightDTO){

        flightService.saveFlight(flightDTO);
        return new ResponseEntity<>("Vuelo creado", HttpStatus.CREATED);

    }

    @PutMapping("/flights/edit/{id}")
    public ResponseEntity<String> editFlight(@PathVariable Long id, @RequestBody FlightDTO flightDTO){

        try {
            flightService.editFlight(flightDTO, id);
            return new ResponseEntity<>("Vuelo editado", HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/flights/delete/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id){
        try {
            flightService.deleteFlight(id);
            return new ResponseEntity<>("Vuelo borrado", HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>("No se puede eliminar el vuelo porque tiene reservas asociadas.", HttpStatus.BAD_REQUEST);
        }

    }

}
