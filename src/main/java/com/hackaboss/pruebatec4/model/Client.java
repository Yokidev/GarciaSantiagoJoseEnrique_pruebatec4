package com.hackaboss.pruebatec4.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate birthdate;
    @Column(unique = true)
    private String identification;

    @JsonBackReference(value = "client-roombooking")
    @ManyToMany(mappedBy = "hosts")
    private List<RoomBooking> roomBookingList = new ArrayList<>();


    @JsonBackReference(value = "client-flightbooking")
    @ManyToMany(mappedBy = "passengers")
    private List<FlightBooking> flightBookingList = new ArrayList<>();


    public void addRoomBooking(RoomBooking roomBooking){
        roomBookingList.add(roomBooking);
    }

    public void addFlightBooking(FlightBooking flightBooking){
        flightBookingList.add(flightBooking);
    }

}
