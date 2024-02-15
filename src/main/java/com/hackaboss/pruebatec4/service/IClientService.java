package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.model.Client;

import java.util.List;

public interface IClientService {

    public List<Client> getClients();
    public void saveClient(Client client);
    public void deleteClient(Long id);
    public Client findClient(Long id);

}
