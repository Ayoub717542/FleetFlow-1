package com.example.FleetFlow.DTO;

import com.example.FleetFlow.enums.LivraisionStatut;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RequestLivraisionDTO {
    @NotNull(message = "Date de livraison is required")
    private LocalDate dateLivraison;

    @NotBlank(message = "Adresse de départ is required")
    private String adresseDepart;

    @NotBlank(message = "Adresse de destination is required")
    private String adresseDestination;

    private LivraisionStatut statut;

    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Chauffeur ID is required")
    private Long chauffeurId;

    @NotNull(message = "Véhicule ID is required")
    private Long vehiculeId;
}
