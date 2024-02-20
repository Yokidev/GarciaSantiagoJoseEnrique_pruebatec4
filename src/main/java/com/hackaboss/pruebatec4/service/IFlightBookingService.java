package com.hackaboss.pruebatec4.service;


import com.hackaboss.pruebatec4.dto.FlightBookingDTO;
import com.hackaboss.pruebatec4.exceptions.FlightBookingDataException;
import com.hackaboss.pruebatec4.model.FlightBooking;

import java.util.List;

public interface IFlightBookingService {

    List<FlightBooking> getFlightBookings();
    Double saveFlightBooking(FlightBookingDTO flightBookingDTO) throws FlightBookingDataException;
    void deleteFlightBooking(Long id);
    FlightBooking findFlightBooking(Long id);

}
