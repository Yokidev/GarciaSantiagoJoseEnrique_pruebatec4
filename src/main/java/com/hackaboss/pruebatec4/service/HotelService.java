package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.model.Hotel;
import com.hackaboss.pruebatec4.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService implements IHotelService{

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public void saveHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    @Override
    public Hotel findHotel(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }
}
