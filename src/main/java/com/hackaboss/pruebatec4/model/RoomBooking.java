package com.hackaboss.pruebatec4.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @JsonBackReference(value = "room-roombooking")
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate checkIn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate checkOut;

    @JsonManagedReference(value = "client-roombooking")
    @ManyToMany
    @JoinTable(name = "room_booking_client",
        joinColumns = @JoinColumn(name = "room_booking_id"),
        inverseJoinColumns = @JoinColumn(name = "client_id"))
    private List<Client> hosts = new ArrayList<>();

    public void addHosts(Client client){
        hosts.add(client);
    }

}
