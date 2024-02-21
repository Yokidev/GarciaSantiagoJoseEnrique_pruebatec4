package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.dto.FlightBookingDTO;
import com.hackaboss.pruebatec4.exceptions.FlightBookingDataException;
import com.hackaboss.pruebatec4.model.FlightBooking;
import com.hackaboss.pruebatec4.service.IFlightBookingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class FlightBookingController {

    @Autowired
    private IFlightBookingService flightBookingService;

    /***
     * Devuelve la lista de reservas de vuelos
     * @return ResponseEntity<List<FlightBooking>>
     */
    @GetMapping("/flight-bookings")
    public ResponseEntity<List<FlightBooking>> getFlightBookings(){
        List<FlightBooking> flightBookingsList = flightBookingService.getFlightBookings();
        if (flightBookingsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(flightBookingsList, HttpStatus.OK);
    }

    /***
     * Devuelve la reserva que coincida con el id proporcionado
     * @param id
     * @return
     */
    @GetMapping("/flight-booking/{id}")
    public ResponseEntity<FlightBooking> getFlightBookingById(@PathVariable Long id){
        try {
            FlightBooking flightBooking = flightBookingService.findFlightBooking(id);
            return new ResponseEntity<>(flightBooking, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /***
     * Crea la reserva con los datos proporcionados
     * @param flightBookingDTO
     * @return
     * @throws FlightBookingDataException
     */
    @PostMapping("/flight-booking/new")
    public ResponseEntity<String> createFlightBooking(@RequestBody FlightBookingDTO flightBookingDTO) throws FlightBookingDataException {
        try {
            double price = flightBookingService.saveFlightBooking(flightBookingDTO);
            String message = "El precio de la reserva es: " + price + "€.";
            return  new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (FlightBookingDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /***
     * Borra la reserva que coincida con la id proporcionada
     * @param id
     * @return
     */
    @DeleteMapping("/flight-booking/delete/{id}")
    public ResponseEntity<String> deleteFlightBooking(@PathVariable Long id){
        try {
            flightBookingService.deleteFlightBooking(id);
            return new ResponseEntity<>("Reserva de vuelo borrada", HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
