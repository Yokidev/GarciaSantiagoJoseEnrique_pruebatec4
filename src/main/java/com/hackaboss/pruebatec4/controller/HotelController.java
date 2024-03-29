package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.dto.HotelDTO;
import com.hackaboss.pruebatec4.exceptions.HotelExistException;
import com.hackaboss.pruebatec4.model.Hotel;
import com.hackaboss.pruebatec4.service.IHotelService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agency")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    /***
     * Devuelve la lista de hoteles
     * @return
     */
    @GetMapping("/hotels")
    public ResponseEntity<List<Hotel>> getHotels(){
        List<Hotel> hotelList = hotelService.getHotels();
        if (hotelList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hotelList, HttpStatus.OK);
    }

    /***
     * Devuelve el hotel que coincida con el id proporcionado
     * @param id
     * @return
     */
    @GetMapping("/hotels/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id){
        try {
            Hotel hotel = hotelService.findHotel(id);
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /***
     * Devuelve una lista de hoteles que coincida con los resultados obtenidos que coincidan con los parametros introducidos
     * @param city
     * @param dateFrom
     * @param dateTo
     * @return
     */
    @GetMapping("/hotelsFiltered")
    public ResponseEntity<List<HotelDTO>> getHotelsByCityAndDateBetween(
            @RequestParam String city,
            @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate dateFrom,
            @RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate dateTo
    ){
        List<HotelDTO> hotelList = hotelService.findHotelsByCityAndDateBetween(city, dateFrom, dateTo);
        if (hotelList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hotelList, HttpStatus.OK);
    }

    /***
     * Crea el hotel con los datos proporcionados
     * @param hotelDto
     * @return
     */
    @PostMapping("/hotels/new")
    public ResponseEntity<String> createHotel(@RequestBody HotelDTO hotelDto){
        try {
            hotelService.saveHotel(hotelDto);
            return new ResponseEntity<>("Hotel creado", HttpStatus.CREATED);
        }catch (HotelExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    /***
     * Modifica los parametros de un hotel existente
     * @param id
     * @param hotelDTO
     * @return
     */
    @PutMapping("/hotels/edit/{id}")
    public ResponseEntity<String> editHotel(@PathVariable Long id, @RequestBody HotelDTO hotelDTO){

        try {
            hotelService.editHotel(hotelDTO, id);
            return new ResponseEntity<>("Hotel editado", HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /***
     * Borra el hotel que coincida con la id proporcionada
     * @param id
     * @return
     */
    @DeleteMapping("/hotels/delete/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable Long id){

        try {
            hotelService.deleteHotel(id);
            return new ResponseEntity<>("Hotel borrado", HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>("No se puede eliminar el hotel porque tiene reservas asociadas.", HttpStatus.BAD_REQUEST);
        }
    }

}
