package com.example.FleetFlow.services;

import com.example.FleetFlow.DTO.ResponceVehiculeDTO;
import com.example.FleetFlow.enums.VehiculeStatut;
import com.example.FleetFlow.models.Vehicule;
import com.example.FleetFlow.repositories.VehculeRepository;
import com.example.FleetFlow.serviceInterfaces.services.VehiculeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehiculeServiceImplTest {

    @InjectMocks
    private VehiculeServiceImpl vehiculeServiceImpl;

    @Mock
    private VehculeRepository vehculeRepository;

    @Test
    void shouldReturnVehiculesByStatut() {
        Pageable pageable = PageRequest.of(0, 10);
        
        Vehicule vehicule1 = new Vehicule();
        vehicule1.setStatut(VehiculeStatut.DISPONIBLE);
        Vehicule vehicule2 = new Vehicule();
        vehicule2.setStatut(VehiculeStatut.DISPONIBLE);

        Page<Vehicule> vehicules = new PageImpl(List.of(vehicule1, vehicule2));

        when(vehculeRepository.findByStatut(VehiculeStatut.DISPONIBLE, pageable)).thenReturn(vehicules);

        Page<ResponceVehiculeDTO> rs = vehiculeServiceImpl.findbystatut(VehiculeStatut.DISPONIBLE, pageable );

        assertTrue(rs.getContent().stream().allMatch(v -> v.getStatut().equals(VehiculeStatut.DISPONIBLE)));
    }
    
    @Test
    void shouldReturnVehiculesWithCapacityGreaterThan() {
        Pageable pageable = PageRequest.of(0, 10);
        int capacity = 50;
        Vehicule vehicule1 = new Vehicule();
        vehicule1.setCapacite(60);
        Vehicule vehicule2 = new Vehicule();
        vehicule2.setCapacite(80);

        Page<Vehicule> vehicules = new PageImpl(List.of(vehicule1, vehicule2));

        when(vehculeRepository.findByCapaciteGreaterThan(capacity,pageable)).thenReturn(vehicules);

        Page<ResponceVehiculeDTO> rs = vehiculeServiceImpl.findgreteCapacitythan(capacity,pageable);

        assertTrue(rs.stream().allMatch(v -> v.getCapacite() > capacity));
    }
}