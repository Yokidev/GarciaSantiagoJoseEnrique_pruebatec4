package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.dto.HotelDTO;
import com.hackaboss.pruebatec4.model.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface IHotelService {

    List<Hotel> getHotels();
    void saveHotel(HotelDTO hotel);
    void deleteHotel(Long id);
    Hotel findHotel(Long id);
    void editHotel(HotelDTO hotelDTO, Long id);
    List<HotelDTO> findHotelsByCityAndDateBetween(String city, LocalDate dateFrom, LocalDate dateTo);
}
