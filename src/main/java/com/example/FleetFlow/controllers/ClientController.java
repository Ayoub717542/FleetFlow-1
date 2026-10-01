package com.example.FleetFlow.controllers;

import com.example.FleetFlow.DTO.ResponceClientDTO;
import com.example.FleetFlow.DTO.RequestClientDTO;
import com.example.FleetFlow.models.Client;
import com.example.FleetFlow.serviceInterfaces.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/ajouterClient")
    public ResponseEntity<ResponceClientDTO> saveClient(@Valid @RequestBody RequestClientDTO client) {
        return ResponseEntity.ok(clientService.ajouterClient(client));
    }
    @GetMapping("/afficherClients")
    public ResponseEntity<Page<ResponceClientDTO>> displayClients(Pageable pageable) {
        Page<ResponceClientDTO> rs = clientService.afficherClients(pageable);
        return ResponseEntity.ok(rs);
    }

    @DeleteMapping("/supprimerClient/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.deleteClient(id));
    }

    @PutMapping("/modifierClient/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        return ResponseEntity.ok(clientService  .updateClient(id, client));
    }

}