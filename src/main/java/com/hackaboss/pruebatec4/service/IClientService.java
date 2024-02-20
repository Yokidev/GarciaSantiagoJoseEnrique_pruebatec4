package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.model.Client;

import java.util.List;

public interface IClientService {

    List<Client> getClients();
    void saveClient(Client client);
    void deleteClient(Long id);
    Client findClient(Long id);

}
