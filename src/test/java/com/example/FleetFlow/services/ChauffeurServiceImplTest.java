package com.example.FleetFlow.services;

import com.example.FleetFlow.DTO.ResponceChauffeurDTO;
import com.example.FleetFlow.Mapper.ChauffeurMapper;
import com.example.FleetFlow.models.Chauffeur;
import com.example.FleetFlow.repositories.ChauffeurRepository;
import com.example.FleetFlow.serviceInterfaces.services.ChauffeurServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChauffeurServiceImplTest {


    @InjectMocks
    private ChauffeurServiceImpl chauffeurServiceImpl;
    @Mock
    private ChauffeurMapper mapper;
    @Mock
    private ChauffeurRepository chauffeurRepository;
    @Mock
    private ResponceChauffeurDTO responceChauffeurDTO;
    private Chauffeur chauffeur;

    @org.junit.jupiter.api.BeforeEach
    public void setUp(){
        responceChauffeurDTO = new ResponceChauffeurDTO();
        responceChauffeurDTO.setUsername("Ali");
        chauffeur = new Chauffeur();
        chauffeur.setUsername("Ali");
    }


    @Test
    void findByDisponibility()
    {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Chauffeur> chauffeurPage = new PageImpl<>(List.of(chauffeur));

        when(chauffeurRepository.findByIsDisponibleTrue(pageable)).thenReturn(chauffeurPage);
        when(mapper.toDTO(chauffeur)).thenReturn(responceChauffeurDTO);

        Page<ResponceChauffeurDTO> result = chauffeurServiceImpl.findByDisponibility(pageable   );
        assertNotNull(result);
        assertEquals(1,result.getTotalElements());
        assertEquals(responceChauffeurDTO, result.getContent().get(0));

        verify(chauffeurRepository, times(1)).findByIsDisponibleTrue(pageable);
        verify(mapper, times(1)).toDTO(chauffeur);
    }
}