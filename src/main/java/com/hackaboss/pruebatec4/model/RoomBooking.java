package com.hackaboss.pruebatec4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clientName;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDate checkIn;
    private LocalDate checkOut;

    @ManyToMany
    @JoinTable(name = "room_booking_client",
        joinColumns = @JoinColumn(name = "room_booking_id"),
        inverseJoinColumns = @JoinColumn(name = "client_id"))
    private List<Client> hosts;

}
