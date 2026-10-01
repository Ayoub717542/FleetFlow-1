package com.example.FleetFlow.services;

import com.example.FleetFlow.DTO.RequestLivraisionDTO;
import com.example.FleetFlow.DTO.ResponceLivraisionDTO;
import com.example.FleetFlow.Mapper.LivraisionMapper;
import com.example.FleetFlow.enums.LivraisionStatut;
import com.example.FleetFlow.models.Livraison;
import com.example.FleetFlow.repositories.LivraisonRepository;
import com.example.FleetFlow.serviceInterfaces.services.LivraisonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LivraisonServiceImplTest {

    @InjectMocks
    private LivraisonServiceImpl livraisonServiceImpl;

    @Mock
    private LivraisonRepository livraisonRepository;

    @Mock
    private LivraisionMapper mapper;

    private Livraison livraison;
    private ResponceLivraisionDTO responceDTO;
    private RequestLivraisionDTO requestDTO;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);

        livraison = new Livraison();
        livraison.setId(1L);
        livraison.setLivraisionStatut(LivraisionStatut.EN_ATTENTE);
        livraison.setAdresseDestination("Casablanca");

        responceDTO = new ResponceLivraisionDTO();
        responceDTO.setChauffeurId(1L);

        requestDTO = new RequestLivraisionDTO();
    }



    @Test
    void shouldCreateLivraision() {
        when(mapper.toEntity(requestDTO)).thenReturn(livraison);
        when(livraisonRepository.save(livraison)).thenReturn(livraison);
        when(mapper.toDTO(livraison)).thenReturn(responceDTO);

        ResponceLivraisionDTO result = livraisonServiceImpl.creeLivraision(requestDTO);

        assertNotNull(result);
        assertEquals(responceDTO.getChauffeurId(), result.getChauffeurId());
        verify(livraisonRepository, times(1)).save(livraison);
        verify(mapper, times(1)).toDTO(livraison);
    }


    @Test
    void shouldAssignChauffeurAndVehiculeToLivraision() {
        Long livraisonId = 1L, chauffeurId = 2L, vehiculeId = 3L;

        when(livraisonRepository.findById(livraisonId)).thenReturn(Optional.of(livraison));
        when(livraisonRepository.save(livraison)).thenReturn(livraison);
        when(mapper.toDTO(livraison)).thenReturn(responceDTO);

        ResponceLivraisionDTO result = livraisonServiceImpl.assigner(livraisonId, chauffeurId, vehiculeId);

        assertNotNull(result);
        verify(livraisonRepository, times(1)).findById(livraisonId);
        verify(livraisonRepository, times(1)).save(livraison);
    }

    @Test
    void shouldThrowWhenLivraisonNotFoundOnAssign() {
        when(livraisonRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> livraisonServiceImpl.assigner(99L, 1L, 1L));
    }


    @Test
    void shouldUpdateStatut() {
        when(livraisonRepository.findById(1L)).thenReturn(Optional.of(livraison));
        when(livraisonRepository.save(livraison)).thenReturn(livraison);
        when(mapper.toDTO(livraison)).thenReturn(responceDTO);

        ResponceLivraisionDTO result = livraisonServiceImpl.updateStatut(1L, LivraisionStatut.LIVREE);

        assertNotNull(result);
        verify(livraisonRepository, times(1)).save(livraison);
    }

    @Test
    void shouldThrowWhenLivraisonNotFoundOnUpdateStatut() {
        when(livraisonRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> livraisonServiceImpl.updateStatut(99L, LivraisionStatut.LIVREE));
    }



    @Test
    void shouldReturnAllLivraisonsPageable() {
        Page<Livraison> page = new PageImpl<>(List.of(livraison));

        when(livraisonRepository.findAll(pageable)).thenReturn(page);
        when(mapper.toDTO(livraison)).thenReturn(responceDTO);

        Page<ResponceLivraisionDTO> result = livraisonServiceImpl.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(livraisonRepository, times(1)).findAll(pageable);
    }


    @Test
    void shouldReturnLivraisonsByStatut() {
        Page<Livraison> page = new PageImpl<>(List.of(livraison));

        when(livraisonRepository.findByLivraisionStatut(LivraisionStatut.EN_ATTENTE, pageable)).thenReturn(page);
        when(mapper.toDTO(livraison)).thenReturn(responceDTO);

        Page<ResponceLivraisionDTO> result = livraisonServiceImpl.getbystatut(LivraisionStatut.EN_ATTENTE, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(livraisonRepository, times(1)).findByLivraisionStatut(LivraisionStatut.EN_ATTENTE, pageable);
    }


    @Test
    void shouldReturnLivraisonsByChauffeurDisponible() {
        Page<Livraison> page = new PageImpl<>(List.of(livraison));

        when(livraisonRepository.findByChauffeurIsDisponible(pageable)).thenReturn(page);
        when(mapper.toDTO(livraison)).thenReturn(responceDTO);

        Page<ResponceLivraisionDTO> result = livraisonServiceImpl.getLivraisonByChauffeurDisponible(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(livraisonRepository, times(1)).findByChauffeurIsDisponible(pageable);
    }

    // ─── findByAdresseDestination ──────────────────────────────────────────────

    @Test
    void shouldReturnLivraisonsByAdresseDestination() {
        String ville = "Casablanca";
        Page<Livraison> page = new PageImpl<>(List.of(livraison));

        when(livraisonRepository.findByadresseDestination(ville, pageable)).thenReturn(page);
        when(mapper.toDTO(livraison)).thenReturn(responceDTO);

        Page<ResponceLivraisionDTO> result = livraisonServiceImpl.findByAdresseDestination(ville, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(livraisonRepository, times(1))
                .findByadresseDestination(ville, pageable);
    }

    // ─── findBetweenDates ──────────────────────────────────────────────────────

    @Test
    void shouldReturnLivraisonsBetweenDates() {
        LocalDate date1 = LocalDate.of(2024, 1, 1);
        LocalDate date2 = LocalDate.of(2024, 12, 31);
        Page<Livraison> page = new PageImpl<>(List.of(livraison));

        when(livraisonRepository.findBetweenDates(date1, date2, pageable)).thenReturn(page);
        when(mapper.toDTO(livraison)).thenReturn(responceDTO);

        Page<ResponceLivraisionDTO> result = livraisonServiceImpl.findBetweenDates(date1, date2, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(livraisonRepository, times(1)).findBetweenDates(date1, date2, pageable);
    }

    @Test
    void shouldReturnEmptyPageWhenNoBetweenDates() {
        LocalDate date1 = LocalDate.of(2020, 1, 1);
        LocalDate date2 = LocalDate.of(2020, 12, 31);

        when(livraisonRepository.findBetweenDates(date1, date2, pageable)).thenReturn(Page.empty());

        Page<ResponceLivraisionDTO> result = livraisonServiceImpl.findBetweenDates(date1, date2, pageable);

        assertTrue(result.isEmpty());
    }

    // ─── findByClientId ────────────────────────────────────────────────────────

    @Test
    void shouldReturnLivraisonsByClientId() {
        Long clientId = 5L;
        Page<Livraison> page = new PageImpl<>(List.of(livraison));

        when(livraisonRepository.findByClientId(clientId, pageable)).thenReturn(page);
        when(mapper.toDTO(livraison)).thenReturn(responceDTO);

        Page<ResponceLivraisionDTO> result = livraisonServiceImpl.findByClientId(clientId, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(livraisonRepository, times(1)).findByClientId(clientId, pageable);
    }

    @Test
    void shouldReturnEmptyPageWhenClientHasNoLivraisons() {
        when(livraisonRepository.findByClientId(999L, pageable)).thenReturn(Page.empty());

        Page<ResponceLivraisionDTO> result = livraisonServiceImpl.findByClientId(999L, pageable);

        assertTrue(result.isEmpty());
    }
}