package com.hackaboss.pruebatec4.repository;

import com.hackaboss.pruebatec4.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findByIdentification(String identification);
}
