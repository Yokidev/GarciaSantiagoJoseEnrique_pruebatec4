package com.hackaboss.pruebatec4.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FlightBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @JsonBackReference(value = "flight-flightbooking")
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
    private String nameClient;
    private Integer numberTickets;


    @JsonManagedReference(value = "client-flightbooking")
    @ManyToMany
    @JoinTable(name = "flight_booking_client",
        joinColumns = @JoinColumn(name = "flightbooking_id"),
        inverseJoinColumns = @JoinColumn(name = "client_id"))
    private List<Client> passengers = new ArrayList<>();

    public void addPassenger(Client client){
        passengers.add(client);
    }

}
