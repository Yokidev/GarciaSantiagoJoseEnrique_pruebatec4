package com.hackaboss.pruebatec4.dto;

import com.hackaboss.pruebatec4.model.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO implements Serializable {

    private String name;
    private String city;
    private List<Room> rooms;

}
