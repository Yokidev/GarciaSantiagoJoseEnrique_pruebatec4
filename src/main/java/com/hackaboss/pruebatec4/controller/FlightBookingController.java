package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.dto.FlightBookingDTO;
import com.hackaboss.pruebatec4.exceptions.FlightException;
import com.hackaboss.pruebatec4.model.FlightBooking;
import com.hackaboss.pruebatec4.service.IFlightBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class FlightBookingController {

    @Autowired
    private IFlightBookingService flightBookingService;

    @GetMapping("/flight-bookings")
    public List<FlightBooking> getFlightBookings(){
        return flightBookingService.getFlightBookings();
    }

    @GetMapping("/flight-bookings/{id}")
    public FlightBooking getFlightBookingById(@PathVariable Long id){
        return flightBookingService.findFlightBooking(id);
    }

    @PostMapping("/flight-booking/new")
    public String createFlightBooking(@RequestBody FlightBookingDTO flightBookingDTO) throws FlightException {
        return "El precio de la reserva es: " + flightBookingService.saveFlightBooking(flightBookingDTO) + " €.";
    }

    @DeleteMapping("/flight-booking/delete/{id}")
    public String deleteFlightBooking(@PathVariable Long id){
        flightBookingService.deleteFlightBooking(id);
        return "Reserva de vuelo borrada";
    }

}
