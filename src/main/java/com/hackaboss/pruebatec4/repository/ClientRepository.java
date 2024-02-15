package com.hackaboss.pruebatec4.repository;

import com.hackaboss.pruebatec4.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
}
