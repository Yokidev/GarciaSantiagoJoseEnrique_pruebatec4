package com.hackaboss.pruebatec4.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonBackReference(value = "room-hotel")
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private String roomType;
    private Integer maxCapacity;

    private Double price;

    private Boolean available;

    @JsonManagedReference(value = "room-roombooking")
    @OneToMany(mappedBy = "room")
    private List<RoomBooking> roomBookings;


}
