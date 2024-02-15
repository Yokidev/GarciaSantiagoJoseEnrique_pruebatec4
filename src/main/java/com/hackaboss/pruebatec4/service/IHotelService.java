package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.model.Hotel;

import java.util.List;

public interface IHotelService {

    public List<Hotel> getHotels();
    public void saveHotel(Hotel hotel);
    public void deleteHotel(Long id);
    public Hotel findHotel(Long id);

}
