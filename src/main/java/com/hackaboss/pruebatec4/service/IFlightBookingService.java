package com.hackaboss.pruebatec4.service;


import com.hackaboss.pruebatec4.dto.FlightBookingDTO;
import com.hackaboss.pruebatec4.exceptions.FlightException;
import com.hackaboss.pruebatec4.model.FlightBooking;

import java.util.List;

public interface IFlightBookingService {

    public List<FlightBooking> getFlightBookings();
    public Double saveFlightBooking(FlightBookingDTO flightBookingDTO) throws FlightException;
    public void deleteFlightBooking(Long id);
    public FlightBooking findFlightBooking(Long id);

}
