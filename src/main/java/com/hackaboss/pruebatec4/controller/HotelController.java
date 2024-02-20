package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.dto.HotelDTO;
import com.hackaboss.pruebatec4.model.Hotel;
import com.hackaboss.pruebatec4.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agency")
public class HotelController {

    @Autowired
    private IHotelService hotelService;


    @GetMapping("/hotels")
    public List<Hotel> getHotels(){
        return hotelService.getHotels();
    }


    @GetMapping("/hotels/{id}")
    public Hotel getHotelById(@PathVariable Long id){
        return hotelService.findHotel(id);
    }


    /*Hoteles filtrados
    public Hotel getHotelByDateAndDestination()
    * */
    @GetMapping("/hotelsFiltered")
    public List<HotelDTO> getHotelsByCityAndDateBetween(
            @RequestParam String city,
            @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate dateFrom,
            @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate dateTo
    ){
        return hotelService.findHotelsByCityAndDateBetween(city, dateFrom, dateTo);
    }


    @PostMapping("/hotels/new")
    public String createHotel(@RequestBody HotelDTO hotelDto){

        hotelService.saveHotel(hotelDto);

        return "Hotel creado";
    }

    @PutMapping("/hotels/edit/{id}")
    public String editHotel(@PathVariable Long id, @RequestBody HotelDTO hotelDTO){
        hotelService.editHotel(hotelDTO, id);

        return "Hotel editado";
    }


    @DeleteMapping("/hotels/delete/{id}")
    public String deleteHotel(@PathVariable Long id){

        hotelService.deleteHotel(id);

        return "Hotel borrado";
    }

}
