package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.model.Client;
import com.hackaboss.pruebatec4.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    IClientService clientService;

    @GetMapping("/clients")
    public List<Client> getClients(){
        return clientService.getClients();
    }
}
