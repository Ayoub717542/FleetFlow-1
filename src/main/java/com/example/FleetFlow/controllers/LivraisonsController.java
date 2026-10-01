package com.example.FleetFlow.controllers;

import com.example.FleetFlow.DTO.RequestLivraisionDTO;
import com.example.FleetFlow.DTO.ResponceLivraisionDTO;
import com.example.FleetFlow.enums.LivraisionStatut;
import com.example.FleetFlow.serviceInterfaces.LivraisonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/livraison")
public class LivraisonsController {
    final private LivraisonService livraisonService;

    @PostMapping("/creerLivraison")
    public ResponseEntity<ResponceLivraisionDTO>  creatLivraision(@RequestBody @Valid RequestLivraisionDTO dto) {
        return ResponseEntity.ok(livraisonService.creeLivraision(dto));
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<ResponceLivraisionDTO>  assign(
            @PathVariable long id,
            @RequestParam long chauffeurId,
            @RequestParam long vehiculeId) {
        return ResponseEntity.ok(livraisonService.assigner(id,chauffeurId,vehiculeId));
    }
    @PutMapping("/{id}/statut")
    public ResponseEntity<ResponceLivraisionDTO>  updateStatut(
            @PathVariable Long id,
            @RequestParam LivraisionStatut statut) {

        return ResponseEntity.ok(livraisonService.updateStatut(id,statut));
    }

    @GetMapping("/AfficherLivraison")
    public ResponseEntity<Page<ResponceLivraisionDTO>> getlivraision(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return ResponseEntity.ok(livraisonService.getAll(PageRequest.of(pageNumber-1, pageSize, sort)));
    }

    @GetMapping("/AfficherLivraisonByChauffeurDisponible")
    public ResponseEntity<Page<ResponceLivraisionDTO>>getlivraisonByChauffeurDis
            (
                    @RequestParam(defaultValue = "1") int pageNumber,
                    @RequestParam(defaultValue = "5") int pageSize,
                    @RequestParam(defaultValue = "id") String sortBy,
                    @RequestParam(defaultValue = "asc") String sortDir
            )
    {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return ResponseEntity.ok
                (livraisonService.getLivraisonByChauffeurDisponible(PageRequest.of(pageNumber-1, pageSize, sort)));
    }

    @GetMapping("/AfficherLivraisonByStatut")
    public ResponseEntity<Page<ResponceLivraisionDTO>>getbystatut(
            @RequestParam LivraisionStatut statut,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Page<ResponceLivraisionDTO> rs = livraisonService.getbystatut(statut,PageRequest.of(pageNumber-1,pageSize,sort));
            return ResponseEntity.ok(rs);
    }

    @GetMapping("/AfficherLivraisonByClient")
    public ResponseEntity<Page<ResponceLivraisionDTO>> findbyclientid(
            @RequestParam Long id,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir

    ) {
        Sort sort= sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return ResponseEntity.ok(livraisonService.findByClientId(id,PageRequest.of(pageNumber-1,pageSize,sort)));
    }


    @GetMapping("/AfficherLivraisonBetweenDates")
    public ResponseEntity<Page<ResponceLivraisionDTO>>  findbetweendates(
            @RequestParam LocalDate date1,
            @RequestParam LocalDate date2,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort= sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return ResponseEntity.ok(livraisonService.findBetweenDates(date1,date2,PageRequest.of(pageNumber-1,pageSize,sort)));
    }

    @GetMapping("/AfficherLivraisonByDestination")
    public ResponseEntity<Page<ResponceLivraisionDTO>> findbydestinationadress
            (@RequestParam String ville,
             @RequestParam(defaultValue = "1") int pageNumber,
             @RequestParam(defaultValue = "5") int pageSize,
             @RequestParam(defaultValue = "id") String sortBy,
             @RequestParam(defaultValue = "asc") String sortDir

            ) {
        Sort sort= sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return ResponseEntity.ok(livraisonService.findByAdresseDestination(ville,PageRequest.of(pageNumber-1,pageSize,sort)));
    }
}