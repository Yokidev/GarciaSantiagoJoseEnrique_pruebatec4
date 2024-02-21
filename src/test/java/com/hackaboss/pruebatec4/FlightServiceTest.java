package com.hackaboss.pruebatec4;


import com.hackaboss.pruebatec4.model.Flight;
import com.hackaboss.pruebatec4.repository.FlightRepository;
import com.hackaboss.pruebatec4.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getFlightsTest() {

        List<Flight> expectedFlights = new ArrayList<>();
        Flight flight = new Flight(
                1L,
                "SEVMAD",
                "Sevilla",
                "Madrid",
                LocalDate.of(2024,05,10),
                200.00,
                "Economic",
                50,
                0,
                new ArrayList<>());

        expectedFlights.add(flight);

        when(flightRepository.findAll()).thenReturn(expectedFlights);

        List<Flight> actualFlights = flightService.getFlights();

        assertEquals(1, actualFlights.size());

    }


}
