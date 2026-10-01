package com.example.FleetFlow.repositories;

import com.example.FleetFlow.DTO.RequestClientDTO;
import com.example.FleetFlow.models.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

  Optional<Client>findByEmail(String email);
  Page<Client> findAll(Pageable pageable);
}