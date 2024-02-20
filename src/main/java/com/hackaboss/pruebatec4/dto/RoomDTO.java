package com.hackaboss.pruebatec4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO implements Serializable {

    private Long idHotel;
    private String roomType;
    private Integer maxCapacity;
    private Double price;
    private Boolean available;

}
