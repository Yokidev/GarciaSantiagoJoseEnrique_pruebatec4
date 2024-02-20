package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.model.Client;
import com.hackaboss.pruebatec4.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class ClientController {

    @Autowired
    IClientService clientService;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getClients(){
        List<Client> clientList = clientService.getClients();
        if (clientList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientList, HttpStatus.OK);
    }
}
